module myjfx {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    opens LogIn to javafx.graphics, javafx.fxml, javafx.base;
    opens Search to javafx.graphics, javafx.fxml, javafx.base;
    opens Network to javafx.graphics, javafx.fxml, javafx.base;
}