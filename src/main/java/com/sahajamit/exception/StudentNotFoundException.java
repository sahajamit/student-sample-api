package com.sahajamit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {
    @Getter
    private Long studentId;

    public StudentNotFoundException(Long studentId){
        super(String.format("Student not found with Id: '%s'", studentId));
        this.studentId = studentId;
    }

}
