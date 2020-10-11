package com.chatbot.permit.municipal.model;

import javax.persistence.*;

@Entity
@Table(name = "city")
public class City {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private int city_user_id;
  private String name;
  private String state;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCity_user_id() {
    return city_user_id;
  }

  public void setCity_user_id(int city_user_id) {
    this.city_user_id = city_user_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
}
