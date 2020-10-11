/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.chatbot.permit.municipal.zones;

import java.awt.Polygon;

/** @author bmurray */
public class ZonePolygon extends Polygon {

  private String ZoneCode;
  private int ZoneID;

  public int getZoneID() {
    return ZoneID;
  }

  public void setZoneID(int id) {
    ZoneID = id;
  }

  public String getZoneCode() {
    return ZoneCode;
  }

  public void setZoneCode(String zone) {
    ZoneCode = zone;
  }
}
