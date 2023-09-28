package de.neuefische.studentdbbackend.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private final static String BASE_URL = "/api/students";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NewStudentDto student1 = new NewStudentDto("Synyster", "Gates", "synyster@a7x.com", "Avenged Sevenfold");
    private final NewStudentDto student2 = new NewStudentDto("M.", "Shadows", "m.shadows@a7x.com", "Avenged Sevenfold");


    @BeforeEach
    void insertTestStudents() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(student1))
        );
        mockMvc.perform(post(BASE_URL)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(student2))
        );
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DirtiesContext
    @Test
    void getAllStudents_shouldReturnAllEntries_whenTwoEntriesAreSaved() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(jsonPath("[0].matriculationNumber").isNotEmpty())
                .andExpect(jsonPath("[0].firstName").value(student1.getFirstName()))
                .andExpect(jsonPath("[1].matriculationNumber").isNotEmpty())
                .andExpect(jsonPath("[1].firstName").value(student2.getFirstName()));
    }

    @Test
    void searchStudent() throws Exception {
    }

    @Test
    void getStudentByMatriculationNumber() throws Exception {
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void addStudent() throws Exception {
    }

    @Test
    void updateStudent() throws Exception {
    }

    @Test
    void deleteStudent() throws Exception {
    }

    @Test
    void handleStudentNotFoundException() throws Exception {
    }

    @Test
    void handleValidationException() throws Exception {
    }
}