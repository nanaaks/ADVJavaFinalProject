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

public class TechForm {

    public static void start(Stage stage) throws IOException {

        // Create Hibernate SessionFactory
        Configuration conf = new Configuration().configure().addAnnotatedClass(Service.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        SessionFactory sFact = conf.buildSessionFactory(reg);
        Session session = sFact.openSession();

        //Create UI Controls
        Label lblTitle = new Label("Welcome, " + "admin");
        Label msgAdd = new Label("Service Logged!");
        msgAdd.setVisible(false);

        Label lblID = new Label("Service ID:");
        Label lblVIN = new Label("VIN:");
        Label lblType = new Label("Service Type:");
        Label lblDate = new Label("Service Date:");
        Label lblCost = new Label("Cost:");
        Label lblStatus = new Label("Status:");
        Label lblName = new Label("Technician Name:");
        TextField txtID = new TextField();
        TextField txtVIN = new TextField();
        TextField txtType = new TextField();
        DatePicker datePicker = new DatePicker();
        TextField txtCost = new TextField();
        TextField txtStatus = new TextField();
        TextField txtName = new TextField();

        Button btnAssigned = new Button("View Assigned Vehicles");
        Button btnLog = new Button("Log Maintenance");
        Button btnClear = new Button("Clear");
        Button btnUpdate = new Button("Update Status");
        Button btnSchedule = new Button("Schedule Maintenance");
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
        grid.add(lblVIN, 0,2);
        grid.add(lblType, 0, 3);
        grid.add(lblDate, 0, 4);
        grid.add(lblCost, 0, 5);
        grid.add(lblStatus, 0, 6);
        grid.add(lblName, 0, 7);
        grid.add(msgAdd, 1,0, 4,1);
        grid.add(txtID, 1, 1);
        grid.add(txtVIN, 1, 2);
        grid.add(txtType, 1, 3);
        grid.add(datePicker, 1, 4);
        grid.add(txtCost,1, 5);
        grid.add(txtStatus,1, 6);
        grid.add(txtName,1, 7);
        grid.add(btnLog, 1, 8);
        grid.add(btnClear, 2, 8);
        grid.add(btnAssigned, 2, 2);
        grid.add(btnUpdate, 2, 3);
        grid.add(btnSchedule, 2, 4);
        grid.add(btnLogout, 2, 0);
        grid.add(txtArea, 0, 9, 2, 1);
        GridPane.setHalignment(txtArea, HPos.CENTER);

        Scene techForm = new Scene(grid, 600, 600);
        stage.setTitle("Technician");
        stage.setScene(techForm);
        stage.show();

        btnLog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Transaction trs = session.beginTransaction();
                int id = Integer.parseInt(txtID.getText());
                int vin = Integer.parseInt(txtVIN.getText());
                String type = txtType.getText();
                LocalDate date = datePicker.getValue();
                double cost = Double.parseDouble(txtCost.getText());
                String status = txtStatus.getText();
                String name = txtName.getText();
                Service service = new Service(id, vin, type, date, cost, status, name);
                session.persist(service);
                trs.commit();
                msgAdd.setTextFill(Color.GREEN);
                msgAdd.setVisible(true);
            }
        });

        btnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtID.clear();
                txtVIN.clear();
                txtType.clear();
                datePicker.setValue(null);
                txtCost.clear();
                txtStatus.clear();
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
