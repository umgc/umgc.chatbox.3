package com.chatbot.permit.municipal.model;

import javax.persistence.*;

@Entity
@Table(name = "zone_land_use")
public class ZoneLandUse {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private int city_id;
  private String description;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCity_id() {
    return city_id;
  }

  public void setCity_id(int city_id) {
    this.city_id = city_id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
