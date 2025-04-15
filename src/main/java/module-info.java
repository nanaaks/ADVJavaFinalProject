module ca.humber.finalproject {
    requires javafx.controls;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;


    opens ca.humber.finalproject to org.hibernate.orm.core;
    exports ca.humber.finalproject;
}