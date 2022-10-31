package com.stefanini.orderprocessing.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class ReadProps {

    protected Properties loadProperties(String propertiesFilename) {

        Properties prop = new Properties();

        ClassLoader loader = this.getClass().getClassLoader();
        try (InputStream stream = loader.getResourceAsStream(propertiesFilename)) {
            if (stream == null) {
                throw new FileNotFoundException();
            }
            prop.load(stream);
        } catch (IOException e) {
           e.getMessage();
        }
        return prop;
    }
}

