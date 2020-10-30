package com.chatbot.permit.municipal.db;
/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author bfetterman
 */
@Component
public class ProcessRequest {

  /**
   * 
   * @param zoneID should be given to the method by Watson
   * @param permitDescription should be given to the method by Watson
   * @param return is the Permit application URL stored in the DB
   */

  @Autowired
  public String retrievePermitInfo(String zoneID, String permitDescription) {

    String applicationUrl = "No permit information found.";

    try {
      String sql =
          "select alu.application_url from allowed_land_use alu join zone_land_use zlu on zlu.id = alu.zone_land_use_id where zlu.description='"
              + permitDescription + "' and (alu.zone_id='" + zoneID + "' or alu.zone_id='ALL')";
      Connection conn = DBConnection.Connect();
      PreparedStatement pst = conn.prepareStatement(sql);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        applicationUrl = rs.getString("application_url");
      }

      pst.close();
      conn.close();

    } catch (Exception e) {

    }

    return applicationUrl;

  }

  /**
   * 
   * @param zoneID should be given to the method by Watson
   * @param permitDescription should be given to the method by Watson
   * @param return is the regulation application URL stored in the DB
   */

  @Autowired
  public String retrieveRegulationInfo(String zoneID, String permitDescription) {

    String procedureUrl = "No regulation information found.";

    try {
      String sql =
          "select alu.procedure_url from allowed_land_use alu join zone_land_use zlu on zlu.id = alu.zone_land_use_id where zlu.description='"
              + permitDescription + "' and (alu.zone_id='" + zoneID + "' or alu.zone_id='ALL')";
      Connection conn = DBConnection.Connect();
      PreparedStatement pst = conn.prepareStatement(sql);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        procedureUrl = rs.getString("procedure_url");
      }

      pst.close();
      conn.close();

    } catch (Exception e) {

    }

    return procedureUrl;

  }

  /**
   * 
   * @param zoneID should be given to the method by Watson
   * @return returns various development standard URLs associated with the zoneID
   */

  @Autowired
  public HashMap retrieveDevelopmentStandardsInfo(String zoneID) {

    HashMap<String, String> standards = new HashMap<>();
    String generalStandardURL = null;
    String additionalStandardURL = null;
    String gardenStandardURL = null;
    String frontageandFacadesStandardsURL = null;

    try {
      String sql = "select * from development_standards ds where ds.zone_id='" + zoneID + "')";
      Connection conn = DBConnection.Connect();
      PreparedStatement pst = conn.prepareStatement(sql);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        generalStandardURL = rs.getString("general_standard_url");

        standards.put("generalStandardURL", generalStandardURL);

        additionalStandardURL = rs.getString("additional_standard_url");

        if (rs.wasNull()) {
          additionalStandardURL = "None";
        }

        standards.put("additionalStandardURL", additionalStandardURL);

        gardenStandardURL = rs.getString("garden_standard_url");

        if (rs.wasNull()) {
          gardenStandardURL = "None";
        }

        standards.put("gardenStandardURL", gardenStandardURL);

        frontageandFacadesStandardsURL = rs.getString("frontage_and_facades_standards_url");

        if (rs.wasNull()) {
          frontageandFacadesStandardsURL = "None";
        }

        standards.put("frontageandFacadesStandardsURL", frontageandFacadesStandardsURL);

      }

      pst.close();
      conn.close();

    } catch (Exception e) {

    }

    if (standards.isEmpty()) {
      standards.put("generalStandardURL", "No development standards were found.");
      standards.put("additionalStandardURL", "No development standards were found.");
      standards.put("gardenStandardURL", "No development standards were found.");
      standards.put("frontageandFacadesStandardsURL", "No development standards were found.");
    }

    return standards;

  }

  @Autowired
  public String retrieveZoneSymbol(String polygonID) {

    String zoneSymbol = "None";

    try {
      String sql = "select z.zone_symbol from polygons p join zone z on p.zone_code = z.zone_symbol where p.POLYGON_ID='" + polygonID + "';";
      Connection conn = DBConnection.Connect();
      PreparedStatement pst = conn.prepareStatement(sql);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        zoneSymbol = rs.getString("zone_symbol");
      }

      pst.close();
      conn.close();
    } catch (Exception e) {

    }

    return zoneSymbol;
  }



}
