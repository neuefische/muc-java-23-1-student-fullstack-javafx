package de.neuefische.mucjava231javafxstudents.controller;

import de.neuefische.mucjava231javafxstudents.service.SceneSwitchService;
import javafx.event.ActionEvent;

import java.io.IOException;

public class CreateAccountViewController {

    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();

    public void createNewAccount(ActionEvent event) {

    }

    public void switchToLoginView(ActionEvent event) throws IOException {
        sceneSwitchService.switchToLoginView(event);
    }
}
