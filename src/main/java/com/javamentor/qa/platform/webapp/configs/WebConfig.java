package com.javamentor.qa.platform.webapp.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${spring.mail.username}")
    private String inviteMailSender;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("loginPage");
        registry.addViewController("/test").setViewName("testPage");
        registry.addViewController("/main").setViewName("mainPage");
        registry.addViewController("/user").setViewName("userPage");
        registry.addViewController("/users").setViewName("usersPage");
        registry.addViewController("/question/ask").setViewName("askQuestion");
        registry.addViewController("/question/{id}").setViewName("questionPage");
        registry.addViewController("/question/tag/{id}").setViewName("questionTagPage");
        registry.addViewController("/questions").setViewName("questionsPage");
        registry.addViewController("/tags").setViewName("tagsPage");
        registry.addViewController("/testallusers").setViewName("testAllUsersPage");
        registry.addViewController("/unanswered").setViewName("unansweredQuestionsPage");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(inviteMailSender);
        return message;
    }
}
