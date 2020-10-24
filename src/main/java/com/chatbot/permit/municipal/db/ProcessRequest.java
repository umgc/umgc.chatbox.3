package com.chatbot.permit.municipal.db;
/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author bfetterman
 */
@Component
public class ProcessRequest {

  private DBConnection dbConnection;

  public ProcessRequest() {
    this.dbConnection = new DBConnection();
  }

  /**
   * 
   * @param zoneID should be given to the method by Watson
   * @param permitDescription should be given to the method by Watson
   * @param return is the Permit application URL stored in the DB
   */

  public String retrievePermitInfo(String zoneID, String permitDescription) {

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

    }

    return applicationUrl;

  }

  /**
   * 
   * @param zoneID should be given to the method by Watson
   * @param permitDescription should be given to the method by Watson
   * @param return is the regulation application URL stored in the DB
   */

  public String retrieveRegulationInfo(String zoneID, String permitDescription) {

    String procedureUrl = "No regulation information found.";

    try {
      String sql =
          "select alu.procedure_url from allowed_land_use alu join zone_land_use zlu on zlu.id = alu.zone_land_use_id where zlu.description='"
              + permitDescription + "' and (alu.zone_id='" + zoneID + "' or alu.zone_id='ALL')";
      PreparedStatement pst = this.dbConnection.getConn().prepareStatement(sql);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        procedureUrl = rs.getString("application_url");
      }

      pst.close();
      this.dbConnection.getConn().close();

    } catch (Exception e) {

    }

    return procedureUrl;

  }

  /**
   * 
   * @param zoneID should be given to the method by Watson
   * @return returns various development standard URLs associated with the zoneID
   */

  public String retrieveDevelopmentStandardsInfo(String zoneID) {

    String noDevelopmentStandards = "No development standards were found.";
    String generalStandardURL = null;
    String additionalStandardURL = null;
    String gardenStandardURL = null;
    String frontageandFacadesStandardsURL = null;

    try {
      String sql = "select * from development_standards ds where ds.zone_id='" + zoneID + "')";
      PreparedStatement pst = this.dbConnection.getConn().prepareStatement(sql);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        generalStandardURL =
            "General Development Standards: " + rs.getString("general_standard_url");
        additionalStandardURL = rs.getString("additional_standard_url");

        if (rs.wasNull()) {
          additionalStandardURL = "Additional development standards: None";
        } else {
          additionalStandardURL = "Additional development standards: " + additionalStandardURL;
        }

        gardenStandardURL = rs.getString("garden_standard_url");

        if (rs.wasNull()) {
          gardenStandardURL = "Garden standards: None";
        } else {
          gardenStandardURL = "Garden standards: " + gardenStandardURL;
        }

        frontageandFacadesStandardsURL = rs.getString("frontage_and_facades_standards_url");

        if (rs.wasNull()) {
          frontageandFacadesStandardsURL = "Frontage and facades standards: None";
        } else {
          frontageandFacadesStandardsURL =
              "Frontage and facades standards: " + frontageandFacadesStandardsURL;
        }
      }

      pst.close();
      this.dbConnection.getConn().close();

    } catch (Exception e) {

    }

    if (generalStandardURL == null)
      return noDevelopmentStandards;

    return generalStandardURL + " " + additionalStandardURL + " " + gardenStandardURL + " "
        + frontageandFacadesStandardsURL;

  }


}
