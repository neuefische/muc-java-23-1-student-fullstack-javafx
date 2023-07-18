package de.neuefische.mucjava231javafxstudents.service;

import de.neuefische.mucjava231javafxstudents.controller.RegistrationConfirmationViewController;
import de.neuefische.mucjava231javafxstudents.controller.UpdateViewController;
import de.neuefische.mucjava231javafxstudents.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitchService {

    private static SceneSwitchService instance;

    public static synchronized SceneSwitchService getInstance() {
        if (instance == null) {
            instance = new SceneSwitchService();
        }
        return instance;
    }

    public void switchToWelcomeView(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/neuefische/mucjava231javafxstudents/students/welcome-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void switchToWelcomeView(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/neuefische/mucjava231javafxstudents/students/welcome-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void switchToCreateNewStudentView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/neuefische/mucjava231javafxstudents/students/registration-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void switchToShowRegisteredStudentView(ActionEvent event, Student updatedStudent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/neuefische/mucjava231javafxstudents/students/registration-confirmation-view.fxml"));
        Parent root = loader.load();

        RegistrationConfirmationViewController registrationConfirmationViewController = loader.getController();
        registrationConfirmationViewController.setSelectedStudent(updatedStudent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void switchToEditSelectedStudentView(ActionEvent event, Student selectedStudent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/neuefische/mucjava231javafxstudents/students/update-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        UpdateViewController updateViewController = loader.getController();
        updateViewController.setStudentDataInFields(selectedStudent);

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void switchToListAllStudentsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/neuefische/mucjava231javafxstudents/students/all-students-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void switchToLoginView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/neuefische/mucjava231javafxstudents/students/login-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void switchToCreateAccountView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/neuefische/mucjava231javafxstudents/students/create-account-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }
}
