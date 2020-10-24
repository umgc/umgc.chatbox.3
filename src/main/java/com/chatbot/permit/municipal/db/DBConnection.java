package com.chatbot.permit.municipal.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author bfetterman
 */

/**
 * This class makes use of the application.properties file and the @value annotation to create a
 * connection to the mySQL database
 */

@Component
public class DBConnection {

  @Value("${spring.datasource.url}")
  private String host;

  @Value("${spring.datasource.username}")
  private String userName;

  @Value("${spring.datasource.password}")
  private String password;

  private Connection conn;

  public Connection Connect() {
    try {

      this.conn = DriverManager.getConnection(host, userName, password);

    } catch (SQLException err) {

      err.printStackTrace();

    }
    return null;
  }

  public Connection getConn() {
    return conn;
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }
}