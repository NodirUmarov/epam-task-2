package com.epam.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableAspectJAutoProxy
@ComponentScans({
        @ComponentScan("com.epam.api"),
        @ComponentScan("com.epam.business"),
        @ComponentScan("com.epam.data")})
@EnableWebMvc
public class ApiConfig {

}
