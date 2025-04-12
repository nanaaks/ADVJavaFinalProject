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
import javafx.scene.layout.VBox;
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

        //Login Scene
        Button btnLogin = new Button("Login");
        Button btnClear = new Button("Clear");
        Button btnExit = new Button("Exit");
        Label errMsg = new Label("Invalid username/password!");
        errMsg.setVisible(false);
        Label lblID = new Label("Username:");
        Label lblPass = new Label("Password:");
        TextField txtID = new TextField();
        PasswordField passwd = new PasswordField();
        txtID.setPromptText("Enter username");
        passwd.setPromptText("Enter password");
        HBox buttons = new HBox();
        buttons.getChildren().addAll(btnLogin, btnExit, btnClear);

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

        Scene mainMenu = Menu.getScene();

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(txtID.getText() .equals("admin") && passwd.getText() .equals("12345")) {
                    try {
                        Menu.start(stage);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    stage.setTitle("Main Menu");
                    stage.setScene(mainMenu);
                    stage.show();
                } else {
                    errMsg.setTextFill(Color.RED);
                    errMsg.setVisible(true);
                }
            }
        });

        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        btnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtID.setText("");
                passwd.setText("");
            }
        });

        Scene loginScene = new Scene(grid, 250, 250);
        stage.setTitle("Login");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}