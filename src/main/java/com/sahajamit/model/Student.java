package com.sahajamit.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "student")
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
//        allowGetters = true)
@EqualsAndHashCode
@Data
public class Student implements Serializable {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String studentClass;
    private String nationality;
}
