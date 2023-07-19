package de.neuefische.mucjava231javafxstudents.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.mucjava231javafxstudents.model.Student;
import de.neuefische.mucjava231javafxstudents.model.StudentWithoutMatriculationNumber;
import javafx.application.Platform;
import javafx.scene.control.ListView;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.net.http.HttpClient;

public class StudentService {

    private static StudentService instance;
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String STUDENTS_URL_BACKEND = System.getenv("BACKEND_STUDENT_URI");

    private StudentService() {
    }

    // Singleton -> es gibt nur eine Instanz von StudentService
    public static synchronized StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    public List<Student> getAllStudents() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(STUDENTS_URL_BACKEND + "/api/students"))
                .header("Accept", "application/json")
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(responseBody -> mapToStudentList(responseBody))
                .join();
    }

    public Student createNewStudent(StudentWithoutMatriculationNumber student, String sessionId) {
        try {
            String requestBody = objectMapper.writeValueAsString(student);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(STUDENTS_URL_BACKEND + "/api/students"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Cookie", "JSESSIONID=" + sessionId)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(responseBody -> mapToStudent(responseBody))
                    .join();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Student updateStudent(Student student, String sessionId) {
        try {
            String requestBody = objectMapper.writeValueAsString(student);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(STUDENTS_URL_BACKEND + "/api/students/" + student.matriculationNumber()))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Cookie", "JSESSIONID=" + sessionId)
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(this::mapToStudent)
                    .join();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudent(String matriculationNumberOfStudentToDelete, ListView<Student> listView, String sessionId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(STUDENTS_URL_BACKEND + "/api/students/" + matriculationNumberOfStudentToDelete))
                .header("Cookie", "JSESSIONID=" + sessionId)
                .DELETE()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 204) {
                        Platform.runLater(() -> {
                            listView.getItems().removeIf(student -> student.matriculationNumber().equals(matriculationNumberOfStudentToDelete));
                            listView.refresh();
                        });
                    } else {
                        throw new RuntimeException("Fehler beim Löschen des Studenten mit der Matrikelnummer " + matriculationNumberOfStudentToDelete);
                    }
                })
                .join();
    }

    private Student mapToStudent(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, Student.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Student> mapToStudentList(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
