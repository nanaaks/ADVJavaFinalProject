package ca.humber.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.time.LocalDate;

public class AppointForm {

    public static void start(Stage stage) throws IOException {

        // Create Hibernate SessionFactory
        Configuration conf = new Configuration().configure().addAnnotatedClass(Appointment.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        SessionFactory sFact = conf.buildSessionFactory(reg);
        Session session = sFact.openSession();

        //Create UI Controls
        Label lblTitle = new Label("Welcome, Client");
        Label lblID = new Label("Client ID:");
        Label lblType = new Label("Service Type:");
        Label lblStation = new Label("Service Station:");
        Label lblNotes = new Label("Notes:");
        Label msgAdd = new Label("Service Logged!");
        msgAdd.setVisible(false);

        TextField txtID = new TextField();
        TextField txtType = new TextField();
        TextField txtNotes = new TextField();
        TextField txtName = new TextField();

        ChoiceBox<String> cboxStation = new ChoiceBox<>();
        cboxStation.getItems().addAll("Toronto", "Markham", "Scarborough", "Brampton", "York");
        cboxStation.setValue("Toronto");


        Button btnLog = new Button("Book Appointment");
        Button btnClear = new Button("Clear");
        Button btnLogout = new Button("Logout");

// === Output Area ===
        TextArea txtArea = new TextArea();
        txtArea.setEditable(false);

// === Layout ===
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(false);


        grid.add(lblTitle, 0, 0);
        grid.add(msgAdd, 1, 0, 4, 1);

        grid.add(lblID, 0, 1);
        grid.add(txtID, 1, 1);

        grid.add(lblType, 0, 3);
        grid.add(txtType, 1, 3);

        grid.add(lblNotes, 0, 4);
        grid.add(txtNotes, 1, 4);

        grid.add(lblStation, 0, 8);
        grid.add(cboxStation, 1, 8);

        grid.add(btnLog, 1, 9);
        grid.add(btnClear, 2, 9);
        grid.add(btnLogout, 2, 0);

        grid.add(txtArea, 0, 10, 2, 1);
        GridPane.setHalignment(txtArea, HPos.CENTER);


        Scene techForm = new Scene(grid, 600, 600);
        stage.setTitle("Technician");
        stage.setScene(techForm);
        stage.show();

        btnLog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        btnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtID.clear();
                txtType.clear();
                txtNotes.clear();
                txtName.clear();
                msgAdd.setVisible(false);
            }
        });

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
