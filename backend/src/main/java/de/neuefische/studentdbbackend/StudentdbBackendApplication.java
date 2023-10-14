package de.neuefische.studentdbbackend;

import de.neuefische.studentdbbackend.student.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentdbBackendApplication {

    private static final Logger logger = LoggerFactory.getLogger(StudentdbBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StudentdbBackendApplication.class, args);

        logger.debug("StudentdbBackendApplication started");
    }

}
