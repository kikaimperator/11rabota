module org.myproject.hzhzhz2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;


    opens org.myproject.hzhzhz2 to javafx.fxml;
    exports org.myproject.hzhzhz2;
}