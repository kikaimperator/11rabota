module org.example.hzhzhzhzhzpk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;


    opens org.example.hzhzhzhzhzpk to javafx.fxml;
    exports org.example.hzhzhzhzhzpk;
}