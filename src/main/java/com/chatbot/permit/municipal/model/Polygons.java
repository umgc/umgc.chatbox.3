package com.chatbot.permit.municipal.model;

import javax.persistence.*;

@Entity
@Table(name = "polygons")
public class Polygons {
  @Id
  private int POLYGON_ID;
  private String ZONE_CODE;
  private String ZONE_CODE_2;
  private String ZONE_CODE_3;
  private String ZONE_NOTE;

  public Polygons(int POLYGON_ID, String ZONE_CODE, String ZONE_NOTE) {
    this.setPOLYGON_ID(POLYGON_ID);
    this.setZONE_CODE(ZONE_CODE);
    this.setZONE_NOTE(ZONE_NOTE);
  }

  public Polygons(int POLYGON_ID, String ZONE_CODE, String ZONE_CODE_2, String ZONE_NOTE) {
    this.setPOLYGON_ID(POLYGON_ID);
    this.setZONE_CODE(ZONE_CODE);
    this.setZONE_CODE_2(ZONE_CODE_2);
    this.setZONE_NOTE(ZONE_NOTE);
  }

  public int getPOLYGON_ID() {
    return this.POLYGON_ID;
  }

  public void setPOLYGON_ID(int POLYGON_ID) {
    this.POLYGON_ID = POLYGON_ID;
  }

  public String getZONE_CODE() {
    return ZONE_CODE;
  }

  public void setZONE_CODE(String ZONE_CODE) {
    this.ZONE_CODE = ZONE_CODE;
  }

  public String getZONE_CODE_2() {
    return ZONE_CODE_2;
  }

  public void setZONE_CODE_2(String ZONE_CODE_2) {
    this.ZONE_CODE_2 = ZONE_CODE_2;
  }

  public String getZONE_CODE_3() {
    return ZONE_CODE_3;
  }

  public String getZONE_NOTE() {
    return ZONE_NOTE;
  }

  public void setZONE_NOTE(String ZONE_NOTE) {
    this.ZONE_NOTE = ZONE_NOTE;
  }

  public boolean equals(Object p) {
    if (p != null && getClass() == p.getClass()) {
      Polygons polygon = (Polygons) p;
      return this.getPOLYGON_ID() == polygon.getPOLYGON_ID();
    }
    return false;
  }

  public int hashCode() {
    return this.hashCode();
  }
}
