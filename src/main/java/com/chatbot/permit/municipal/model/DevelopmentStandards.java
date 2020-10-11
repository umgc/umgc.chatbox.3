package com.chatbot.permit.municipal.model;

import javax.persistence.*;

@Entity
@Table(name = "development_standards")
public class DevelopmentStandards {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String zone_id;
  private String general_standard_url;
  private String additional_standard_url;
  private String garden_standard_url;
  private String frontage_and_facades_standards_url;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getZone_id() {
    return zone_id;
  }

  public void setZone_id(String zone_id) {
    this.zone_id = zone_id;
  }

  public String getGeneral_standard_url() {
    return general_standard_url;
  }

  public void setGeneral_standard_url(String general_standard_url) {
    this.general_standard_url = general_standard_url;
  }

  public String getAdditional_standard_url() {
    return additional_standard_url;
  }

  public void setAdditional_standard_url(String additional_standard_url) {
    this.additional_standard_url = additional_standard_url;
  }

  public String getGarden_standard_url() {
    return garden_standard_url;
  }

  public void setGarden_standard_url(String garden_standard_url) {
    this.garden_standard_url = garden_standard_url;
  }

  public String getFrontage_and_facades_standards_url() {
    return frontage_and_facades_standards_url;
  }

  public void setFrontage_and_facades_standards_url(String frontage_and_facades_standards_url) {
    this.frontage_and_facades_standards_url = frontage_and_facades_standards_url;
  }
}
