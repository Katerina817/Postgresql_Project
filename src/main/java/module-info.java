module org.example.postgresql_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires static lombok;

    opens org.example.postgresql_project to javafx.fxml;
    exports org.example.postgresql_project;
    exports org.example.postgresql_project.FXControllers;
    exports org.example.postgresql_project.Entities;
    opens org.example.postgresql_project.FXControllers to javafx.fxml;
}