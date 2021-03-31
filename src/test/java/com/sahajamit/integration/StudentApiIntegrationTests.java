package com.sahajamit.integration;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentApiIntegrationTests {

    @LocalServerPort
    private int port;

    private String studentPostPayload = "{\"id\":1000,\"firstName\":\"Amit\",\"lastName\":\"Rawat\",\"studentClass\":\"1 A\",\"nationality\":\"Singapore\"}";
    private String studentPutPayload = "{\"id\":1000,\"firstName\":\"Amit\",\"lastName\":\"Rawat\",\"studentClass\":\"1 C\",\"nationality\":\"Singapore\"}";

    @Test
    public void testCreateStudent() {
        RequestSpecification requestSpecification = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .body(studentPostPayload);

        Response response = requestSpecification.post(createURLWithPort("/api/students"));
        Assert.assertEquals(response.getStatusCode(),200);
        String responseBody = response.asString();
        Assert.assertEquals(responseBody, studentPostPayload);
    }

    @Test
    public void testGetStudent(){
        RequestSpecification requestSpecification = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("Accept","application/json");

        Response response = requestSpecification.get(createURLWithPort("/api/students/1000"));
        Assert.assertEquals(response.getStatusCode(),200);
        String responseBody = response.asString();
        Assert.assertEquals(responseBody, studentPostPayload);
    }

    @Test
    public void testUpdateStudent() {
        RequestSpecification requestSpecification = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .body(studentPutPayload);

        Response response = requestSpecification.put(createURLWithPort("/api/students/1000"));
        Assert.assertEquals(response.getStatusCode(),200);
        String responseBody = response.asString();
        Assert.assertEquals(responseBody, studentPutPayload);
    }

    @Test
    public void testDeleteStudent() {
        RequestSpecification requestSpecification = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("Accept","application/json");

        Response response = requestSpecification.delete(createURLWithPort("/api/students/1000"));
        Assert.assertEquals(response.getStatusCode(),200);
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}
