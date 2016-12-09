package com.belk.pep.config;

import com.belk.pep.constants.ImageDeleteConstants;
import com.belk.pep.util.PropertyLoader;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    //static reference to itself
    private static Properties prop = PropertyLoader.getPropertyLoader(ImageDeleteConstants.LOAD_DELETE_IMAGE_PROPERTY_FILE);
    public static final String dbDriver = prop.getProperty(ImageDeleteConstants.JDBC_DRIVER);
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static ConnectionFactory instance = new ConnectionFactory();
    
    
    
    //private constructor
    private ConnectionFactory() {
        try {
        	LOGGER.debug("Driver Name --->"+dbDriver);
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
        	LOGGER.error("Error while returning the DB DRiver instance "+e.getMessage());
        }
    }
     
    private Connection createConnection(String env) {
        Connection connection = null;
        try {
            String dbUrl = prop.getProperty(env + "." + ImageDeleteConstants.DB_URL);
            String user = prop.getProperty(env + "." + ImageDeleteConstants.USER);
            String password = prop.getProperty(env + "." + ImageDeleteConstants.PASS);
            connection = DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException e) {
        	LOGGER.error("ERROR: Unable to Connect to Database."+e.getMessage());
        }
        return connection;
    }   
     
    public static Connection getConnection(String env) {
        return instance.createConnection(env);
    }
}