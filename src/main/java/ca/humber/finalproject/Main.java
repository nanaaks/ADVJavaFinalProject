package ca.humber.finalproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Create Hibernate SessionFactory
        Configuration conf = new Configuration().configure().addAnnotatedClass(User.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        SessionFactory sFact = conf.buildSessionFactory(reg);
        Session session = sFact.openSession();

        //Create UI controls
        Label errMsg = new Label("Invalid username/password!");
        errMsg.setVisible(false);
        Label lblID = new Label("User ID:");
        Label lblPass = new Label("Password:");
        TextField txtID = new TextField();
        PasswordField passwd = new PasswordField();
        txtID.setPromptText("Enter user ID");
        passwd.setPromptText("Enter password");
        Button btnLogin = new Button("Login");
        Button btnExit = new Button("Exit");
        HBox buttons = new HBox(65);
        buttons.getChildren().addAll(btnLogin, btnExit);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(false);
        grid.add(errMsg, 0,0, 4,1);
        grid.add(lblID, 0, 1);
        grid.add(lblPass, 0, 2);
        grid.add(txtID, 1,1);
        grid.add(passwd, 1,2);
        grid.add(buttons, 1, 3);

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Transaction trs =  session.beginTransaction();
                User user = session.get(User.class, txtID.getText());
                String userID = user.getUserid();
                String password = user.getPassword();
                if(txtID.getText().equals(userID) && passwd.getText().equals(password)) {
                    try {
                        if (user.getRole().equals("Client")) {
                            ClientForm.start(stage, userID);
                        } else if(user.getRole().equals("Technician")) {
                            TechForm.start(stage);
                        } else if (user.getRole().equals("Administrator")) {
                            AdminForm.start(stage);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    errMsg.setTextFill(Color.RED);
                    errMsg.setVisible(true);
                    txtID.setText("");
                    passwd.setText("");
                }
                trs.commit();
            }
        });

        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        Scene loginScene = new Scene(grid, 300, 300);
        stage.setTitle("Login");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}