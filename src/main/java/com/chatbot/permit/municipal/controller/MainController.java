package com.chatbot.permit.municipal.controller;

import com.chatbot.permit.municipal.db.ProcessRequest;
import com.chatbot.permit.municipal.domain.WatsonArguments;
import com.chatbot.permit.municipal.repository.MapsRepository;
import com.chatbot.permit.municipal.repository.PolygonsRepository;
import com.chatbot.permit.municipal.service.ParsingService;
import com.chatbot.permit.municipal.watsonactions.AddressVerification;
import com.chatbot.permit.municipal.zones.MapHandler;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class MainController {

  @Autowired
  PolygonsRepository polygonsRepository;
  @Autowired
  MapsRepository mapsRepository;
  @Autowired
  private ParsingService parsingService;

  @Value("${mapquest.apikey}")
  private String mapQuestApiKey;
  @Value("${address.city}")
  private String city;
  @Value("${address.state}")
  private String state;
  @Value("${spring.datasource.url}")
  private String host;
  @Value("${spring.datasource.username}")
  private String userName;
  @Value("${spring.datasource.password}")
  private String password;
  private MapHandler mapHandler;

  @PostConstruct
  public void initMapHandler() {
    this.mapHandler = new MapHandler(polygonsRepository, mapsRepository);
  }

  @RequestMapping(value = "/umgcchatbot", method = RequestMethod.POST,
      consumes = "application/json", produces = "application/json")
  public JSONObject main(@RequestBody WatsonArguments watsonArguments) throws Exception {
    JSONObject response = new JSONObject();

    switch (watsonArguments.getWebhookType()) {
      case "verifyAddress":
        AddressVerification av =
            new AddressVerification(city, state, mapQuestApiKey, mapHandler, parsingService);
        response.put("zoneID", av.verifyAddress(watsonArguments.getStreet1()));
        break;
      case "retrieveInformation":
        int zoneId = watsonArguments.getZoneID();
        // zoneID is 0 if Watson doesn't include it in the request
        if (zoneId == 0) {
          AddressVerification addressVerification =
                  new AddressVerification(city, state, mapQuestApiKey, mapHandler, parsingService);
          zoneId = addressVerification.verifyAddress(watsonArguments.getStreet1());
        }
        String zoneCode = polygonsRepository.findZONECODEByPOLYGONID(zoneId);
        ProcessRequest pr = new ProcessRequest(host, userName, password);
        String link = pr.retrieveInformation(watsonArguments.getType(), watsonArguments.getAction(), watsonArguments.getObject(), zoneCode);
        response.put("link", link);
        break;
    }

    return response;
  }
}
