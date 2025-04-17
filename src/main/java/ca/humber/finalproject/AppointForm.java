package ca.humber.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
import java.time.LocalDate;

public class AppointForm {

    public static String userID;

    public static void start(Stage stage) throws IOException {

        userID = Main.userID;

        // Create Hibernate SessionFactory
        Configuration conf = new Configuration().configure().addAnnotatedClass(Appointment.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        SessionFactory sFact = conf.buildSessionFactory(reg);
        Session session = sFact.openSession();

        //Create UI Controls
        Label lblTitle = new Label("Welcome, Client");
        Label lblID = new Label("Client ID:");
        Label lblType = new Label("Service Type:");
        Label lblDate = new Label("Service Date:");
        Label lblStation = new Label("Service Station:");
        Label lblNotes = new Label("Notes:");
        Label msgAdd = new Label("Appointment Booked!");
        msgAdd.setVisible(false);

        TextField txtID = new TextField();
        TextField txtType = new TextField();
        TextField txtNotes = new TextField();
        DatePicker datePicker = new DatePicker();
        ChoiceBox<String> cboxStation = new ChoiceBox<>();
        cboxStation.getItems().addAll("Toronto", "Markham", "Scarborough", "Brampton", "York");
        cboxStation.setValue("Toronto");


        Button btnBook = new Button("Book Appointment");
        Button btnClear = new Button("Clear");
        Button btnLogout = new Button("Back");

        TextArea txtOutput = new TextArea();
        txtOutput.setEditable(false);


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(false);


        grid.add(lblTitle, 0, 0, 2, 1);
        grid.add(msgAdd, 0, 1, 2, 1);

        grid.add(lblID, 0, 2);
        grid.add(txtID, 1, 2);

        grid.add(lblType, 0, 3);
        grid.add(txtType, 1, 3);

        grid.add(lblDate, 0, 4);
        grid.add(datePicker, 1, 4);

        grid.add(lblNotes, 0, 5);
        grid.add(txtNotes, 1, 5);

        grid.add(lblStation, 0, 6);
        grid.add(cboxStation, 1, 6);

        // Buttons aligned horizontally
        HBox buttons = new HBox(15);
        buttons.getChildren().addAll(btnBook, btnClear);
        grid.add(buttons, 1, 7);

// Text output area
        grid.add(txtOutput, 0, 8, 2, 1);
        GridPane.setHalignment(txtOutput, HPos.CENTER);

// Logout button top-right
        grid.add(btnLogout, 2, 0);

        Scene appoint = new Scene(grid, 600, 600);
        stage.setTitle("Book an Appointment");
        stage.setScene(appoint);
        stage.show();

        btnBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Transaction trs = session.beginTransaction();
                int id = Integer.parseInt(txtID.getText());
                String type = txtType.getText();
                String notes = txtType.getText();
                LocalDate date = datePicker.getValue();
                String station = cboxStation.getValue();
                Appointment appointment = new Appointment(id, type, notes, date, station);
                session.persist(appointment);
                trs.commit();
                msgAdd.setTextFill(Color.GREEN);
                msgAdd.setVisible(true);
            }
        });

        btnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtID.clear();
                txtType.clear();
                txtNotes.clear();
                datePicker.setValue(null);
                msgAdd.setVisible(false);
            }
        });

        btnLogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    ClientForm.start(stage, userID);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
