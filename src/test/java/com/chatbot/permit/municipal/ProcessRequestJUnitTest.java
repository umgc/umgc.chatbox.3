/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.chatbot.permit.municipal;

import com.chatbot.permit.municipal.db.DBConnection;
import com.chatbot.permit.municipal.db.ProcessRequest;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 *
 * @author bmurray
 */
public class ProcessRequestJUnitTest {

  @Mock
  DBConnection dbConnection;
  @Mock
  Connection connection;
  @Mock
  PreparedStatement preparedStatement;
  @Mock
  ResultSet resultSet;

  public ProcessRequestJUnitTest() {}

  @BeforeClass
  public static void setUpClass() {}

  @AfterClass
  public static void tearDownClass() {}

  @Before
  public void setUp() throws SQLException {
    MockitoAnnotations.initMocks(this);
    when(dbConnection.getConn()).thenReturn(connection);
  }

  @After
  public void tearDown() {}

  @AfterEach
  public void clearMocks() {
    reset(resultSet);
    reset(preparedStatement);
    reset(preparedStatement);
    reset(connection);
  }

  // TODO add test methods here.
  // The methods must be annotated with annotation @Test. For example:
  //
  // @Test
  // public void hello() {}

  @Test
  public void retrievePermitInfo() throws Exception {
    HashMap<String, String> expectedValue = new HashMap<String, String>();
    expectedValue.put("permitUrl",
        "Private: https://www.cityofpasadena.net/wp-content/uploads/sites/30/Zoning-Permit-Application.pdf?v=1602628892503 or Public: https://ww5.cityofpasadena.net/planning/wp-content/uploads/sites/56/2017/09/Tree-Removal-Public.pdf");
    String sqlStml =
        "select alu.application_url from allowed_land_use alu join zone_land_use zlu on zlu.id = alu.zone_land_use_id where zlu.description= ? and (alu.zone_id= ? or alu.zone_id='ALL')";
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getString("application_url")).thenReturn(
        "Private: https://www.cityofpasadena.net/wp-content/uploads/sites/30/Zoning-Permit-Application.pdf?v=1602628892503 or Public: https://ww5.cityofpasadena.net/planning/wp-content/uploads/sites/56/2017/09/Tree-Removal-Public.pdf");
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(connection.prepareStatement(sqlStml)).thenReturn(preparedStatement);
    assertEquals(expectedValue,
        new ProcessRequest(dbConnection).retrievePermitInfo("OS", "remove tree"));
  }

  @Test
  public void retrieveInformationPermitCase() throws Exception {
    HashMap<String, String> expectedValue = new HashMap<String, String>();
    expectedValue.put("permitUrl",
        "Private: https://www.cityofpasadena.net/wp-content/uploads/sites/30/Zoning-Permit-Application.pdf?v=1602628892503 or Public: https://ww5.cityofpasadena.net/planning/wp-content/uploads/sites/56/2017/09/Tree-Removal-Public.pdf");
    String sqlStml =
        "select alu.application_url from allowed_land_use alu join zone_land_use zlu on zlu.id = alu.zone_land_use_id where zlu.description= ? and (alu.zone_id= ? or alu.zone_id='ALL')";
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getString("application_url")).thenReturn(
        "Private: https://www.cityofpasadena.net/wp-content/uploads/sites/30/Zoning-Permit-Application.pdf?v=1602628892503 or Public: https://ww5.cityofpasadena.net/planning/wp-content/uploads/sites/56/2017/09/Tree-Removal-Public.pdf");
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(connection.prepareStatement(sqlStml)).thenReturn(preparedStatement);
    assertEquals(expectedValue,
        new ProcessRequest(dbConnection).retrieveInformation("permit", "remove", "tree", "OS"));
  }

  @Test
  public void retrieveRegulationInfoTest() throws Exception {
    HashMap<String, String> expectedValue = new HashMap<String, String>();
    expectedValue.put("regulationUrl",
        "https://library.municode.com/ca/pasadena/codes/code_of_ordinances?nodeId=TIT8HESA_CH8.52CITRTRPROR_8.52.070PRPRTRRELATRPRPEPP");
    String sqlStml =
        "select alu.procedure_url from allowed_land_use alu join zone_land_use zlu on zlu.id = alu.zone_land_use_id where zlu.description= ? and (alu.zone_id= ? or alu.zone_id='ALL')";
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getString("procedure_url")).thenReturn(
        "https://library.municode.com/ca/pasadena/codes/code_of_ordinances?nodeId=TIT8HESA_CH8.52CITRTRPROR_8.52.070PRPRTRRELATRPRPEPP");
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(connection.prepareStatement(sqlStml)).thenReturn(preparedStatement);
    assertEquals(expectedValue,
        new ProcessRequest(dbConnection).retrieveRegulationInfo("OS", "remove tree"));
  }

  @Test
  public void retrieveInformationRegulationCase() throws Exception {
    HashMap<String, String> expectedValue = new HashMap<String, String>();
    expectedValue.put("regulationUrl",
        "https://library.municode.com/ca/pasadena/codes/code_of_ordinances?nodeId=TIT8HESA_CH8.52CITRTRPROR_8.52.070PRPRTRRELATRPRPEPP");
    String sqlStml =
        "select alu.procedure_url from allowed_land_use alu join zone_land_use zlu on zlu.id = alu.zone_land_use_id where zlu.description= ? and (alu.zone_id= ? or alu.zone_id='ALL')";
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getString("procedure_url")).thenReturn(
        "https://library.municode.com/ca/pasadena/codes/code_of_ordinances?nodeId=TIT8HESA_CH8.52CITRTRPROR_8.52.070PRPRTRRELATRPRPEPP");
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(connection.prepareStatement(sqlStml)).thenReturn(preparedStatement);
    assertEquals(expectedValue,
        new ProcessRequest(dbConnection).retrieveInformation("regulation", "remove", "tree", "OS"));
  }

  @Test
  public void retrieveInformationDefaultCase() throws Exception {
    assertEquals(null,
        new ProcessRequest(dbConnection).retrieveInformation("default", "remove", "tree", "OS"));
  }

  @Test
  public void retrieveDevelopmentStandardsInfoTest() throws Exception {
    HashMap<String, String> expectedValue = new HashMap<String, String>();
    expectedValue.put("additionalStandardURL", "None");
    expectedValue.put("frontageandFacadesStandardsURL", "None");
    expectedValue.put("generalStandardURL",
        "https://library.municode.com/ca/pasadena/codes/code_of_ordinances?nodeId=TIT17_ZONING_CODE_ART2ZODIALLAUSZOECST_CH17.26SPPUZODI_17.26.040SPPUDIGEDEST");
    expectedValue.put("gardenStandardURL", "None");
    String sqlStml = "select * from development_standards ds where ds.zone_id= ?";
    when(resultSet.wasNull()).thenReturn(true).thenReturn(true).thenReturn(true);
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getString("additional_standard_url")).thenReturn(null);
    when(resultSet.getString("frontage_and_facades_standards_url")).thenReturn(null);
    when(resultSet.getString("general_standard_url")).thenReturn(
        "https://library.municode.com/ca/pasadena/codes/code_of_ordinances?nodeId=TIT17_ZONING_CODE_ART2ZODIALLAUSZOECST_CH17.26SPPUZODI_17.26.040SPPUDIGEDEST");
    when(resultSet.getString("garden_standard_url")).thenReturn(null);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(connection.prepareStatement(sqlStml)).thenReturn(preparedStatement);
    assertEquals(expectedValue,
        new ProcessRequest(dbConnection).retrieveDevelopmentStandardsInfo("OS"));
  }

  @Test
  public void retrieveZoneSymbolTest() throws Exception {
    HashMap<String, String> expectedValue = new HashMap<String, String>();
    expectedValue.put("zoneSymbol", "OS");
    String sqlStml =
            "select distinct p.ZONE_CODE from polygons p join maps m on p.POLYGON_ID = m.FK_POLYGON_ID where p.POLYGON_ID='71'";
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getString("ZONE_CODE")).thenReturn("OS");
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(connection.prepareStatement(sqlStml)).thenReturn(preparedStatement);
    assertEquals(expectedValue, new ProcessRequest(dbConnection).retrieveZoneSymbol(71));
  }

  @Test
  public void retrieveNoZoneSymbolTest() throws Exception {
    HashMap<String, String> expectedValue = new HashMap<String, String>();
    expectedValue.put("zoneSymbol", "None");
    String sqlStml =
        "select z.zone_symbol from polygons p join zone z on p.zone_code = z.zone_symbol where p.POLYGON_ID = ?;";
    when(resultSet.next()).thenReturn(false);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(connection.prepareStatement(sqlStml)).thenReturn(preparedStatement);
    assertEquals(expectedValue, new ProcessRequest(dbConnection).retrieveZoneSymbol(71));
  }
}
