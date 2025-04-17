package ca.humber.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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

public class AdminForm {

    public static void start(Stage stage) throws IOException {

        // Create Hibernate SessionFactory
        Configuration conf = new Configuration().configure().addAnnotatedClass(Service.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        SessionFactory sFact = conf.buildSessionFactory(reg);
        Session session = sFact.openSession();

        //Create UI Controls
        Label lblTech = new Label("Technician Name:");

        TextField txtTech = new TextField();

        Button btnNewUser = new Button("Add New User");
        Button btnTechnician = new Button("Technician Activity Report");
        Button btnService = new Button("Service Reports");
        Button btnLogout = new Button("Logout");

        TextArea txtArea = new TextArea();
        txtArea.setEditable(false);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(false);

        grid.add(lblTech, 0, 1);
        grid.add(txtTech, 0, 2);
        grid.add(btnTechnician, 1, 2);
        grid.add(txtArea, 0, 3, 3, 1);
        GridPane.setHalignment(txtArea, HPos.CENTER);
        grid.add(btnService, 2, 2);
        grid.add(btnNewUser, 0, 4);
        grid.add(btnLogout, 2, 0);

        Scene techForm = new Scene(grid, 550, 350);
        stage.setTitle("Administrator");
        stage.setScene(techForm);
        stage.show();

        btnTechnician.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // get text from the Technician ID field
                String tech = txtTech.getText();
                txtArea.clear();

                List<Service> logs = session.createQuery("FROM Service WHERE name = :name", Service.class)
                        .setParameter("name", tech)
                        .list();

                if (logs.isEmpty()) {
                    txtArea.setText("No records found for technician: " + tech);
                } else {
                    for (Service s : logs) {
                        txtArea.appendText("Service ID: " + s.getId() +
                                ", VIN: " + s.getVin() +
                                ", Type: " + s.getType() +
                                ", Date: " + s.getDate() +
                                ", Status: " + s.getStatus() +
                                ", Station: " + s.getStation() +
                                "\n\n");
                    }
                }
            }
        });

        btnService.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Transaction trs = session.beginTransaction();

                // Retrieve all service records
                String hqlSearch = "FROM Service";
                Query<Service> querySearch = session.createQuery(hqlSearch, Service.class);
                List<Service> records = querySearch.getResultList();
                if (records.isEmpty()) {
                    txtArea.appendText("No records found!");
                } else {
                    for (Service service : records) {
                            txtArea.appendText("ID: " + service.getId() +
                                    ", VIN: " + service.getVin() +
                                    ", Type: " + service.getType() +
                                    ", Date: " + service.getDate() +
                                    ", Station: " + service.getStation() +
                                    "\n\n");
                        }
                    }
                }
        });


        btnNewUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    AddUser.start(stage);
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
