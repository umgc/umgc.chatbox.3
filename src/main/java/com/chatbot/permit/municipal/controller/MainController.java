package com.chatbot.permit.municipal.controller;

import com.chatbot.permit.municipal.domain.Location;
import com.chatbot.permit.municipal.repository.MapsRepository;
import com.chatbot.permit.municipal.repository.PolygonsRepository;
import com.chatbot.permit.municipal.service.ParsingService;
import com.chatbot.permit.municipal.zones.MapHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@RestController
public class MainController {

	@Autowired
	PolygonsRepository polygonsRepository;
	@Autowired
	MapsRepository mapsRepository;

	@Value("${mapquest.apikey}")
	private String mapQuestApiKey;

	private final String CITY_AND_STATE = "Pasadena, CA";
	private final String MAPQUEST_BASE_URL = "http://www.mapquestapi.com/geocoding/v1/address?key=";


  @Autowired
  private ParsingService parsingService;

  @RequestMapping(value = "/geocode", method = RequestMethod.POST, consumes = "application/json",
      produces = "application/json")
  public LinkedHashMap main(@RequestBody Location userLocation) throws Exception {
	// repositories are not initialize outside of @RequestMapping so declare MapHandler instance here
	MapHandler startApp = new MapHandler(polygonsRepository, mapsRepository);
    LinkedHashMap latLng;
    String mapquestUrl = MAPQUEST_BASE_URL + mapQuestApiKey + "&location=" + userLocation.getLocation() + ", " + CITY_AND_STATE;
    LinkedHashMap addressInfo;
    String geocodeQualityCode;
    LinkedHashMap<String, Integer> polygonZoneID = new LinkedHashMap<String, Integer>();

    try {
    	// send request for lat and long values from mapquest api
    	LinkedHashMap response = (LinkedHashMap) parsingService.parse(mapquestUrl);
		// extract address info from nested return object
		ArrayList<Object> results = (ArrayList<Object>) response.get("results");
		LinkedHashMap topResult = (LinkedHashMap) results.get(0);
		ArrayList<Object> locations = (ArrayList<Object>) topResult.get("locations");
		    addressInfo = (LinkedHashMap) locations.get(0);
	} catch (Exception exe) {
		exe.printStackTrace();
	    polygonZoneID.put("polygonZoneID", -1);
	    return polygonZoneID;
	}
		
	/**
	 * Mapquest returns a geoQualityCode value that specifies how exact the return match is. The last three letters
	 * are confidence ratings for different areas. A value of AAA means that the result has the highest confidence
	 * value for each area.
	 */
	geocodeQualityCode = (String) addressInfo.get("geocodeQualityCode");
	if (!geocodeQualityCode.substring(2).equals("AAA")) {
	    polygonZoneID.put("polygonZoneID", -1);
	    return polygonZoneID;
	}
	
	latLng = (LinkedHashMap) addressInfo.get("latLng");
	// Use MapPoint class to find zone for user's lat and long
	try {
	    int id = startApp.findZones((Double) latLng.get("lng"), (Double) latLng.get("lat"));
	    polygonZoneID.put("polygonZoneID", id);
	} catch(Exception exc) {
		exc.printStackTrace();
	    polygonZoneID.put("polygonZoneID", -1);
	    return polygonZoneID;
	}
	
	return polygonZoneID;
  }
}
