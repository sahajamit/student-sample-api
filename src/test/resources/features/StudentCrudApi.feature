@api
Feature: Student Enrollment API

  @get-invalid-student
  Scenario: Fetching an invalid student id
    Given user wants to query the details for the student with id 1000
    Then the result 'FAILS'

  @create-student
  Scenario: Enrolling a new student
    Given user wants to enroll a new student with the following attributes:
      | id   | firstName | lastName | studentClass | nationality |
      | 1000 | Amit      | Rawat    | 1 C          | Singapore   |
    When user saves the new student
    Then the result 'IS SUCCESSFUL'

  @get-valid-student
  Scenario: Fetching a valid student id
    Given user wants to query the details for the student with id 1000
    Then the result 'IS SUCCESSFUL'

  @delete-student
  Scenario: Deleting a student
    Given user wants to delete the student with id 1000
    Then the result 'IS SUCCESSFUL'