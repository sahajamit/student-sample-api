package com.sahajamit.controller;

import com.sahajamit.exception.StudentNotFoundException;
import com.sahajamit.model.Student;
import com.sahajamit.repository.StudentRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    // Create Student Record
    @PostMapping("/students")
    public Student createStudent(@Valid @RequestBody Student student) {
        return studentRepository.save(student);
    }

    // Delete a Student
    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        studentRepository.delete(student);

        return ResponseEntity.ok().build();
    }

    // Delete multiple Students
    @DeleteMapping("/students")
    public ResponseEntity<?> deleteStudent(@RequestBody String idsString) {
        JSONObject idsJson = new JSONObject(idsString);
        JSONArray idsStringArray = idsJson.getJSONArray("ids");
        idsStringArray.toList().stream()
                .forEach(studentId->{
                    Student student = studentRepository.findById(Long.valueOf(studentId.toString()))
                            .orElseThrow(() -> new StudentNotFoundException(Long.valueOf(studentId.toString())));
                    studentRepository.delete(student);
                });
        return ResponseEntity.ok().build();
    }

    // Update a Student
    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable(value = "id") Long studentId,
                           @Valid @RequestBody Student studentDetails) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        student.setId(studentDetails.getId());
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setNationality(studentDetails.getNationality());
        student.setStudentClass(studentDetails.getStudentClass());

        Student updatedStudent = studentRepository.save(student);
        return updatedStudent;
    }

    // Update a Student partially
    @PatchMapping("/students/{id}")
    public Student updateStudentPartially(@PathVariable(value = "id") Long studentId,
                              @Valid @RequestBody Student studentDetails) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        if(!Objects.isNull(studentDetails.getFirstName()))
            student.setFirstName(studentDetails.getFirstName());

        if(!Objects.isNull(studentDetails.getLastName()))
            student.setLastName(studentDetails.getLastName());

        if(!Objects.isNull(studentDetails.getNationality()))
            student.setNationality(studentDetails.getNationality());

        if(!Objects.isNull(studentDetails.getStudentClass()))
            student.setStudentClass(studentDetails.getStudentClass());

        Student updatedStudent = studentRepository.save(student);
        return updatedStudent;
    }

    // Get a Single Student
    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable(value = "id") Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
    }

    // Get bulk student records
    @GetMapping("/students/fetchStudents")
    public List<Student> getStudentByClass(
            @RequestParam(name = "class",required = false) String studentClass,
            @RequestParam(name = "firstName",required = false) String firstName,
            @RequestParam(name = "lastName",required = false) String lastName,
            @RequestParam(name = "nationality",required = false) String nationality

    ) {
        if(!Objects.isNull(studentClass))
            return studentRepository.findByClass(studentClass);
        else if(!Objects.isNull(firstName))
            return studentRepository.findByFirstName(firstName);
        else if(!Objects.isNull(lastName))
            return studentRepository.findByLastName(lastName);
        else if(!Objects.isNull(nationality))
            return studentRepository.findByNationality(nationality);
        else
            return studentRepository.findAll();
    }
}
