module com.teamfour.sistutorias {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.teamfour.sistutorias.presentation to javafx.fxml;
    exports com.teamfour.sistutorias.presentation;
}
