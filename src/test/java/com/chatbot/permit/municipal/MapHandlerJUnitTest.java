/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.chatbot.permit.municipal;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.chatbot.permit.municipal.zones.MapHandler;

/**
 *
 * @author bmurray
 */
public class MapHandlerJUnitTest {

  public MapHandlerJUnitTest() {}

  @BeforeClass
  public static void setUpClass() {


  }

  @AfterClass
  public static void tearDownClass() {}

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  // TODO add test methods here.
  // The methods must be annotated with annotation @Test. For example:
  //
  /**
   * in zone: 71OS in zone: 9CD-5 in zone: 17CD-1ADCD-1 AD-1 in zone: 390IG-SP-2-AD-2 in zone:
   * 129CD-6 in zone: 315RM-12 in zone: -1nullnullnullnull
   */
  // @Test
  // public void hello() {}
  @Test
  public void findZonesTest() throws Exception {
    assertEquals(71, new MapHandler().findZones(-118.166029, 34.151636));
    assertEquals(9, new MapHandler().findZones(-118.132064, 34.141109));
    assertEquals(17, new MapHandler().findZones(-118.147809, 34.145677));
    assertEquals(390, new MapHandler().findZones(-118.150206, 34.124529));
    assertEquals(129, new MapHandler().findZones(-118.147012, 34.137053));
    assertEquals(315, new MapHandler().findZones(-118.149303, 34.179435));
    assertEquals(-1, new MapHandler().findZones(-76.847445, 38.912977));
  }

  @Test
  public void getZoneCodeForIDTest() throws Exception {
    assertEquals("OS", new MapHandler().getZoneCodeForID(71));
    assertEquals("CD-5", new MapHandler().getZoneCodeForID(9));
    assertEquals("CD-1", new MapHandler().getZoneCodeForID(17));
    assertEquals("IG-SP-2-AD-2", new MapHandler().getZoneCodeForID(390));
    assertEquals("CD-6", new MapHandler().getZoneCodeForID(129));
    assertEquals("RM-12", new MapHandler().getZoneCodeForID(315));
    assertEquals(null, new MapHandler().getZoneCodeForID(-1));

  }

  @Test
  public void getZoneCode2ForIDTest() throws Exception {
    assertEquals(null, new MapHandler().getZoneCode2ForID(71));
    assertEquals(null, new MapHandler().getZoneCode2ForID(9));
    assertEquals("AD", new MapHandler().getZoneCode2ForID(17));
    assertEquals(null, new MapHandler().getZoneCode2ForID(390));
    assertEquals(null, new MapHandler().getZoneCode2ForID(129));
    assertEquals(null, new MapHandler().getZoneCode2ForID(315));
    assertEquals(null, new MapHandler().getZoneCode2ForID(-1));

  }

  @Test
  public void getZoneCode3ForIDTest() throws Exception {
    assertEquals(null, new MapHandler().getZoneCode3ForID(71));
    assertEquals(null, new MapHandler().getZoneCode3ForID(9));
    assertEquals(null, new MapHandler().getZoneCode3ForID(17));
    assertEquals(null, new MapHandler().getZoneCode3ForID(390));
    assertEquals(null, new MapHandler().getZoneCode3ForID(129));
    assertEquals(null, new MapHandler().getZoneCode3ForID(315));
    assertEquals(null, new MapHandler().getZoneCode3ForID(-1));

  }

  @Test
  public void getZoneNoteForIDTest() throws Exception {
    assertEquals("OS", new MapHandler().getZoneNoteForID(71));
    assertEquals("CD-5", new MapHandler().getZoneNoteForID(9));
    assertEquals("CD-1 AD-1", new MapHandler().getZoneNoteForID(17));
    assertEquals("IG-SP-2-AD-2", new MapHandler().getZoneNoteForID(390));
    assertEquals("CD-6", new MapHandler().getZoneNoteForID(129));
    assertEquals("RM-12", new MapHandler().getZoneNoteForID(315));
    assertEquals(null, new MapHandler().getZoneNoteForID(-1));

  }
}
