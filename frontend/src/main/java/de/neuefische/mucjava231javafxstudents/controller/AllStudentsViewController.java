package de.neuefische.mucjava231javafxstudents.controller;

import de.neuefische.mucjava231javafxstudents.model.Student;
import de.neuefische.mucjava231javafxstudents.security.AuthenticationService;
import de.neuefische.mucjava231javafxstudents.service.SceneSwitchService;
import de.neuefische.mucjava231javafxstudents.service.StudentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class AllStudentsViewController {

    @FXML
    private ListView<Student> listView;
    @FXML
    private Text text;
    @FXML
    private Button editStudentButton;
    @FXML
    private Button deleteStudentButton;

    private final StudentService studentService = StudentService.getInstance();
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();

    public void initialize() {
        // Hier stellen wir die Daten der ListView ein
        //  listView.getItems() = Liste der Elemente aus listView holen
        // .addAll() = Elemente zur Liste hinzufügen
        List<Student> allStudents = studentService.getAllStudents();
        listView.getItems()
                .addAll(allStudents);

        listView.getSelectionModel()
                .selectedItemProperty()
                // Listener der etwas macht
                .addListener(
                        (observableValue, s, t1) -> {
                            text.setText(listView.getSelectionModel().getSelectedItem().firstName() + " " + listView.getSelectionModel().getSelectedItem().lastName());
                            editStudentButton.setDisable(false);
                            deleteStudentButton.setDisable(false);
                        }
                );
    }

    @FXML
    public void switchToWelcomeView(ActionEvent event) throws IOException {
        sceneSwitchService.switchToWelcomeView(event);
    }

    @FXML
    public void switchToCreateNewStudentView(ActionEvent event) throws IOException {
        sceneSwitchService.switchToCreateNewStudentView(event);
    }

    @FXML
    public void switchToEditSelectedStudentView(ActionEvent event) throws IOException {
        sceneSwitchService.switchToEditSelectedStudentView(event, listView.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void deleteSelectedStudent() {
        authenticationService.getSessionId();
        studentService.deleteStudent(listView.getSelectionModel().getSelectedItem().matriculationNumber(), listView, authenticationService.getSessionId());
    }
}
