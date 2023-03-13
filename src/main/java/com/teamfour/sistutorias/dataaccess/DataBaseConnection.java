package com.teamfour.sistutorias.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConnection {
    
    private Connection connection;
    private final String DB="jdbc:mysql://sistematutoriasfei.mysql.database.azure.com:3306/sistematutoriasfei?useSSL=true&requireSSL=false";

    public Connection getConnection() throws SQLException{
        connect();
        return connection;
    }

    private void connect() throws SQLException{
       /* usuarioDB = usuarioDataBase.obtenerUsuario();
        connection=DriverManager.getConnection(DB,usuarioDB.getUser(),usuarioDB.getPassword());*/
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
