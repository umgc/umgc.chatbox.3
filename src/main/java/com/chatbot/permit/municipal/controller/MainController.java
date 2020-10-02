package com.chatbot.permit.municipal.controller;

import com.chatbot.permit.municipal.domain.Location;
import com.chatbot.permit.municipal.service.ParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.chatbot.permit.municipal.zones.MapPoint;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class MainController {
    private static final String JSON_GEOCODE = "http://www.mapquestapi.com/geocoding/v1/address?key=" +
            "&location=";
    MapPoint startApp = new MapPoint();

    @Autowired
    private ParsingService parsingService;

    @RequestMapping(value = "/geocode", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public LinkedHashMap main(@RequestBody Location userLocation) throws Exception {
        LinkedHashMap latLng;
        String mapquestUrl = JSON_GEOCODE + userLocation.getLocation();
        LinkedHashMap addressInfo;
        String geocodeQualityCode;

        try {
            // send request for lat and long values from mapquest api
            LinkedHashMap response = (LinkedHashMap) parsingService.parse(mapquestUrl);

            // extract address info from nested return object
            ArrayList<Object> results = (ArrayList<Object>) response.get("results");
            LinkedHashMap topResult = (LinkedHashMap) results.get(0);
            ArrayList<Object> locations = (ArrayList<Object>) topResult.get("locations");
            addressInfo = (LinkedHashMap) locations.get(0);
        } catch (Exception exe) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Failed to Retrieve Address Information"
            );
        }

        /**
         * Mapquest returns a geoQualityCode value that specifies how exact the return match is. The last three letters
         * are confidence ratings for different areas. A value of AAA means that the result has the highest confidence
         * value for each area.
         */
        geocodeQualityCode = (String) addressInfo.get("geocodeQualityCode");
        if (!geocodeQualityCode.substring(2).equals("AAA")) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid address"
            );
        }

        latLng = (LinkedHashMap) addressInfo.get("latLng");
        // Use MapPoint class to find zone for user's lat and long
        LinkedHashMap zoneCode = new LinkedHashMap<String, String>();
        try {
            List<String> zones = startApp.findZones((Double) latLng.get("lng"), (Double) latLng.get("lat"));
            zoneCode.put("zone", zones.get(0));
        } catch(Exception exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Failed to retrieve zone"
            );
        }

        return zoneCode;
    }
}
