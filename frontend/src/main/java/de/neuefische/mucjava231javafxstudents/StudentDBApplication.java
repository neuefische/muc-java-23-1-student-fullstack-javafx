package de.neuefische.mucjava231javafxstudents;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentDBApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("students/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.resizableProperty().set(false);
        stage.setTitle("StudentDB-JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}