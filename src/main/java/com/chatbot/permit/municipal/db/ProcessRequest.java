package com.chatbot.permit.municipal.db;
/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bfetterman
 */
public class ProcessRequest {

  private DBConnection dbConnection;
  Logger logger = LoggerFactory.getLogger(ProcessRequest.class);
  private static final String loggerContext = "context";

  public ProcessRequest(String host, String userName, String password) {
    this.dbConnection = new DBConnection(host, userName, password);
  }

  public ProcessRequest(DBConnection dbConnection) {
    // connection for unit tests
    this.dbConnection = dbConnection;
  }

  public Map<String, String> retrieveInformation(String type, String action, String object,
      String zoneID) throws SQLException {
    HashMap<String, String> links;
    String permitDescription = action + " " + object;

    switch (type) {
      case "permit":
        links = (HashMap<String, String>) this.retrievePermitInfo(zoneID, permitDescription);
        break;
      case "regulation":
        links = (HashMap<String, String>) this.retrieveRegulationInfo(zoneID, permitDescription);
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

  public Map<String, String> retrievePermitInfo(String zoneID, String permitDescription)
      throws SQLException {

    HashMap<String, String> appUrlHashMap = new HashMap<>();
    String applicationUrl = "No permit information found.";
    PreparedStatement pst = null;

    try {
      String sql =
          "select alu.application_url from allowed_land_use alu join zone_land_use zlu on zlu.id = alu.zone_land_use_id where zlu.description= ? and (alu.zone_id= ? or alu.zone_id='ALL')";
      pst = this.dbConnection.getConn().prepareStatement(sql);
      pst.setString(1, permitDescription);
      pst.setString(2, zoneID);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        applicationUrl = rs.getString("application_url");
      }

      pst.close();
      this.dbConnection.getConn().close();

    } catch (Exception e) {
      logger.error(loggerContext, e);
    } finally {
      if (pst != null) {
        pst.close();
      }
      this.dbConnection.getConn().close();
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

  public Map<String, String> retrieveRegulationInfo(String zoneID, String permitDescription)
      throws SQLException {

    HashMap<String, String> procedureUrlHashMap = new HashMap<>();
    String procedureUrl = "No regulation information found.";
    PreparedStatement pst = null;

    try {
      String sql =
          "select alu.procedure_url from allowed_land_use alu join zone_land_use zlu on zlu.id = alu.zone_land_use_id where zlu.description= ? and (alu.zone_id= ? or alu.zone_id='ALL')";
      pst = this.dbConnection.getConn().prepareStatement(sql);
      pst.setString(1, permitDescription);
      pst.setString(2, zoneID);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        procedureUrl = rs.getString("procedure_url");
      }
    } catch (Exception e) {
      logger.error(loggerContext, e);
    } finally {
      if (pst != null) {
        pst.close();
      }
      this.dbConnection.getConn().close();
    }

    procedureUrlHashMap.put("regulationUrl", procedureUrl);
    return procedureUrlHashMap;

  }

  /**
   *
   * @param zoneID should be given to the method by Watson
   * @return returns various development standard URLs associated with the zoneID
   */

  public Map<String, String> retrieveDevelopmentStandardsInfo(String zoneID) throws SQLException {

    HashMap<String, String> standards = new HashMap<>();
    String noDevelopmentStandards = "No development standards were found.";
    String generalStandardURL = null;
    String additionalStandardURL = null;
    String gardenStandardURL = null;
    String frontageandFacadesStandardsURL = null;
    PreparedStatement pst = null;

    try {
      String sql = "select * from development_standards ds where ds.zone_id= ?";
      pst = this.dbConnection.getConn().prepareStatement(sql);
      pst.setString(1, zoneID);
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
    } catch (Exception e) {
      logger.error(loggerContext, e);
    } finally {
      if (pst != null) {
        pst.close();
      }
      this.dbConnection.getConn().close();
    }

    if (standards.isEmpty()) {
      standards.put("generalStandardURL", noDevelopmentStandards);
      standards.put("additionalStandardURL", noDevelopmentStandards);
      standards.put("gardenStandardURL", noDevelopmentStandards);
      standards.put("frontageandFacadesStandardsURL", noDevelopmentStandards);
    }

    return standards;

  }

  public Map<String, String> retrieveZoneSymbol(int polygonID) throws SQLException {

    String zoneSymbol = "None";
    PreparedStatement pst = null;
    HashMap<String, String> zoneSymbolHashMap = new HashMap<>();

    try {
      String sql =
          "select z.zone_symbol from polygons p join zone z on p.zone_code = z.zone_symbol where p.POLYGON_ID = ?;";
      pst = this.dbConnection.getConn().prepareStatement(sql);
      pst.setString(1, String.valueOf(polygonID));
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        zoneSymbol = rs.getString("zone_symbol");
      }

      pst.close();
    } catch (Exception e) {
      logger.error(loggerContext, e);
    } finally {
      if (pst != null) {
        pst.close();
      }
      this.dbConnection.getConn().close();
    }

    zoneSymbolHashMap.put("zoneSymbol", zoneSymbol);

    return zoneSymbolHashMap;
  }
}
