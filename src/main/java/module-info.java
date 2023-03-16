module com.teamfour.sistutorias {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.teamfour.sistutorias.presentation to javafx.fxml;
    exports com.teamfour.sistutorias.presentation;
    opens com.teamfour.sistutorias.domain to javafx.fxml;
    exports com.teamfour.sistutorias.domain;
}
