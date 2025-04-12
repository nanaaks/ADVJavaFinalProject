package ca.humber.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu {

    public static Scene getScene(){
        //Create UI Controls
        Label lblTitle = new Label("Welcome");
        Button btnLogout = new Button("Logout");
        VBox pane = new VBox();
        pane.getChildren().addAll(lblTitle, btnLogout);
        Scene mainMenu = new Scene(pane, 300, 300);
        return mainMenu;
    }

    public void start(Stage stage) throws IOException {

        //Create UI Controls
        Label lblTitle = new Label("Welcome");
        Button btnLogout = new Button("Logout");
        VBox pane = new VBox();
        pane.getChildren().addAll(lblTitle, btnLogout);
        Scene mainMenu = new Scene(pane, 300, 300);

        /*
        btnLogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setTitle("Login Page");
                stage.setScene(loginScene);
                stage.show();
            }
        });
         */
    }
}
