package com.sahajamit.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahajamit.controller.StudentController;
import com.sahajamit.model.Student;
import com.sahajamit.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class)
public class StudentApiUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository mockedstudentRepository;

    private String studentPostPayload = "{\"id\":1000,\"firstName\":\"Amit\",\"lastName\":\"Rawat\",\"studentClass\":\"1 A\",\"nationality\":\"Indian\"}";

    public Student getStudent(String payload) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(payload,Student.class);
    }

    @Test
    public void testCreateStudent() throws Exception{
        Mockito.when(mockedstudentRepository.save(Mockito.any(Student.class))).thenReturn(getStudent(studentPostPayload));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(studentPostPayload);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(studentPostPayload,response.getContentAsString(), false);
    }

    @Test
    public void testGetStudent() throws Exception{
        Mockito.when(mockedstudentRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(getStudent(studentPostPayload)));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/students/1000")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(studentPostPayload,response.getContentAsString(), false);
    }

}
