package com.example.Spring.Security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/get-name")
    public String getName(){
        return "Hi student, your name is Salik.";
    }


    @GetMapping("/get-class")
    public String getClassDetail(){
        return "Hi student, your class is Spring 2.";
    }
}
