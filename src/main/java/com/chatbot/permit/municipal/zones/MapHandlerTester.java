/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.chatbot.permit.municipal.zones;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author bmurray
 */
public class MapHandlerTester {


  public MapHandlerTester() {
    try {
    } catch (Exception exc) {
      System.out.println(exc.getMessage());
    }
  }

  // Driver Code only purpose is to test functionality
  public static void main(String[] args) throws Exception {
    try {
      MapHandler startApp = new MapHandler();
      List<ZonePolygon> mapZones = startApp.convertAllZonesToPolygon();
      // find zone and return ID

      int foundZone1 = startApp.findZones(-118.166029, 34.151636);
      System.out.println("in zone: " + foundZone1 + startApp.getZoneCodeForID(foundZone1)
          + startApp.getZoneCode2ForID(foundZone1) + startApp.getZoneCode3ForID(foundZone1)
          + startApp.getZoneNoteForID(foundZone1));
      int foundZone2 = startApp.findZones(-118.132064, 34.141109);
      System.out.println("in zone: " + foundZone2 + startApp.getZoneCodeForID(foundZone2)
          + startApp.getZoneCode2ForID(foundZone2) + startApp.getZoneCode3ForID(foundZone2)
          + startApp.getZoneNoteForID(foundZone2));
      int foundZone3 = startApp.findZones(-118.147809, 34.145677);
      System.out.println("in zone: " + foundZone3 + startApp.getZoneCodeForID(foundZone3)
          + startApp.getZoneCode2ForID(foundZone3) + startApp.getZoneCode3ForID(foundZone3)
          + startApp.getZoneNoteForID(foundZone3));
      int foundZone4 = startApp.findZones(-118.150206, 34.124529);
      System.out.println("in zone: " + foundZone4 + startApp.getZoneCodeForID(foundZone4)
          + startApp.getZoneCode2ForID(foundZone4) + startApp.getZoneCode3ForID(foundZone4)
          + startApp.getZoneNoteForID(foundZone4));
      int foundZone5 = startApp.findZones(-118.147012, 34.137053);
      System.out.println("in zone: " + foundZone5 + startApp.getZoneCodeForID(foundZone5)
          + startApp.getZoneCode2ForID(foundZone5) + startApp.getZoneCode3ForID(foundZone5)
          + startApp.getZoneNoteForID(foundZone5));
      int foundZone6 = startApp.findZones(-118.149303, 34.179435);
      System.out.println("in zone: " + foundZone6 + startApp.getZoneCodeForID(foundZone6)
          + startApp.getZoneCode2ForID(foundZone6) + startApp.getZoneCode3ForID(foundZone6)
          + startApp.getZoneNoteForID(foundZone6));
      int foundZone7 = startApp.findZones(-76.847445, 38.912977);
      System.out.println("in zone: " + foundZone7 + startApp.getZoneCodeForID(foundZone7)
          + startApp.getZoneCode2ForID(foundZone7) + startApp.getZoneCode3ForID(foundZone7)
          + startApp.getZoneNoteForID(foundZone7));

      // startApp.parseKML();

    } catch (Exception exc) {
      System.out.println(exc.getMessage());
    }
  }

}
