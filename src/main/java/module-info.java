module org.reimagnus.bonfire {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.reimagnus.bonfire to javafx.fxml;
    exports org.reimagnus.bonfire;
}