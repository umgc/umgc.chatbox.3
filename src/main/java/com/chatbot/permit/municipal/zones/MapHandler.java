/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.chatbot.permit.municipal.zones;

import com.chatbot.permit.municipal.model.Maps;
import com.chatbot.permit.municipal.model.Polygons;
import com.chatbot.permit.municipal.repository.MapsRepository;
import com.chatbot.permit.municipal.repository.PolygonsRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



/**
 *
 * @author bmurray
 */
public class MapHandler {
  private static List<ZonePolygon> mapZones;

  PolygonsRepository polygonsRepository;
  MapsRepository mapsRepository;

  public MapHandler() {
	try {
		this.mapZones = this.convertAllZonesToPolygon();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// this.parseKML(); //uncomment this to import file on start
  }

  public MapHandler(PolygonsRepository polygonsRepository, MapsRepository mapsRepository) {
	  this.polygonsRepository = polygonsRepository;
	  this.mapsRepository = mapsRepository;
	  try {
		this.mapZones = this.convertAllZonesToPolygon();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// this.parseKML(); //uncomment this to import file on start
  }
  
  /**
   * This function returns a list of the custom ZonePolygon objects that are in the DB.
   * 
   * @return
   */
  public List<ZonePolygon> getMapZones() {
    return mapZones;
  }

  /**
   * This function parses through a KML file and imports the contents into two tables. One table
   * stores the the ID, the zone, and the zone again as a note. The second table stores the ID, the
   * lat and lon, and the lat and lon converted to ints.
   * 
   * @throws Exception
   */
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
          NodeList nList = doc.getElementsByTagName("Placemark");

          for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
              Element eElement = (Element) nNode;

              // Store ZoneID
              String ZoneName = eElement.getElementsByTagName("name").item(0).getTextContent();
              addZone(temp, ZoneName);

              // Scan input for Cordniates in zone and store in table
              Scanner input = new Scanner(
                  eElement.getElementsByTagName("coordinates").item(0).getTextContent());
              while (input.hasNextLine()) {
                String lineIn = input.nextLine().trim();
                if (lineIn.contains(",")) {
                  int first = lineIn.indexOf(',');
                  int second = lineIn.indexOf(',', (first + 1));
                  String tempLat = lineIn.substring(0, first);
                  String tempLon = lineIn.substring((first + 1), second);
                  String tempz = lineIn.substring((second + 1));
                  addZoneCord(temp, tempLat, tempLon);
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

  /**
   * This function takes in coordinates and then loops through all polygons in the db until it finds
   * one that contains the coordinates.
   * 
   * @param lat
   * @param lon
   * @return Returns the zone id if found or a -1 if not found.
   * @throws Exception
   */
  public int findZones(Double lat, Double lon) throws Exception {
    int foundZone;
    int numZones = mapZones.size();
    int tempLat = (int) (lat * 1000000);
    int tempLon = (int) (lon * 1000000);

    try {
      // Loop through all zones to look for polygon that contains the cordinates.
      int index1 = 0;
      while (index1 < numZones) {
        // If true, return zone id found
        if (mapZones.get(index1).contains(tempLat, tempLon)) {
          foundZone = mapZones.get(index1).getZoneID();
          return foundZone;
        }
        index1++;
      }
    } catch (Exception exc) {
      System.out.println(exc.getMessage());

    }
    return -1;// returns negative one if no zone is found.
  }

  /**
   * This function takes in the variables need to add the a zone to a database.
   * 
   * @param uniq_Zone_id
   * @param zone
   * @throws Exception
   */
  public void addZone(int uniq_Zone_id, String zone) throws Exception {
    polygonsRepository.save(new Polygons(uniq_Zone_id, zone, zone));
  }

  /**
   * This function takes in the variables need to add the coordinates to a database.
   * 
   * @param uniq_Zone_id
   * @param lat
   * @param lon
   * @throws Exception
   */
  public void addZoneCord(int uniq_Zone_id, String lat, String lon) throws Exception {
    Double templat = Double.parseDouble(lat);
    Double templon = Double.parseDouble(lon);
    int templatCord = (int) (templat * 1000000);
    int templonCord = (int) (templon * 1000000);
    mapsRepository.save(new Maps(uniq_Zone_id, templat, templon, templatCord, templonCord));
  }

  /**
   * This function converts a single row in the DB into Polygons and adds them to a list.
   *
   * @param zoneID
   * @return Custom class ZonePolygon object.
   * @throws SQLException
   */
  public ZonePolygon convertZoneToPolygon(int zoneID) throws SQLException {

    ZonePolygon tempZone = new ZonePolygon();
    List<Maps> maps = mapsRepository.findByFKPOLYGONID(zoneID);
    if (maps.size() > 0) {
      tempZone.setZoneID(zoneID);
      for (Maps map : maps) {
        tempZone.addPoint((int) map.getLAT_CORD(), (int) map.getLON_CORD());
      }
    }

    return tempZone;
  }

  /**
   * This function converts all zones in the DB into Polygons and adds them to a list.
   * 
   * @return List of custom class ZonePolygons.
   * @throws SQLException
   */
  public List<ZonePolygon> convertAllZonesToPolygon() throws SQLException {

    List<ZonePolygon> list = new ArrayList<>();
    List<Integer> fkPolygonIds = mapsRepository.findMapsDistinctBy();
    for (int id : fkPolygonIds) {
      ZonePolygon zonePolygon = convertZoneToPolygon(id);
      list.add(zonePolygon);
    }

    return  list;
  }

  /**
   * This function returns the string value of the zone code
   * 
   * @param zoneID
   * @return string value of zone code
   * @throws SQLException
   */
  public String getZoneCodeForID(int zoneID) throws SQLException {
    Polygons potentialPolygons =  polygonsRepository.findById(zoneID).orElse(null);
    if (potentialPolygons != null) {
      return potentialPolygons.getZONE_CODE();
    }

    return null;
  }

  /**
   * This function returns the string value of any 2nd zone code associated.
   * 
   * @param zoneID
   * @return string value of zone code Will return NULL if no value found
   * @throws SQLException
   */
  public String getZoneCode2ForID(int zoneID) throws SQLException {
    Polygons potentialPolygons =  polygonsRepository.findById(zoneID).orElse(null);
    if (potentialPolygons != null) {
      return potentialPolygons.getZONE_CODE_2();
    }

    return null;
  }

  /**
   * This function returns the string value of any 3rd zone code associated.
   * 
   * @param zoneID
   * @return string value of zone code Will return NULL if no value found
   * @throws SQLException
   */
  public String getZoneCode3ForID(int zoneID) throws SQLException {
    Polygons potentialPolygons =  polygonsRepository.findById(zoneID).orElse(null);
    if (potentialPolygons != null) {
      return potentialPolygons.getZONE_CODE_3();
    }

    return null;
  }

  /**
   * This function returns the string value of any note tied to the zone code associated.
   * 
   * @param zoneID
   * @return string value of zone note Will return NULL if no value found
   * @throws SQLException
   */
  public String getZoneNoteForID(int zoneID) throws SQLException {
    Polygons potentialPolygons =  polygonsRepository.findById(zoneID).orElse(null);
    if (potentialPolygons != null) {
      return potentialPolygons.getZONE_NOTE();
    }

    return null;
  }
}

