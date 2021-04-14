package com.franktran.springbootdatajdbc.student;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentRepository.findById(id).orElse(null);
    }

    @PostMapping
    public void createStudent(@RequestBody Student student) {
        studentRepository.save(student);
    }

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student existStudent = getStudentById(id);
        if (Objects.nonNull(existStudent)) {
            existStudent.setEmail(student.getEmail());
            existStudent.setName(student.getName());
            studentRepository.save(existStudent);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) {
        Student existStudent = getStudentById(id);
        if (Objects.nonNull(existStudent)) {
            studentRepository.delete(existStudent);
        }
    }
}
