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
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class TechForm {

    public static void start(Stage stage) throws IOException {

        // Create Hibernate SessionFactory
        Configuration conf = new Configuration().configure().addAnnotatedClass(Service.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        SessionFactory sFact = conf.buildSessionFactory(reg);
        Session session = sFact.openSession();

        Configuration conf2 = new Configuration().configure().addAnnotatedClass(Maintenance.class);
        ServiceRegistry reg2 = new StandardServiceRegistryBuilder().applySettings(conf2.getProperties()).build();
        SessionFactory sFact2 = conf2.buildSessionFactory(reg2);
        Session session2 = sFact2.openSession();

        //Create UI Controls
        Label lblTitle = new Label("Welcome");
        Label msgAdd = new Label("Service Logged!");
        msgAdd.setVisible(false);

        Label lblID = new Label("Service ID:");
        Label lblVIN = new Label("VIN:");
        Label lblType = new Label("Service Type:");
        Label lblDate = new Label("Service Date:");
        Label lblCost = new Label("Cost:");
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
        TextField txtCost = new TextField();
        ChoiceBox<String> cboxStation1 = new ChoiceBox<>();
        cboxStation1.getItems().addAll("Pending", "In Service", "Completed");
        cboxStation1.setValue("Pending");
        TextField txtName = new TextField();

        Button btnAssigned = new Button("View Assigned Vehicles");
        Button btnLog = new Button("Log Maintenance");
        Button btnClear = new Button("Clear");
        Button btnUpdate = new Button("Update Status");
        Button btnSchedule = new Button("Check Reminders");
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
        grid.add(lblStation, 0, 8);
        grid.add(msgAdd, 1,0, 4,1);
        grid.add(txtID, 1, 1);
        grid.add(txtVIN, 1, 2);
        grid.add(txtType, 1, 3);
        grid.add(datePicker, 1, 4);
        grid.add(txtCost,1, 5);
        grid.add(cboxStation1,1, 6);
        grid.add(txtName,1, 7);
        grid.add(cboxStation, 1,8);
        grid.add(btnLog, 1, 9);
        grid.add(btnClear, 2, 9);
        grid.add(btnAssigned, 2, 2);
        grid.add(btnUpdate, 2, 3);
        grid.add(btnSchedule, 2, 4);
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
                Transaction trs1 = session.beginTransaction();
                int id = Integer.parseInt(txtID.getText());
                int vin = Integer.parseInt(txtVIN.getText());
                String type = txtType.getText();
                LocalDate date = datePicker.getValue();
                double cost = Double.parseDouble(txtCost.getText());
                String status = cboxStation1.getValue();
                String name = txtName.getText();
                String station = cboxStation.getValue();
                Service service = new Service(id, vin, type, date, cost, status, name, station);
                session.persist(service);
                trs1.commit();
                Transaction trs2 = session2.beginTransaction();
                Maintenance maintenance = new Maintenance(vin,type, name,cost,date);
                session2.persist(maintenance);
                txtArea.setText(maintenance.toString());
                trs2.commit();
                msgAdd.setTextFill(Color.GREEN);
                msgAdd.setVisible(true);
            }
        });

        btnAssigned.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Transaction trs = session.beginTransaction();

                // Search for service records given technician name
                String target = txtName.getText();
                String hqlSearch = "FROM Service WHERE name = :target";
                Query<Service> querySearch = session.createQuery(hqlSearch, Service.class);
                querySearch.setParameter("target", target);
                List<Service> records = querySearch.getResultList();
                if (records.isEmpty()) {
                    txtArea.appendText("No records found!");
                } else {
                    for (Service service : records){
                        txtArea.appendText("VIN: " + service.getVin() +
                                "\n\n");
                    }
                }
                trs.commit();
            }
        });


        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    UpdateStatus updateStatus = new UpdateStatus();
                    updateStatus.start(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });


        btnSchedule.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ScheduleMaintenance scheduleMaintenance = new ScheduleMaintenance();
                scheduleMaintenance.start(stage);
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
                txtName.clear();
                msgAdd.setVisible(false);
                txtArea.clear();
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
