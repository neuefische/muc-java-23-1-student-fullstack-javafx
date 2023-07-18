module de.neuefische.mucjava231javafxstudents {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    requires java.net.http;

    opens de.neuefische.mucjava231javafxstudents;
    exports de.neuefische.mucjava231javafxstudents;

    opens de.neuefische.mucjava231javafxstudents.controller;
    exports de.neuefische.mucjava231javafxstudents.controller;

    opens de.neuefische.mucjava231javafxstudents.model;
    exports de.neuefische.mucjava231javafxstudents.model;

    opens de.neuefische.mucjava231javafxstudents.security;
    exports de.neuefische.mucjava231javafxstudents.security;
}