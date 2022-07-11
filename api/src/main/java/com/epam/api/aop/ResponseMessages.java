package com.epam.api.aop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:ru.properties")
public class ResponseMessages {

    private ResponseMessages() {}

    @Value("${not.found}")
    public static String notFound;

    @Value("${cannot.delete}")
    public static String cannotDelete;

    @Value("${invalid.format}")
    public static String invalidFormat;

    @Value("${duplicate.data}")
    public static String duplicateData;
}