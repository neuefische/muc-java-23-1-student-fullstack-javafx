package de.neuefische.studentdbbackend.student;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "user")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    @Test
    void getAllStudents_shouldReturnAllEntries_whenTwoEntriesAreSavedAndNoSearchParamsAreDefined() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(jsonPath("[0].matriculationNumber").isNotEmpty())
                .andExpect(jsonPath("[0].firstName").value(student1.getFirstName()))
                .andExpect(jsonPath("[1].matriculationNumber").isNotEmpty())
                .andExpect(jsonPath("[1].firstName").value(student2.getFirstName()));
    }

    @Test
    void getAllStudentsByFirstName_shouldReturnOneEntry_whenOneFittingEntryExists() throws Exception {
        mockMvc.perform(get(BASE_URL + "?firstName=" + student1.getFirstName()))
                .andExpect(jsonPath("[0].firstName").value(student1.getFirstName()))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getAllStudentsByLastName_shouldReturnOneEntry_whenOneFittingEntryExists() throws Exception {
        mockMvc.perform(get(BASE_URL + "?lastName=" + student1.getLastName()))
                .andExpect(jsonPath("[0].lastName").value(student1.getLastName()))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getAllStudentsByCourse_shouldReturnTwoEntry_whenTwoFittingEntriesExists() throws Exception {
        mockMvc.perform(get(BASE_URL + "?courseOfStudies=" + student1.getCourseOfStudies()))
                .andExpect(jsonPath("[0].courseOfStudies").value(student1.getCourseOfStudies()))
                .andExpect(jsonPath("[1].courseOfStudies").value(student2.getCourseOfStudies()))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void searchStudent() throws Exception {
        mockMvc.perform(get(BASE_URL + "/search?firstName=" + student1.getFirstName() + "&lastName=" + student1.getLastName()))
                .andExpect(jsonPath("matriculationNumber").isNotEmpty())
                .andExpect(jsonPath("firstName").value(student1.getFirstName()))
                .andExpect(jsonPath("lastName").value(student1.getLastName()));
    }

    @Test
    void getStudentByMatriculationNumber_shouldReturnRequestedStudent_whenMatchingMatriculatioNumberIsProvided() throws Exception {
        String studentsListAsString = mockMvc.perform(get(BASE_URL))
                .andReturn().getResponse().getContentAsString();

        List<StudentResponseDto> studentsList = objectMapper.readValue(studentsListAsString, new TypeReference<>() {
        });
        StudentResponseDto firstStudent = studentsList.get(0);

        mockMvc.perform(get(BASE_URL + "/" + firstStudent.getMatriculationNumber()))
                .andExpect(jsonPath("matriculationNumber").value(firstStudent.getMatriculationNumber()));
    }

    @Test
    void addStudent_shouldCreateNewStudentWithMatricleNumber_whenValidDataIsProvided() throws Exception {
        NewStudentDto student3 = new NewStudentDto("Zacky", "Vengeance", "zacky@a7x.com", "Avenged Sevenfold");

        mockMvc.perform(post(BASE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(student3)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("matriculationNumber").isNotEmpty())
                .andExpect(jsonPath("firstName").value(student3.getFirstName())
                );
    }

    @Test
    void addStudent_shouldReturn400_whenInvalidDataIsProvided() throws Exception {
        NewStudentDto studentMissingFirstName = new NewStudentDto(null, "Vengeance", "zacky@a7x.com", "Avenged Sevenfold");
        NewStudentDto studentMissingLastName = new NewStudentDto(null, "Vengeance", "zacky@a7x.com", "Avenged Sevenfold");
        NewStudentDto studentMissingEmail = new NewStudentDto(null, "Vengeance", "zacky@a7x.com", "Avenged Sevenfold");
        NewStudentDto studentMissingCourseOfStudies = new NewStudentDto(null, "Vengeance", "zacky@a7x.com", "Avenged Sevenfold");

        mockMvc.perform(post(BASE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(studentMissingFirstName)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post(BASE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(studentMissingLastName)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post(BASE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(studentMissingEmail)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post(BASE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(studentMissingCourseOfStudies)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void updateStudent_shouldUpdateStudentAccordingly_whenUserHasAdminRoleAndStudentExists() throws Exception {
        String studentsListAsString = mockMvc.perform(get(BASE_URL))
                .andReturn().getResponse().getContentAsString();

        List<StudentResponseDto> studentsList = objectMapper.readValue(studentsListAsString, new TypeReference<>() {
        });
        StudentResponseDto originalStudent = studentsList.get(0);
        NewStudentDto updatedFirstStudent = new NewStudentDto("Papa", originalStudent.getLastName(), originalStudent.getEmail(), originalStudent.getCourseOfStudies());

        mockMvc.perform(put(BASE_URL + "/" + originalStudent.getMatriculationNumber())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedFirstStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("matriculationNumber").value(originalStudent.getMatriculationNumber()))
                .andExpect(jsonPath("firstName").value(updatedFirstStudent.getFirstName()))
                .andExpect(jsonPath("lastName").value(updatedFirstStudent.getLastName())
                );
    }

    @Test
    void updateStudent_shoulReturn400_whenValuesInBodyArentValidAndMatriculationNumberIsInvalid() throws Exception {
        String THIS_MATRICULATION_NUMBER_DOES_NOT_EXIST = "THIS_MATRICULATION_NUMBER_DOES_NOT_EXIST";
        NewStudentDto studentThatDoesntExist = new NewStudentDto("?", "?", "?", "?");

        mockMvc.perform(put(BASE_URL + "/" + THIS_MATRICULATION_NUMBER_DOES_NOT_EXIST)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(studentThatDoesntExist)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStudent_shoulReturn400_whenValuesInBodyArentValidAndMatriculationNumberIsValid() throws Exception {
        String studentsListAsString = mockMvc.perform(get(BASE_URL))
                .andReturn().getResponse().getContentAsString();

        List<StudentResponseDto> studentsList = objectMapper.readValue(studentsListAsString, new TypeReference<>() {
        });
        StudentResponseDto originalStudent = studentsList.get(0);

        NewStudentDto invalidRequestBody = new NewStudentDto("?", "?", "?", "?");

        mockMvc.perform(put(BASE_URL + "/" + originalStudent.getMatriculationNumber())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidRequestBody)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStudent_shoulReturn404_whenStudentDoesntExist() throws Exception {
        String THIS_MATRICULATION_NUMBER_DOES_NOT_EXIST = "THIS_MATRICULATION_NUMBER_DOES_NOT_EXIST";
        NewStudentDto studentThatDoesntExist = new NewStudentDto("Nobody", "Nobody", "Nobody@Nobody.de", "Nobody");

        mockMvc.perform(put(BASE_URL + "/" + THIS_MATRICULATION_NUMBER_DOES_NOT_EXIST)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(studentThatDoesntExist)))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void deleteStudent_shouldDeleteStudentByMatriculationNumber_whenStudentExistsAndUserHasAdminRole() throws Exception {
        String studentsListAsString = mockMvc.perform(get(BASE_URL))
                .andReturn().getResponse().getContentAsString();

        List<StudentResponseDto> studentsList = objectMapper.readValue(studentsListAsString, new TypeReference<>() {
        });
        StudentResponseDto studentToDelete = studentsList.get(0);

        mockMvc.perform(delete(BASE_URL + "/" + studentToDelete.getMatriculationNumber()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(BASE_URL + "/" + studentToDelete.getMatriculationNumber()))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void deleteStudent_shouldReturn404_whenStudentDoesntExistAndUserHasAdminRole() throws Exception {
        String THIS_MATRICULATION_NUMBER_DOES_NOT_EXIST = "THIS_MATRICULATION_NUMBER_DOES_NOT_EXIST";

        mockMvc.perform(delete(BASE_URL + "/" + THIS_MATRICULATION_NUMBER_DOES_NOT_EXIST))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStudent_shouldReturn403_whenUserHasNoAdminRole() throws Exception {
        String studentsListAsString = mockMvc.perform(get(BASE_URL))
                .andReturn().getResponse().getContentAsString();

        List<StudentResponseDto> studentsList = objectMapper.readValue(studentsListAsString, new TypeReference<>() {
        });
        StudentResponseDto originalStudent = studentsList.get(0);

        mockMvc.perform(delete(BASE_URL + "/" + originalStudent.getMatriculationNumber()))
                .andExpect(status().isForbidden());
    }
}