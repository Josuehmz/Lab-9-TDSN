package com.lab.secureweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SecurewebApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SecurewebApplication.class);
        app.setDefaultProperties(buildDefaultProperties());
        app.run(args);
    }

    private static Map<String, Object> buildDefaultProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("server.port", getPort());
        return props;
    }

    public static int getPort() {
        String portEnv = System.getenv("PORT");
        if (portEnv != null && !portEnv.isBlank()) {
            try {
                return Integer.parseInt(portEnv.trim());
            } catch (NumberFormatException ignored) {
            }
        }
        return 5000;
    }
}
