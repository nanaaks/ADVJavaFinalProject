module ca.humber.finalproject {
    requires javafx.controls;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;


    opens ca.humber.finalproject to org.hibernate.orm.core;
    exports ca.humber.finalproject;
}