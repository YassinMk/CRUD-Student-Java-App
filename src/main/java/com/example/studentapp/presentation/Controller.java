package com.example.studentapp.presentation;

import com.example.studentapp.DAO.StudentDaoImp;
import com.example.studentapp.DAO.VilleDaoImpl;
import com.example.studentapp.DAO.entities.Student;
import com.example.studentapp.DAO.entities.Ville;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    @FXML
    TextField name, lastName, id, email, searchBar;

    @FXML
    Label alert;
    @FXML
    DatePicker date;
    @FXML
    Button save, delete, exit, searchBtn;
    @FXML
    ComboBox<String> city;
    @FXML
    TableView<Student> tableStudent;
    @FXML
    TableColumn<Student, String> columnId = new TableColumn<>();
    @FXML
    TableColumn<Student, String> columnName = new TableColumn<>();
    @FXML
    TableColumn<Student, String> columnLastName = new TableColumn<>();
    @FXML
    TableColumn<Student, String> columnEmail = new TableColumn<>();
    @FXML
    TableColumn<Student, String> columnDate = new TableColumn<>();
    @FXML
    TableColumn<Student, String> columnCity = new TableColumn<>();


    public void initialize() {
        initializeComboBox();
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnCity.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity().getNom()));
        getSelected();
        getAllStudent();
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                searchBarEmpty();
            }
        });
    }

    @FXML
    void saveStudent() {

        int stdId = Integer.parseInt(id.getText());
        if (name.getText().isEmpty() || lastName.getText().isEmpty() || email.getText().isEmpty() || (date.getValue() == null) || (city.getValue() == null)) {
            alert.setText("Svp remplir tous les champs");
            Timeline timeline = new Timeline(new KeyFrame(
                    javafx.util.Duration.millis(3000),
                    ae -> alert.setText("")));
            timeline.play();
        } else if (email.getText().indexOf("@") == -1) {
            alert.setText("Svp entrer un email valide");
            Timeline timeline = new Timeline(new KeyFrame(
                    javafx.util.Duration.millis(3000),
                    ae -> alert.setText("")));
            timeline.play();

        } else {
            StudentDaoImp studentDao = new StudentDaoImp();
            VilleDaoImpl villeDao = new VilleDaoImpl();

            Student e = new Student(stdId, name.getText(), lastName.getText(), email.getText(), date.getValue().toString(), villeDao.getByName(city.getValue()));
            studentDao.create(e);
            tableStudent.getItems().add(e);
            id.clear();
            name.clear();
            lastName.clear();
            email.clear();
            date.setValue(null);
            city.setValue(null);
        }

    }

    void initializeComboBox(){
        VilleDaoImpl vdo = new VilleDaoImpl();
        List<Ville> villes = vdo.getAll();
        List<String> cityNames = new ArrayList<>();
        for (Ville ville : villes) {
            cityNames.add(ville.getNom());
        }
        city.getItems().addAll(cityNames);
    }

    void getSelected() {
        tableStudent.setOnMouseClicked(event -> {
            id.setText(tableStudent.getSelectionModel().getSelectedItem().getId().toString());
            name.setText(tableStudent.getSelectionModel().getSelectedItem().getName());
            lastName.setText(tableStudent.getSelectionModel().getSelectedItem().getLastName());
            city.setValue(tableStudent.getSelectionModel().getSelectedItem().getCity().getNom());
            email.setText(tableStudent.getSelectionModel().getSelectedItem().getEmail());
            date.setValue(LocalDate.parse(tableStudent.getSelectionModel().getSelectedItem().getDate()));

        });
        clearText();
    }

    void getAllStudent() {
        StudentDaoImp studentDao = new StudentDaoImp();
        tableStudent.getItems().addAll(studentDao.getAll());
    }

    @FXML
    void exitApp() {
        Platform.exit();
    }

    @FXML
    void deleteStudent() {
        StudentDaoImp sdi = new StudentDaoImp();
        sdi.delete(Integer.parseInt(id.getText()));
        tableStudent.getItems().remove(tableStudent.getSelectionModel().getSelectedItem());
    }

    //clear text afte
    @FXML
    void search() {
        String inputText = searchBar.getText().trim();
        if (!inputText.isEmpty()) {
            StudentDaoImp sdi = new StudentDaoImp();
            List<Student> searchResults = sdi.searchQuery(inputText);

            if (!searchResults.isEmpty()) {
                ObservableList<Student> students = tableStudent.getItems();
                students.clear(); // Clear existing items in the table

                students.addAll(searchResults);

                // Optional: Select the first result in the table
                tableStudent.getSelectionModel().selectFirst();
            } else {
                alert.setText("No student found with the given criteria: " + inputText);
                Timeline timeline = new Timeline(new KeyFrame(
                        javafx.util.Duration.millis(3000),
                        ae -> alert.setText("")));
                timeline.play();
            }
        } else {
            alert.setText("Please enter a valid search criteria.");
            Timeline timeline = new Timeline(new KeyFrame(
                    javafx.util.Duration.millis(3000),
                    ae -> alert.setText("")));
            timeline.play();
        }
    }

    @FXML
    void searchBarEmpty() {
        if (searchBar.getText().isEmpty()) {
            tableStudent.getSelectionModel().clearSelection();
            getAllStudent();
        }
    }


    void clearText(){
        id.clear();
        name.clear();
        lastName.clear();
        email.clear();
        date.setValue(null);
        city.setValue(null);
    }


}