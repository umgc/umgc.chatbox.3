/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.chatbot.permit.municipal.zones;

import java.awt.Polygon;

/**
 *
 * @author bmurray
 */
public class ZonePolygon extends Polygon {

  private String zoneCode;
  private int zoneID;

  public int getZoneID() {
    return zoneID;
  }

  public void setZoneID(int id) {
    zoneID = id;
  }

  public String getZoneCode() {
    return zoneCode;
  }

  public void setZoneCode(String zone) {
    zoneCode = zone;
  }

}

