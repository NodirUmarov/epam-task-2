package com.epam.business.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@ComponentScan("com.epam.business")
public class BusinessConfig {
}
