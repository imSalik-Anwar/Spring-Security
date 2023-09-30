package com.example.Spring.Security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() { // it encrypts passwords of users
        return new BCryptPasswordEncoder();
    }
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception { // defining access for users
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/public/**") // ** means all the APIs in controller having RequestMapping "public".
                .permitAll() // permitAll means public controller has an open-access without any roles. Anyone can access APIs in public controller.
                .requestMatchers("/admin/**") // All APIs in admin controller
                .permitAll()
                .requestMatchers("/student/add/**")
                .permitAll()
                .requestMatchers("/student/get/name")
                .hasRole("STUDENT")
                .requestMatchers("/student/get/class")
                .hasRole("STUDENT")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();

        return httpSecurity.build();
    }
    @Bean
 /* This method is used for in-memory authentication
    public UserDetailsService getUserDetailsService() { // to define user roles

        UserDetails student = User.withUsername("Salik") // defining users roles
                .password(getPasswordEncoder().encode("salik123"))
                .roles("STUDENT")
                .build();

        UserDetails admin = User.withUsername("Arif")
                .password(getPasswordEncoder().encode("arif123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(student, admin); // We are not using any DB so the application will
        // store the details of these user roles in main memory. That's why it is called in-memory
    }*/
    // In-DB Authentication
    public UserDetailsService getUserDetailsService(){
        return new CustomerUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;
    }

        /*
        If we need to make only certain APIs of a controller accessible, we can define their endpoints. Rather than writing **.
        Suppose if there are 10 APIs in student controller, and we want to make only 4 of them accessible to students,
        we can write all 4 of them on by one in requestMatcher and define their access in hasRole or hasAnyRoles.
        *hasRole is used when only one type of users has access to respective API/APIs.
        *hasAnyRoles is used when more than one types of users have access to respective API/APIs.
        for example: ["/student/**"] APIs are accessible to Students and Admins, so we use hasAnyRole
        ["/admin/**"] APIs are accessible to admin ony, so we use hasRole.
         */
}
