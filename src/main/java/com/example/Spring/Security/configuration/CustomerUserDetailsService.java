package com.example.Spring.Security.configuration;

import com.example.Spring.Security.model.Student;
import com.example.Spring.Security.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> studentOptional = studentRepository.findByUsername(username);
        if(studentOptional.isEmpty()){
            throw new UsernameNotFoundException("User doesn't exists.");
        }
        return new UserDetailsCreator(studentOptional.get());
    }
}
