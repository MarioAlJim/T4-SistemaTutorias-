package com.teamfour.sistutorias.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConnection {
    
    private Connection connection;

    public Connection getConnection() throws SQLException{
        connect();
        return connection;
    }

    private void connect() throws SQLException{
        try {
            File archivo = new File("src/main/java/com/teamfour/sistutorias/dataaccess/dbconfig.txt");
            FileInputStream configurationFile = new FileInputStream(archivo);
            Properties properties = new Properties();
            properties.load(configurationFile);
            configurationFile.close();
            String direction = properties.getProperty("Direction");
            String user = properties.getProperty("User");
            String password = properties.getProperty("Password");
            connection = DriverManager.getConnection(direction, user, password);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    public void closeConection(){
        if(connection!=null){
            try {
                if(!connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
