package de.neuefische.mucjava231javafxstudents.controller;

import de.neuefische.mucjava231javafxstudents.service.AuthenticationService;
import de.neuefische.mucjava231javafxstudents.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController {

    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessageLabel;

    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();

    public void switchToCreateAccountView(ActionEvent event) throws IOException {
        sceneSwitchService.switchToCreateAccountView(event);
    }

    public void login(ActionEvent event) throws IOException {
        if (authenticationService.login(userNameTextField.getText(), passwordField.getText())) {
            errorMessageLabel.setText("Login successful. Welcome back, " + authenticationService.getUsername() + "!");

            System.out.println("LoginController: " + authenticationService.getSessionId());

            sceneSwitchService.switchToWelcomeView((Stage) userNameTextField.getScene().getWindow());
        } else {
            errorMessageLabel.setText(authenticationService.getErrorMessage());
        }
    }
}
