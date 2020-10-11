package com.chatbot.permit.municipal.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zone")
public class Zone {
    @Id
    private String zone_symbol;
    private int city_id;
    private String description;

    public String getZone_symbol() {
        return zone_symbol;
    }

    public void setZone_symbol(String zone_symbol) {
        this.zone_symbol = zone_symbol;
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
