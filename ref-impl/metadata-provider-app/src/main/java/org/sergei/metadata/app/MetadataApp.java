package org.sergei.metadata.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

@SpringBootApplication(exclude = {
        JdbcTemplateAutoConfiguration.class
})
public class MetadataApp {

    public static void main(String[] args) {
        SpringApplication.run(MetadataApp.class, args);
    }
}
