module org.reimagnus.bonfire {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens org.reimagnus.bonfire to javafx.fxml;
    exports org.reimagnus.bonfire;
}