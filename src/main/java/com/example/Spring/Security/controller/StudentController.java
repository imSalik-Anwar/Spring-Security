package com.example.Spring.Security.controller;

import com.example.Spring.Security.model.Student;
import com.example.Spring.Security.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @PostMapping("/add/username/{username}/password/{password}")
    public Student addStudent(@PathVariable("username") String userName, @PathVariable("password") String password){
        Student student = new Student();
        student.setPassword(bCryptPasswordEncoder.encode(password));
        student.setUsername(userName);
        student.setRole("ROLE_STUDENT, ROLE_ADMIN");

        return studentRepository.save(student);
    }
    @GetMapping("/get/name")
    public String getName(){
        return "Hi student, your name is Salik.";
    }


    @GetMapping("/get/class")
    public String getClassDetail(){
        return "Hi student, your class is Spring 2.";
    }
}
