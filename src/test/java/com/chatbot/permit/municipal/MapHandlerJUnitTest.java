/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.chatbot.permit.municipal;

import com.chatbot.permit.municipal.model.Maps;
import com.chatbot.permit.municipal.model.Polygons;
import com.chatbot.permit.municipal.repository.MapsRepository;
import com.chatbot.permit.municipal.repository.PolygonsRepository;
import com.chatbot.permit.municipal.zones.MapHandler;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 *
 * @author bmurray
 */
public class MapHandlerJUnitTest {

  @Mock
  private MapsRepository mockMapsRepository;
  @Mock
  private PolygonsRepository mockPolygonsRepository;

  public MapHandlerJUnitTest() {}

  @BeforeClass
  public static void setUpClass() {


  }

  @AfterClass
  public static void tearDownClass() {}

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    // mock data for maps repository
    List<Integer> zoneIds = Arrays.asList(71, 9, 17, 390, 129, 315);
    when(mockMapsRepository.findMapsDistinctBy()).thenReturn(zoneIds);
    when(mockMapsRepository.findByFKPOLYGONID(71))
        .thenReturn(Arrays.asList(new Maps(71, -118.171751, 34.181911, -118171751, 34181911),
            new Maps(71, -118.166906, 34.151521, -118166906, 34151521),
            new Maps(71, -118.164063, 34.149659, -118164063, 34149659),
            new Maps(71, -118.163817, 34.164540, -118163817, 34164540)));
    when(mockMapsRepository.findByFKPOLYGONID(9))
        .thenReturn(Arrays.asList(new Maps(9, -118.133904, 34.147762, -118133904, 34147762),
            new Maps(9, -118.132267, 34.135326, -118132267, 34135326),
            new Maps(9, -118.131377, 34.135647, -118131377, 34135647),
            new Maps(9, -118.13091, 34.15129, -118130910, 34151290)));
    when(mockMapsRepository.findByFKPOLYGONID(17))
        .thenReturn(Arrays.asList(new Maps(17, -118.154152, 34.145886, -118154152, 34145886),
            new Maps(17, -118.154149, 34.144501, -118154149, 34144501),
            new Maps(17, -118.147468, 34.144507, -118147468, 34144507),
            new Maps(17, -118.147566, 34.145812, -118147566, 34145812)));
    when(mockMapsRepository.findByFKPOLYGONID(390))
        .thenReturn(Arrays.asList(new Maps(390, -118.151324, 34.129923, -118151324, 34129923),
            new Maps(390, -118.150283, 34.124041, -118150283, 34124041),
            new Maps(390, -118.150052, 34.124327, -118150052, 34124327),
            new Maps(390, -118.149744, 34.124698, -118149744, 34124698)));
    when(mockMapsRepository.findByFKPOLYGONID(129))
        .thenReturn(Arrays.asList(new Maps(129, -118.153338, 34.140208, -118153338, 34140208),
            new Maps(129, -118.151821, 34.135773, -118151821, 34135773),
            new Maps(129, -118.146507, 34.128724, -118146507, 34128724),
            new Maps(129, -118.146623, 34.137519, -118146623, 34137519)));
    when(mockMapsRepository.findByFKPOLYGONID(315))
        .thenReturn(Arrays.asList(new Maps(315, -118.150009, 34.180908, -118150009, 34180908),
            new Maps(315, -118.149989, 34.179353, -118149989, 34179353),
            new Maps(315, -118.146214, 34.175038, -118146214, 34175038),
            new Maps(315, -118.146991, 34.180579, -118146991, 34180579)));

    // mock data for polygons repository
    when(mockPolygonsRepository.findById(71))
        .thenReturn(java.util.Optional.of(new Polygons(71, "OS", "OS")));
    when(mockPolygonsRepository.findById(9))
        .thenReturn(java.util.Optional.of(new Polygons(9, "CD-5", "CD-5")));
    when(mockPolygonsRepository.findById(17))
        .thenReturn(java.util.Optional.of(new Polygons(17, "CD-1", "AD", "CD-1 AD-1")));
    when(mockPolygonsRepository.findById(390))
        .thenReturn(java.util.Optional.of(new Polygons(390, "IG-SP-2-AD-2", "IG-SP-2-AD-2")));
    when(mockPolygonsRepository.findById(129))
        .thenReturn(java.util.Optional.of(new Polygons(129, "CD-6", "CD-6")));
    when(mockPolygonsRepository.findById(315))
        .thenReturn(java.util.Optional.of(new Polygons(315, "RM-12", "RM-12")));
  }

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
    assertEquals(71, new MapHandler(mockPolygonsRepository, mockMapsRepository)
        .findZones(-118.166029, 34.151636));
    assertEquals(9, new MapHandler(mockPolygonsRepository, mockMapsRepository)
        .findZones(-118.132064, 34.141109));
    assertEquals(17, new MapHandler(mockPolygonsRepository, mockMapsRepository)
        .findZones(-118.147809, 34.145677));
    assertEquals(390, new MapHandler(mockPolygonsRepository, mockMapsRepository)
        .findZones(-118.150206, 34.124529));
    assertEquals(129, new MapHandler(mockPolygonsRepository, mockMapsRepository)
        .findZones(-118.147012, 34.137053));
    assertEquals(315, new MapHandler(mockPolygonsRepository, mockMapsRepository)
        .findZones(-118.149303, 34.179435));
    assertEquals(-1, new MapHandler(mockPolygonsRepository, mockMapsRepository)
        .findZones(-76.847445, 38.912977));
  }

  @Test
  public void getZoneCodeForIDTest() throws Exception {
    assertEquals("OS",
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCodeForID(71));
    assertEquals("CD-5",
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCodeForID(9));
    assertEquals("CD-1",
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCodeForID(17));
    assertEquals("IG-SP-2-AD-2",
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCodeForID(390));
    assertEquals("CD-6",
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCodeForID(129));
    assertEquals("RM-12",
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCodeForID(315));
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCodeForID(-1));

  }

  @Test
  public void getZoneCode2ForIDTest() throws Exception {
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode2ForID(71));
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode2ForID(9));
    assertEquals("AD",
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode2ForID(17));
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode2ForID(390));
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode2ForID(129));
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode2ForID(315));
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode2ForID(-1));

  }

  @Test
  public void getZoneCode3ForIDTest() throws Exception {
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode3ForID(71));
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode3ForID(9));
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode3ForID(17));
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode3ForID(390));
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode3ForID(129));
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode3ForID(315));
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneCode3ForID(-1));

  }

  @Test
  public void getZoneNoteForIDTest() throws Exception {
    assertEquals("OS",
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneNoteForID(71));
    assertEquals("CD-5",
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneNoteForID(9));
    assertEquals("CD-1 AD-1",
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneNoteForID(17));
    assertEquals("IG-SP-2-AD-2",
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneNoteForID(390));
    assertEquals("CD-6",
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneNoteForID(129));
    assertEquals("RM-12",
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneNoteForID(315));
    assertEquals(null,
        new MapHandler(mockPolygonsRepository, mockMapsRepository).getZoneNoteForID(-1));

  }
}
