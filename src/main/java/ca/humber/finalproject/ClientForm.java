package ca.humber.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import java.util.List;

public class ClientForm {

    public static void start(Stage stage, String client) throws IOException {

        // Create Hibernate SessionFactory
        Configuration conf = new Configuration().configure().addAnnotatedClass(Vehicle.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        SessionFactory sFact = conf.buildSessionFactory(reg);
        Session session = sFact.openSession();

        // Create Hibernate SessionFactory
        Configuration conf2 = new Configuration().configure().addAnnotatedClass(Maintenance.class);
        ServiceRegistry reg2 = new StandardServiceRegistryBuilder().applySettings(conf2.getProperties()).build();
        SessionFactory sFact2 = conf2.buildSessionFactory(reg2);
        Session session2 = sFact2.openSession();

        //Create UI Controls
        Label lblTitle = new Label("Welcome, " + "Client");
        Label msgAdd = new Label("Vehicle Added!");
        msgAdd.setVisible(false);
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
        Button btnClear = new Button("Clear");
        Button btnMaintain = new Button("View Maintenance History");
        Button btnSchedule = new Button("Schedule Appointments");
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
        grid.add(lblVIN, 0, 1);
        grid.add(lblMake, 0, 2);
        grid.add(lblModel, 0, 3);
        grid.add(lblYear, 0, 4);
        grid.add(lblMileage, 0, 5);
        grid.add(lblPlate, 0, 6);
        grid.add(msgAdd, 1,0, 4,1);
        grid.add(txtVIN, 1, 1);
        grid.add(txtMake, 1, 2);
        grid.add(txtModel, 1, 3);
        grid.add(txtYear,1, 4);
        grid.add(txtMileage,1, 5);
        grid.add(txtPlate,1, 6);
        grid.add(btnRegister, 1, 7);
        grid.add(btnClear, 2, 7);
        grid.add(btnSchedule, 2, 1);
        grid.add(btnMaintain, 2,2);
        grid.add(txtArea, 2, 3, 1, 4);
        GridPane.setHalignment(txtArea, HPos.CENTER);
        grid.add(btnLogout, 2, 0);

        Scene clientForm = new Scene(grid, 800, 550);
        stage.setTitle("Client");
        stage.setScene(clientForm);
        stage.show();

        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Transaction trs = session.beginTransaction();
                int vin = Integer.parseInt(txtVIN.getText());;
                String make = txtMake.getText();
                String model = txtModel.getText();
                int year = Integer.parseInt(txtYear.getText());;
                double mileage = Double.parseDouble(txtMileage.getText());
                String plate = txtPlate.getText();
                String owner = client;
                Vehicle vehicle = new Vehicle(vin, make, model, year, mileage, plate, owner);
                session.persist(vehicle);
                trs.commit();
                msgAdd.setTextFill(Color.GREEN);
                msgAdd.setVisible(true);
            }
        });

        btnMaintain.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Transaction trs = session2.beginTransaction();

                // Search for maintenance records given VIN
                int target = Integer.parseInt(txtVIN.getText());;
                String hqlSearch = "FROM Maintenance WHERE vin = :target";
                Query<Maintenance> querySearch = session2.createQuery(hqlSearch, Maintenance.class);
                querySearch.setParameter("target", target);
                List<Maintenance> records = querySearch.getResultList();
                for (Maintenance maintenance : records){
                    txtArea.appendText("Service Type: " + maintenance.getServicetype() +
                            ", Technician: " + maintenance.getTechnicianname() +
                            ", Cost: " + maintenance.getCost() +
                            ", Date: " + maintenance.getDate() +
                            "\n\n");
                }
                trs.commit();
            }
        });


        btnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtVIN.clear();
                txtMake.clear();
                txtModel.clear();
                txtYear.clear();
                txtMileage.clear();
                txtPlate.clear();
                msgAdd.setVisible(false);
            }
        });

        btnSchedule.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AppointForm.start(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
