/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.chatbot.permit.municipal.zones;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** @author bmurray */
public class ImportKML {
  private Connection myConn;

  public ImportKML() throws Exception {
    String dbName = "TEST";
    String dbUser = "test";
    String dbPwd = "test";
    String dbURL = "jdbc:derby://localhost:1527/kmlFile";

    myConn = DriverManager.getConnection(dbURL, dbUser, dbPwd);
    // System.out.println("DB connection successful to: " + dbURL);
  }

  public void addZoneCord(int uniq_Zone_id, String zone, String lat, String lon) throws Exception {
    Statement myStmt = null;
    ResultSet myRs = null;
    try {
      myStmt = myConn.createStatement();
      Double templat = Double.parseDouble(lat);
      Double templon = Double.parseDouble(lon);
      int templatCord = (int) (templat * 1000000);
      int templonCord = (int) (templon * 1000000);
      String sql =
          "INSERT INTO ZONE (UNQ_ZONE_ID, ZONE_CODE, LAT, LON, LAT_CORD, LON_CORD) "
              + "VALUES("
              + uniq_Zone_id
              + ", '"
              + zone
              + "', "
              + templat
              + ", "
              + templon
              + ", "
              + templatCord
              + ", "
              + templonCord
              + ")";
      myStmt.executeUpdate(sql);

    } finally {
      close(myStmt, myRs);
    }
  }

  public void parseKML() throws Exception {
    try {

      final JFileChooser fc = new JFileChooser();
      // Open the dialog using null as parent component if you are outside a
      // Java Swing application otherwise provide the parent comment instead
      int returnVal = fc.showOpenDialog(null);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        // Retrieve the selected file
        File file = fc.getSelectedFile();
        try (FileInputStream inputFile = new FileInputStream(file)) {
          // Do something here

          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          Document doc = dBuilder.parse(inputFile);
          doc.getDocumentElement().normalize();
          // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
          NodeList nList = doc.getElementsByTagName("Placemark");
          // System.out.println("----------------------------");

          for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
              Element eElement = (Element) nNode;

              // Store ZoneID
              String ZoneName = eElement.getElementsByTagName("name").item(0).getTextContent();

              // Scan input for Cordniates in zone and store in table
              Scanner input =
                  new Scanner(
                      eElement.getElementsByTagName("coordinates").item(0).getTextContent());
              while (input.hasNextLine()) {

                String lineIn = input.nextLine().trim();
                if (lineIn.contains(",")) {
                  int first = lineIn.indexOf(',');
                  int second = lineIn.indexOf(',', (first + 1));
                  String tempLat = lineIn.substring(0, first);
                  String tempLon = lineIn.substring((first + 1), second);
                  String tempz = lineIn.substring((second + 1));
                  addZoneCord(temp, ZoneName, tempLat, tempLon);
                  // System.out.println("Zone: " + ZoneID + " Lat: " + tempLat + " Lon: " +tempLon);
                }
              }
            }
          }
        }
      }

      // File inputFile = new File("input.txt");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ZonePolygon convertZoneToPolygon(int zoneID) throws SQLException {

    ZonePolygon tempZone = new ZonePolygon();
    Statement myStmt = null;
    ResultSet myRsCount = null;
    ResultSet myRs = null;

    try {
      myStmt = myConn.createStatement();
      myRs = myStmt.executeQuery("SELECT * FROM ZONE WHERE UNQ_ZONE_ID = " + zoneID);

      if (myRs != null) {
        tempZone.setZoneID(zoneID);

        while (myRs.next()) {
          tempZone.setZoneCode(myRs.getString("ZONE_CODE"));
          int tempLat = Integer.parseInt(myRs.getString("LAT_CORD"));
          int tempLon = Integer.parseInt(myRs.getString("LON_CORD"));
          tempZone.addPoint(tempLat, tempLon);
        }
      }
      return tempZone;
    } finally {
      close(myStmt, myRs);
    }
  }

  public List<ZonePolygon> convertAllZonesToPolygon() throws SQLException {

    List<ZonePolygon> list = new ArrayList<>();
    Statement myStmt = null;
    ResultSet myRs1 = null;
    try {
      myStmt = myConn.createStatement();
      myRs1 = myStmt.executeQuery("SELECT DISTINCT UNQ_ZONE_ID FROM ZONE");

      if (myRs1 != null) {
        while (myRs1.next()) {
          // todo

          ZonePolygon tempZone =
              convertZoneToPolygon(Integer.parseInt(myRs1.getString("UNQ_ZONE_ID")));
          list.add(tempZone);
        }
      }
      return list;
    } finally {
      close(myStmt, myRs1);
    }
  }

  private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
      throws SQLException {

    if (myRs != null) {
      myRs.close();
    }
    if (myStmt != null) {}
    if (myConn != null) {
      myConn.close();
    }
  }

  private void close(Statement myStmt, ResultSet myRs) throws SQLException {
    close(null, myStmt, myRs);
  }

  public static void main(String[] args) throws Exception {
    // ImportKML dbo = new ImportKML();

  }
}
