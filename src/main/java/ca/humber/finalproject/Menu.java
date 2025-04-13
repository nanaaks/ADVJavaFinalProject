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

    public static void start(Stage stage) throws IOException {
        //Create UI Controls
        Label lblTitle = new Label("Welcome");
        Button btnVehicles = new Button("Vehicle Registration");
        Button btnLogout = new Button("Logout");
        VBox menu = new VBox(30);
        menu.getChildren().addAll(lblTitle, btnVehicles, btnLogout);
        Scene mainMenu = new Scene(menu, 300, 300);
        stage.setTitle("Main Menu");
        stage.setScene(mainMenu);
        stage.show();

        btnLogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main login = new Main();
                    login.start(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
