package ca.humber.finalproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        /*Create Hibernate SessionFactory
        Configuration conf = new Configuration().configure().addAnnotatedClass(User.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        */

        //Create UI controls
        Label errMsg = new Label("Invalid username/password!");
        errMsg.setVisible(false);
        Label lblUser = new Label("User:");
        Label lblID = new Label("Username:");
        Label lblPass = new Label("Password:");
        TextField txtUser = new TextField();
        TextField txtID = new TextField();
        PasswordField passwd = new PasswordField();
        txtUser.setPromptText("Client or Technician");
        txtID.setPromptText("Enter username");
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
        grid.add(lblUser, 0, 1);
        grid.add(lblID, 0, 2);
        grid.add(lblPass, 0, 3);
        grid.add(txtUser, 1,1);
        grid.add(txtID, 1,2);
        grid.add(passwd, 1,3);
        grid.add(buttons, 1, 4);

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(txtID.getText().equals("admin") && passwd.getText().equals("12345")) {
                    try {
                        if (txtUser.getText().equals("Client")) {
                            VehicleForm.start(stage);
                        } else if(txtUser.getText().equals("Technician")) {

                        } else {

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