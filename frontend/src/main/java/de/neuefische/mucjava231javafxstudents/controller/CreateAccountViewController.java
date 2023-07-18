package de.neuefische.mucjava231javafxstudents.controller;

import de.neuefische.mucjava231javafxstudents.security.AuthenticationService;
import de.neuefische.mucjava231javafxstudents.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccountViewController {

    @FXML
    private TextField emailTextField;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessageLabel;

    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();

    public void createNewAccount(ActionEvent event) throws IOException {
        if (authenticationService.createNewAccount(emailTextField.getText(), userNameTextField.getText(), passwordField.getText())) {
            errorMessageLabel.setText("Registration successful. Welcome " + authenticationService.getUsername() + "!");

            System.out.println("CreateAccountViewController: " + authenticationService.getSessionId());

            sceneSwitchService.switchToWelcomeView((Stage) userNameTextField.getScene().getWindow());
        } else {
            errorMessageLabel.setText(authenticationService.getErrorMessage());
        }
    }

    public void switchToLoginView(ActionEvent event) throws IOException {
        sceneSwitchService.switchToLoginView(event);
    }
}
