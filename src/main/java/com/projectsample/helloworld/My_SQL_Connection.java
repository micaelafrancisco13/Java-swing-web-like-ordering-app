/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ela
 */
public class My_SQL_Connection {
    private String url = "jdbc:mysql://localhost/sql_store";
    private String user = "root";
    private String password = "ela12";
    private String driver = "com.mysql.cj.jdbc.Driver";
    private Connection connection;
    
    public My_SQL_Connection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public Connection getConnection(){
        return connection;
    }
}