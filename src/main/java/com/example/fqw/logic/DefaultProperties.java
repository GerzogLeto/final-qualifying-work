package com.example.fqw.logic;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DefaultProperties {
    @Getter
    private Properties properties;

    public DefaultProperties() {
        this.properties = new Properties();
        try (InputStream input = DefaultProperties.class
                .getClassLoader().getResourceAsStream("default.properties")){
            properties.load(input);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
