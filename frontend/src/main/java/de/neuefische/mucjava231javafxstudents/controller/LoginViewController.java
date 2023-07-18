package de.neuefische.mucjava231javafxstudents.controller;

import de.neuefische.mucjava231javafxstudents.service.SceneSwitchService;
import javafx.event.ActionEvent;

import java.io.IOException;

public class LoginViewController {

    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();

    public void switchToCreateAccountView(ActionEvent event) throws IOException {
        sceneSwitchService.switchToCreateAccountView(event);
    }

    public void login(ActionEvent event) {
    }
}
