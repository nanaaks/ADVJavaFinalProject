package ca.humber.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class AddUser {

    public static void start(Stage stage) throws IOException {

        // Create Hibernate SessionFactory
        Configuration conf = new Configuration().configure().addAnnotatedClass(User.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        SessionFactory sFact = conf.buildSessionFactory(reg);
        Session session = sFact.openSession();

        //Create UI Controls
        Label msgAdd = new Label("User Added!");
        msgAdd.setVisible(false);

        Label lblID = new Label("User ID:");
        Label lblPassword = new Label("Password:");
        Label lblName = new Label("Name:");
        Label lblRole = new Label("Role:");

        TextField txtID = new TextField();
        TextField txtPassword = new TextField();
        TextField txtName = new TextField();
        ChoiceBox<String> cboxStation = new ChoiceBox<>();
        cboxStation.getItems().addAll("Administrator", "Technician","Client");
        cboxStation.setValue("Administrator");

        Button btnAdd = new Button("Add User");
        Button btnClear = new Button("Clear");
        Button btnCancel = new Button("Cancel");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(false);
        grid.add(msgAdd, 1,0, 4,1);
        grid.add(lblID, 0, 1);
        grid.add(lblPassword, 0,2);
        grid.add(lblName, 0, 3);
        grid.add(lblRole, 0, 4);
        grid.add(txtID, 1, 1);
        grid.add(txtPassword, 1, 2);
        grid.add(txtName, 1, 3);
        grid.add(cboxStation,1, 4);
        grid.add(btnAdd, 0, 5);
        grid.add(btnClear, 1, 5);
        grid.add(btnCancel, 2, 5);

        Scene addForm = new Scene(grid, 400, 400);
        stage.setTitle("Add New User");
        stage.setScene(addForm);
        stage.show();

        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Transaction trs = session.beginTransaction();
                String id = txtID.getText();
                String pass = txtPassword.getText();
                String name = txtName.getText();
                String role = cboxStation.getValue();
                User user = new User(id, pass, name, role);
                session.persist(user);
                trs.commit();
                msgAdd.setTextFill(Color.GREEN);
                msgAdd.setVisible(true);
            }
        });



        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    AdminForm.start(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
