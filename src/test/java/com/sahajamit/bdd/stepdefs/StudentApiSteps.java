package com.sahajamit.bdd.stepdefs;

import com.sahajamit.model.Student;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Class to define the cucumber step definitions.
 */
public class StudentApiSteps extends AbstractSteps implements En {
    public StudentApiSteps(){

        Given("user wants to enroll a new student with the following attributes:", (DataTable studentDt) -> {
            testContext().reset();
            List<Student> studentList = studentDt.asList(Student.class);

            super.testContext()
                    .setPayload(studentList.get(0));
        });

        When("user saves the new student", () -> {
            String createStudentUrl = "/api/students";
            executePost(createStudentUrl);
        });

        Then("the result {string}", (String expectedResult) -> {
            Response response = testContext().getResponse();

            switch (expectedResult) {
                case "IS SUCCESSFUL":
                    assertThat(response.statusCode()).isIn(200, 201);
                    if(!response.asString().isEmpty())
                        assertThat(response.as(Student.class).equals(testContext().getPayload()));
                    break;
                case "FAILS":
                    assertThat(response.statusCode()).isBetween(400, 504);
                    break;
                default:
                    fail("Unexpected error");
            }
        });

        Given("^user wants to query the details for the student with id (\\d+)$", (Integer studentId) -> {
            String getStudentUrl = String.format("/api/students/%s",studentId);
            executeGet(getStudentUrl);
        });
        Given("^user wants to delete the student with id (\\d+)$", (Integer studentId) -> {
            String deleteStudentUrl = String.format("/api/students/%s",studentId);
            executeDelete(deleteStudentUrl);
        });
    }
}
