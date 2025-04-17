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

public class UpdateStatus {
    public static void start(Stage stage) throws IOException {
        // Create Hibernate SessionFactory
        Configuration conf = new Configuration().configure().addAnnotatedClass(Service.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        SessionFactory sFact = conf.buildSessionFactory(reg);
        Session session = sFact.openSession();

        Label lblTitle = new Label("Welcome, Technician");
        Label msgAdd = new Label("Service Status Updated!");
        msgAdd.setVisible(false);

        Label lblID = new Label("Service ID:");
        Label lblVIN = new Label("VIN:");
        Label lblType = new Label("Service Type:");
        Label lblDate = new Label("Service Date:");
        Label lblStatus = new Label("Status:");
        Label lblName = new Label("Technician Name:");
        Label lblStation = new Label("Service Station:");

        ChoiceBox<String> cboxStation = new ChoiceBox<>();
        cboxStation.getItems().addAll("Toronto", "Markham", "Scarborough", "Brampton", "York");
        cboxStation.setValue("Toronto");

        TextField txtID = new TextField();
        TextField txtVIN = new TextField();
        TextField txtType = new TextField();
        DatePicker datePicker = new DatePicker();

        ChoiceBox<String> cboxStation1 = new ChoiceBox<>();
        cboxStation1.getItems().addAll("Pending", "In Service", "Completed");
        cboxStation1.setValue("Pending");

        TextField txtName = new TextField();

        Button btnClear = new Button("Clear");
        Button btnLog = new Button("Update Maintenance");
        Button btnLogout = new Button("Logout");

        TextArea txtArea = new TextArea();
        txtArea.setEditable(false);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(false);

        grid.add(lblTitle, 0, 0);
        grid.add(lblID, 0, 1);
        grid.add(lblVIN, 0, 2);
        grid.add(lblType, 0, 3);
        grid.add(lblDate, 0, 4);
        grid.add(lblStatus, 0, 5);
        grid.add(lblName, 0, 6);
        grid.add(lblStation, 0, 7);
        grid.add(msgAdd, 1, 0, 4, 1);
        grid.add(txtID, 1, 1);
        grid.add(txtVIN, 1, 2);
        grid.add(txtType, 1, 3);
        grid.add(datePicker, 1, 4);
        grid.add(cboxStation1, 1, 5);
        grid.add(btnLog, 1, 8);
        grid.add(txtName, 1, 6);
        grid.add(cboxStation, 1, 7);
        grid.add(btnClear, 2, 8);
        grid.add(btnLogout, 2, 0);
        grid.add(txtArea, 0, 10, 2, 1);
        GridPane.setHalignment(txtArea, HPos.CENTER);

        Scene techForm = new Scene(grid, 600, 600);
        stage.setTitle("Technician");
        stage.setScene(techForm);
        stage.show();

        btnLog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Transaction trs = session.beginTransaction();
                int id = Integer.parseInt(txtID.getText());
                Service service = session.get(Service.class, id);
                if (service != null) {
                    service.setStatus(cboxStation1.getValue());
                    session.update(service);
                    msgAdd.setText("Service status updated successfully!");
                    msgAdd.setTextFill(Color.GREEN);
                    msgAdd.setVisible(true);
                    txtArea.setText(service.toString());
                } else {
                    msgAdd.setText("Service ID not found.");
                    msgAdd.setTextFill(Color.RED);
                    msgAdd.setVisible(true);
                }
                trs.commit();
            }
        });

        btnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtID.clear();
                txtVIN.clear();
                txtType.clear();
                datePicker.setValue(null);
                txtName.clear();
                cboxStation1.setValue("Pending");
                cboxStation.setValue("Toronto");
                msgAdd.setVisible(false);
                txtArea.clear();
            }
        });

        btnLogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    TechForm.start(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
