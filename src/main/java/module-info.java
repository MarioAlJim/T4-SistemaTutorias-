module com.teamfour.sistutorias {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires java.desktop;
    requires controlsfx;

    opens com.teamfour.sistutorias.presentation to javafx.fxml;
    opens com.teamfour.sistutorias.domain to javafx.fxml;
    exports com.teamfour.sistutorias.presentation;
    exports com.teamfour.sistutorias.domain;

}
