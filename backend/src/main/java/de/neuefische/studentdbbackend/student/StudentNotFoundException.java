package de.neuefische.studentdbbackend.student;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String matriculationNumber) {
        super("Student with matriculation number " + matriculationNumber + " not found");
    }
}
