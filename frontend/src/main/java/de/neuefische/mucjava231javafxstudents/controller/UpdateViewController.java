package de.neuefische.mucjava231javafxstudents.controller;

import de.neuefische.mucjava231javafxstudents.model.Student;
import de.neuefische.mucjava231javafxstudents.security.AuthenticationService;
import de.neuefische.mucjava231javafxstudents.service.SceneSwitchService;
import de.neuefische.mucjava231javafxstudents.service.StudentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class UpdateViewController {

    private String matriculationNumber;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField courseOfStudiesField;
    @FXML
    private Label labelErrorMessage;

    private final StudentService studentService = StudentService.getInstance();
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();

    public void setStudentDataInFields(Student studentToEdit) {
        this.matriculationNumber = studentToEdit.matriculationNumber();
        firstNameField.setText(studentToEdit.firstName());
        lastNameField.setText(studentToEdit.lastName());
        emailField.setText(studentToEdit.email());
        courseOfStudiesField.setText(studentToEdit.courseOfStudies());
    }

    @FXML
    public void switchToWelcomeView(ActionEvent event) throws IOException {
        sceneSwitchService.switchToWelcomeView(event);
    }

    @FXML
    public void switchToShowRegisteredStudentView(ActionEvent event) throws IOException {
        if (isEveryTextFieldValid()) {
            Student studentData = new Student(
                    matriculationNumber,
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    courseOfStudiesField.getText()
            );
            Student updatedStudent = studentService.updateStudent(studentData, authenticationService.getSessionId());

            sceneSwitchService.switchToShowRegisteredStudentView(event, updatedStudent);
        }
    }

    private boolean isEveryTextFieldValid() {
        if (firstNameField.getText() == null || firstNameField.getText().isEmpty()) {
            labelErrorMessage.setText("Bitte gib einen Vornamen ein");
            return false;
        } else if (lastNameField.getText() == null || lastNameField.getText().isEmpty()) {
            labelErrorMessage.setText("Bitte gib einen Nachnamen ein");
            return false;
        } else if (emailField.getText() == null || emailField.getText().isEmpty()) {
            labelErrorMessage.setText("Bitte gib eine E-Mail-Adresse ein");
            return false;
        } else if (courseOfStudiesField.getText() == null || courseOfStudiesField.getText().isEmpty()) {
            labelErrorMessage.setText("Bitte gib einen Studiengang ein");
            return false;
        } else {
            labelErrorMessage.setText("");
            return true;
        }
    }
}
