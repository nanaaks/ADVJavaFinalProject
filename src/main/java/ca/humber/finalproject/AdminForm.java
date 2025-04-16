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
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;

public class AdminForm {

    public static void start(Stage stage) throws IOException {

        // Create Hibernate SessionFactory
        Configuration conf = new Configuration().configure().addAnnotatedClass(Service.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        SessionFactory sFact = conf.buildSessionFactory(reg);
        Session session = sFact.openSession();

        //Create UI Controls
        Label lblID = new Label("Technician ID:");

        TextField txtID = new TextField();

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

        grid.add(lblID, 0, 1);
        grid.add(txtID, 0, 2);
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
