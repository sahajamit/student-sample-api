package com.sahajamit.repository;

import com.sahajamit.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.studentClass = :studentClass")
    List<Student> findByClass(@Param("studentClass") String studentClass);

    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName")
    List<Student> findByFirstName(@Param("firstName") String studentClass);

    @Query("SELECT s FROM Student s WHERE s.lastName = :lastName")
    List<Student> findByLastName(@Param("lastName") String studentClass);

    @Query("SELECT s FROM Student s WHERE s.nationality = :nationality")
    List<Student> findByNationality(@Param("nationality") String studentClass);

}
