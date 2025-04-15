package ca.humber.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientForm {

    public static void start(Stage stage) throws IOException {
        //Create UI Controls
        Label lblTitle = new Label("Welcome, " + "admin");
        Label lblVIN = new Label("VIN:");
        Label lblMake = new Label("Make:");
        Label lblModel = new Label("Model:");
        Label lblYear = new Label("Year:");
        Label lblMileage = new Label("Mileage:");
        Label lblPlate = new Label("License Plate:");
        TextField txtVIN = new TextField();
        TextField txtMake = new TextField();
        TextField txtModel = new TextField();
        TextField txtYear = new TextField();
        TextField txtMileage = new TextField();
        TextField txtPlate = new TextField();
        Button btnRegister = new Button("Register a New Vehicle");
        Button btnMaintain = new Button("View Maintenance History");
        Button btnSchedule = new Button("Schedule Appointments");
        Button btnLogout = new Button("Logout");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(false);
        grid.add(lblTitle, 0, 0);
        grid.add(lblVIN, 0, 1);
        grid.add(lblMake, 0, 2);
        grid.add(lblModel, 0, 3);
        grid.add(lblYear, 0, 4);
        grid.add(lblMileage, 0, 5);
        grid.add(lblPlate, 0, 6);
        grid.add(txtVIN, 1, 1);
        grid.add(txtMake, 1, 2);
        grid.add(txtModel, 1, 3);
        grid.add(txtYear,1, 4);
        grid.add(txtMileage,1, 5);
        grid.add(txtPlate,1, 6);
        grid.add(btnRegister, 0, 7);
        grid.add(btnMaintain, 1,7);
        grid.add(btnSchedule, 2, 7);
        grid.add(btnLogout, 2, 0);

        Scene mainMenu = new Scene(grid, 550, 350);
        stage.setTitle("Vehicle Registration");
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
