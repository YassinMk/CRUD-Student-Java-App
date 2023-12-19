module com.example.studentapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.studentapp to javafx.fxml;
    exports com.example.studentapp.DAO;
    opens com.example.studentapp.DAO to javafx.fxml;
    exports com.example.studentapp.DAO.entities;
    opens com.example.studentapp.DAO.entities to javafx.fxml;
    exports com.example.studentapp.presentation;
    opens com.example.studentapp.presentation to javafx.fxml;
}