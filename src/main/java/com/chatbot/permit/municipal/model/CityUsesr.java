package com.chatbot.permit.municipal.model;

import javax.persistence.*;

@Entity
@Table(name = "city_user")
public class CityUsesr {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String email_address;
  private String password;
  private String last_name;
  private String first_name;
  private int authorities_id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail_address() {
    return email_address;
  }

  public void setEmail_address(String email_address) {
    this.email_address = email_address;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLast_name() {
    return last_name;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public int getAuthorities_id() {
    return authorities_id;
  }

  public void setAuthorities_id(int authorities_id) {
    this.authorities_id = authorities_id;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }
}
