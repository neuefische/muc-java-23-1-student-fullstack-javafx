package de.neuefische.mucjava231javafxstudents.controller;

import de.neuefische.mucjava231javafxstudents.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    public void createNewAccount(ActionEvent event) {
        System.out.println(emailTextField.getText() + " " + userNameTextField.getText() + " " + passwordField.getText());
    }

    public void switchToLoginView(ActionEvent event) throws IOException {
        sceneSwitchService.switchToLoginView(event);
    }
}
