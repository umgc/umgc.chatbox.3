package com.chatbot.permit.municipal.model;

import javax.persistence.*;

@Entity
@Table(name = "maps")
public class Maps {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private int ID;
  @Column(name = "FK_POLYGON_ID")
  private int FKPOLYGONID;
  private double LAT;
  private double LON;
  int LAT_CORD;
  int LON_CORD;

  public Maps(int FK_POLYGON_ID, double LAT, double LON, int LAT_CORD, int LON_CORD) {
    this.setFKPOLYGONID(FK_POLYGON_ID);
    this.setLAT(LAT);
    this.setLON(LON);
    this.setLAT_CORD(LAT_CORD);
    this.setLON_CORD(LON_CORD);
  }

  public void setFKPOLYGONID(int FKPOLYGONID) {
    this.FKPOLYGONID = FKPOLYGONID;
  }

  public void setLAT(double LAT) {
    this.LAT = LAT;
  }

  public void setLON(double LON) {
    this.LON = LON;
  }

  public double getLAT_CORD() {
    return LAT_CORD;
  }

  public void setLAT_CORD(int LAT_CORD) {
    this.LAT_CORD = LAT_CORD;
  }

  public double getLON_CORD() {
    return LON_CORD;
  }

  public void setLON_CORD(int LON_CORD) {
    this.LON_CORD = LON_CORD;
  }
  public boolean equals(Object m) {
    if (m != null && getClass() == m.getClass()) {
      Maps maps = (Maps) m;
      return this.getID() == maps.getID();
    }
    return false;
  }

  public int getID() {
    return ID;
  }
}
