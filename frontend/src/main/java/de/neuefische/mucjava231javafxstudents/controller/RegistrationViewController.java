package de.neuefische.mucjava231javafxstudents.controller;

import de.neuefische.mucjava231javafxstudents.model.Student;
import de.neuefische.mucjava231javafxstudents.model.StudentWithoutMatriculationNumber;
import de.neuefische.mucjava231javafxstudents.security.AuthenticationService;
import de.neuefische.mucjava231javafxstudents.service.SceneSwitchService;
import de.neuefische.mucjava231javafxstudents.service.StudentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegistrationViewController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField courseOfStudiesField;
    @FXML
    private CheckBox gdprCheckbox;
    @FXML
    private Label labelErrorMessage;

    private final StudentService studentService = StudentService.getInstance();
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();

    @FXML
    public void switchToWelcomeView(ActionEvent event) throws IOException {
        sceneSwitchService.switchToWelcomeView(event);
    }

    @FXML
    public void switchToRegistrationConfirmationView(ActionEvent event) throws IOException {
        if (isEveryTextFieldValid()) {
            StudentWithoutMatriculationNumber studentData = new StudentWithoutMatriculationNumber(firstNameField.getText(), lastNameField.getText(), emailField.getText(), courseOfStudiesField.getText());
            Student newStudentWithId = studentService.createNewStudent(studentData, authenticationService.getSessionId());

            sceneSwitchService.switchToShowRegisteredStudentView(event, newStudentWithId);
        }
    }

    private boolean isEveryTextFieldValid() {
        String EMAIL_PATTERN_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$";
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN_REGEX);

        if (firstNameField.getText() == null || firstNameField.getText().isEmpty()) {
            labelErrorMessage.setText("Bitte gib einen Vornamen ein, der mind. 2 Zeichen lang ist");
            return false;
        } else if (lastNameField.getText() == null || lastNameField.getText().isEmpty()) {
            labelErrorMessage.setText("Bitte gib einen Nachnamen ein, der mind. 2 Zeichen lang ist");
            return false;
        } else if (!emailPattern.matcher(emailField.getText()).matches()) {
            labelErrorMessage.setText("Bitte gib eine gültige E-Mail-Adresse im Format <vorname@nachname.de> ein");
            return false;
        } else if (courseOfStudiesField.getText() == null || courseOfStudiesField.getText().isEmpty()) {
            labelErrorMessage.setText("Bitte gib einen Studiengang ein, der mind. 2 Zeichen lang ist");
            return false;
        } else if (!gdprCheckbox.isSelected()) {
            labelErrorMessage.setText("Bitte akzeptiere die Datenschutzerklärung");
            return false;
        } else {
            labelErrorMessage.setText("");
            return true;
        }
    }
}
