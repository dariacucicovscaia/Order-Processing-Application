package com.stefanini.orderprocessing.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DataBaseConnection  extends ReadProps{

    private static Connection con = null;
    static {
        String propertiesFilename = "application.properties";
        Properties properties = new DataBaseConnection().loadProperties(propertiesFilename);

        String url =properties.getProperty("SPRING_DATASOURCE_URL");
        String root = properties.getProperty("SPRING_DATASOURCE_USERNAME");
        String password = properties.getProperty("SPRING_DATASOURCE_PASSWORD");

        try {
            con = DriverManager.getConnection(url, root, password);
        } catch (SQLException e) {

        }
    }

    public static Connection getConnection() {
        return con;
    }
}