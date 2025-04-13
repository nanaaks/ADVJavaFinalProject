module ca.humber.finalproject {
    requires javafx.controls;
    requires org.hibernate.orm.core;


    opens ca.humber.finalproject to org.hibernate.orm.core;
    exports ca.humber.finalproject;
}