module ca.humber.finalproject {
    requires javafx.controls;
    requires org.hibernate.orm.core;


    opens ca.humber.finalproject to javafx.fxml;
    exports ca.humber.finalproject;
}