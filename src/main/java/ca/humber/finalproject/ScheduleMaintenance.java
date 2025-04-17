package ca.humber.finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.time.LocalDate;
import java.util.List;

public class ScheduleMaintenance {

    public static void start(Stage stage) {

        // Connect to database
        Configuration conf = new Configuration().configure().addAnnotatedClass(Service.class);
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        SessionFactory sFact = conf.buildSessionFactory(reg);
        Session session = sFact.openSession();

        // UI Controls
        Label lblTitle = new Label("Maintenance Reminder");
        Label lblTech = new Label("Select Technician:");
        ChoiceBox<String> cboxTech = new ChoiceBox<>();

        List<String> techs = session.createQuery("SELECT DISTINCT name FROM Service", String.class).list();
        cboxTech.getItems().addAll(techs);

        Label lblStation = new Label("Select Station:");
        ChoiceBox<String> cboxStation = new ChoiceBox<>();
        cboxStation.getItems().addAll("Toronto", "Markham", "Scarborough", "Brampton", "York");
        cboxStation.setValue("Toronto");

        Button btnLoad = new Button("Check Reminders");
        Button btnBack = new Button("Back");
        TextArea txtResult = new TextArea();
        txtResult.setEditable(false);

        // Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(15);

        grid.add(lblTitle, 0, 0, 2, 1);
        grid.add(lblTech, 0, 1);
        grid.add(cboxTech, 1, 1);
        grid.add(lblStation, 0, 2);
        grid.add(cboxStation, 1, 2);
        grid.add(btnLoad, 1, 3);
        grid.add(txtResult, 0, 4, 2, 1);
        grid.add(btnBack, 1, 5);

        // Scene
        Scene scene = new Scene(grid, 500, 400);
        stage.setTitle("Maintenance Reminders");
        stage.setScene(scene);
        stage.show();

        // Button to load reminders
        btnLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String tech = cboxTech.getValue();
                String station = cboxStation.getValue();
                txtResult.clear();

                // Get all completed services
                List<Service> services = session.createQuery("FROM Service WHERE status = 'Completed'", Service.class).list();

                // Check which ones are older than 6 months
                for (Service service : services) {
                    if (service.getDate().isBefore(LocalDate.now().minusMonths(6)) &&
                            service.getName().equalsIgnoreCase(tech) &&
                            service.getStation().equalsIgnoreCase(station)) {

                        txtResult.appendText("ID: " + service.getId() +
                                ", VIN: " + service.getVin() +
                                ", Type: " + service.getType() +
                                ", Date: " + service.getDate() +
                                ", Station: " + service.getStation() +
                                "\n\n");
                    }
                }

                if (txtResult.getText().isEmpty()) {
                    txtResult.setText("No reminders found for this technician at " + station + ".");
                }
            }
        });


        // Back Button
        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    TechForm.start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
