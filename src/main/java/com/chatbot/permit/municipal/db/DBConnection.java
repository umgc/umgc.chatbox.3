package com.chatbot.permit.municipal.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author bfetterman
 */

/**
 * This class makes use of the application.properties file and the @value
 * annotation to create a connection to the mySQL database
 */

public class DBConnection {

  private Connection conn;
  Logger logger = LoggerFactory.getLogger(DBConnection.class);

  public DBConnection(String host, String userName, String password) {
    this.connect(host, userName, password);
  }

  public void connect(String host, String userName, String password) {
    try {

      this.conn = DriverManager.getConnection(host, userName, password);

    } catch (SQLException err) {
      logger.error("context", err);
    }
  }

  public Connection getConn() {
    return conn;
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }
}
