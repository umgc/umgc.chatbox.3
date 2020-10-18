package com.chatbot.permit.municipal;

import com.chatbot.permit.municipal.controller.MainController;
import com.chatbot.permit.municipal.model.Maps;
import com.chatbot.permit.municipal.model.Polygons;
import com.chatbot.permit.municipal.repository.DevelopmentStandardsRepository;
import com.chatbot.permit.municipal.repository.MapsRepository;
import com.chatbot.permit.municipal.repository.PolygonsRepository;
import com.chatbot.permit.municipal.service.JsonParsingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
class MunicipalPermitChabotApplicationTests {

  @MockBean
  private JsonParsingService jsonParsingService;
  @MockBean
  private MapsRepository mockMapsRepository;
  @MockBean
  private PolygonsRepository mockPolygonsRepository;
  @Autowired
  @InjectMocks
  private MainController mainController;
  @Autowired
  private MockMvc mockMvc;
  @Mock
  private DevelopmentStandardsRepository developmentStandardsRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    // mock data for maps repository
    List<Integer> zoneIds = Arrays.asList(71);
    given(mockMapsRepository.findMapsDistinctBy()).willReturn(zoneIds);
    given(mockMapsRepository.findByFKPOLYGONID(71)).willReturn(Arrays.asList(
            new Maps(71, -118.171751, 34.181911, -118171751, 34181911),
            new Maps(71, -118.166906, 34.151521, -118166906, 34151521),
            new Maps(71, -118.164063, 34.149659, -118164063, 34149659),
            new Maps(71, -118.163817, 34.164540, -118163817, 34164540)));
    // mock data for polygons repository
    given(mockPolygonsRepository.findById(71)).willReturn(java.util.Optional.of(new Polygons(71, "OS", "OS")));
  }

  @Test
  void contextLoads() {
    assertThat(mainController).isNotNull();
  }

  @Test
  public void validAddress() throws Exception {
    Object location = new Object() {
      public String location = "360 N Arroyo Blvd";
    };
    ObjectMapper locationMapper = new ObjectMapper();
    String locationJson = locationMapper.writeValueAsString(location);

    LinkedHashMap latLon = new LinkedHashMap();
    latLon.put("lng", -118.166029);
    latLon.put("lat", 34.151636);
    LinkedHashMap addressInfo = new LinkedHashMap();
    addressInfo.put("latLng", latLon);
    addressInfo.put("geocodeQualityCode", "L1AAA");
    ArrayList<LinkedHashMap> locations = new ArrayList<LinkedHashMap>();
    locations.add(addressInfo);
    LinkedHashMap topResult = new LinkedHashMap();
    topResult.put("locations", locations);
    ArrayList<Object> results = new ArrayList<Object>();
    results.add(topResult);
    LinkedHashMap mapQuestResponse = new LinkedHashMap();
    mapQuestResponse.put("results", results);

    given(jsonParsingService
            .parse("http://www.mapquestapi.com/geocoding/v1/address?key=&location=360 N Arroyo Blvd, Pasadena, CA"))
            .willReturn(mapQuestResponse);

    this.mockMvc.perform(post("/geocode")
            .content(locationJson)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{'polygonZoneID': 71}"));
  }

  @Test
  public void outOfPasadena() throws Exception {
    Object location = new Object() {
      public String location = "1616 McCormick Dr";
    };
    ObjectMapper locationMapper = new ObjectMapper();
    String locationJson = locationMapper.writeValueAsString(location);

    LinkedHashMap latLon = new LinkedHashMap();
    latLon.put("lng", -76.846206);
    latLon.put("lat", 38.912614);
    LinkedHashMap addressInfo = new LinkedHashMap();
    addressInfo.put("latLng", latLon);
    addressInfo.put("geocodeQualityCode", "L1AAA");
    ArrayList<LinkedHashMap> locations = new ArrayList<LinkedHashMap>();
    locations.add(addressInfo);
    LinkedHashMap topResult = new LinkedHashMap();
    topResult.put("locations", locations);
    ArrayList<Object> results = new ArrayList<Object>();
    results.add(topResult);
    LinkedHashMap mapQuestResponse = new LinkedHashMap();
    mapQuestResponse.put("results", results);

    given(jsonParsingService
            .parse("http://www.mapquestapi.com/geocoding/v1/address?key=&location=1616 McCormick Dr, Pasadena, CA"))
            .willReturn(mapQuestResponse);

    this.mockMvc.perform(post("/geocode")
            .content(locationJson)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{'polygonZoneID': -1}"));
  }

  @Test
  public void notHighestConfidence() throws Exception {
    Object location = new Object() {
      public String location = "360 N Arroyo Blvd";
    };
    ObjectMapper locationMapper = new ObjectMapper();
    String locationJson = locationMapper.writeValueAsString(location);

    LinkedHashMap latLon = new LinkedHashMap();
    latLon.put("lng", -118.166029);
    latLon.put("lat", 34.151636);
    LinkedHashMap addressInfo = new LinkedHashMap();
    addressInfo.put("latLng", latLon);
    addressInfo.put("geocodeQualityCode", "L1ABA");
    ArrayList<LinkedHashMap> locations = new ArrayList<LinkedHashMap>();
    locations.add(addressInfo);
    LinkedHashMap topResult = new LinkedHashMap();
    topResult.put("locations", locations);
    ArrayList<Object> results = new ArrayList<Object>();
    results.add(topResult);
    LinkedHashMap mapQuestResponse = new LinkedHashMap();
    mapQuestResponse.put("results", results);

    given(jsonParsingService
            .parse("http://www.mapquestapi.com/geocoding/v1/address?key=&location=360 N Arroyo Blvd, Pasadena, CA"))
            .willReturn(mapQuestResponse);

    this.mockMvc.perform(post("/geocode")
            .content(locationJson)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{'polygonZoneID': -1}"));
  }

  @Test
  public void noMapQuestResponse() throws Exception {
    Object location = new Object() {
      public String location = "360 N Arroyo Blvd";
    };
    ObjectMapper locationMapper = new ObjectMapper();
    String locationJson = locationMapper.writeValueAsString(location);

    given(jsonParsingService
            .parse("http://www.mapquestapi.com/geocoding/v1/address?key=&location=360 N Arroyo Blvd, Pasadena, CA"))
            .willReturn(null);

    this.mockMvc.perform(post("/geocode")
            .content(locationJson)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{'polygonZoneID': -1}"));
  }
}
