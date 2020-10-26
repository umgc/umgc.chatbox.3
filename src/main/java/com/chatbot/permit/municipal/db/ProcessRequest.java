package com.chatbot.permit.municipal.db;
/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 *
 * @author bfetterman
 */
public class ProcessRequest {

  private DBConnection dbConnection;

  public ProcessRequest(String host, String userName, String password) {
    this.dbConnection = new DBConnection(host, userName, password);
  }

  public ProcessRequest(DBConnection dbConnection) {
    // connection for unit tests
    this.dbConnection = dbConnection;
  }

  public HashMap<String, String> retrieveInformation(String type, String action, String object, String zoneID) {
    HashMap<String, String> links;
    String permitDescription = action + " " + object;

    switch (type) {
      case "permit":
        links = this.retrievePermitInfo(zoneID, permitDescription);
        break;
      case "regulation":
        links = this.retrieveRegulationInfo(zoneID, permitDescription);
        break;
      default:
        links = null;
    }

    return links;
  }

  /**
   * 
   * @param zoneID should be given to the method by Watson
   * @param permitDescription should be given to the method by Watson
   * @return is the Permit application URL stored in the DB
   */

  public HashMap<String, String> retrievePermitInfo(String zoneID, String permitDescription) {

    HashMap<String, String> appUrlHashMap = new HashMap<String, String>();
    String applicationUrl = "No permit information found.";

    try {
      String sql =
          "select alu.application_url from allowed_land_use alu join zone_land_use zlu on zlu.id = alu.zone_land_use_id where zlu.description='"
              + permitDescription + "' and (alu.zone_id='" + zoneID + "' or alu.zone_id='ALL')";
      PreparedStatement pst = this.dbConnection.getConn().prepareStatement(sql);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        applicationUrl = rs.getString("application_url");
      }

      pst.close();
      this.dbConnection.getConn().close();

    } catch (Exception e) {
      e.printStackTrace();
    }

    appUrlHashMap.put("permitUrl", applicationUrl);
    return appUrlHashMap;

  }

  /**
   * 
   * @param zoneID should be given to the method by Watson
   * @param permitDescription should be given to the method by Watson
   * @return is the regulation application URL stored in the DB
   */

  public HashMap<String, String> retrieveRegulationInfo(String zoneID, String permitDescription) {

    HashMap<String, String> procedureUrlHashMap = new HashMap<String, String>();
    String procedureUrl = "No regulation information found.";

    try {
      String sql =
          "select alu.procedure_url from allowed_land_use alu join zone_land_use zlu on zlu.id = alu.zone_land_use_id where zlu.description='"
              + permitDescription + "' and (alu.zone_id='" + zoneID + "' or alu.zone_id='ALL')";
      PreparedStatement pst = this.dbConnection.getConn().prepareStatement(sql);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        procedureUrl = rs.getString("procedure_url");
      }

      pst.close();
      this.dbConnection.getConn().close();

    } catch (Exception e) {
      e.printStackTrace();
    }

    procedureUrlHashMap.put("regulationUrl", procedureUrl);
    return procedureUrlHashMap;

  }

  /**
   * 
   * @param zoneID should be given to the method by Watson
   * @return returns various development standard URLs associated with the zoneID
   */

  public HashMap<String, String> retrieveDevelopmentStandardsInfo(String zoneID) {

    HashMap <String, String> standards = new HashMap<>();
    String noDevelopmentStandards = "No development standards were found.";
    String generalStandardURL = null;
    String additionalStandardURL = null;
    String gardenStandardURL = null;
    String frontageandFacadesStandardsURL = null;

    try {
      String sql = "select * from development_standards ds where ds.zone_id='" + zoneID + "'";
      PreparedStatement pst = this.dbConnection.getConn().prepareStatement(sql);
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
      this.dbConnection.getConn().close();

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


}
