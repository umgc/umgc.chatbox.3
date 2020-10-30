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
    int zoneId = watsonArguments.getZoneID();


    // zoneID is 0 if Watson doesn't include it in the request
    if (zoneId == 0) {
      AddressVerification addressVerification =
          new AddressVerification(city, state, mapQuestApiKey, mapHandler, parsingService);
      zoneId = addressVerification.verifyAddress(watsonArguments.getStreet1());
    }

    switch (watsonArguments.getWebhookType()) {
      case "verifyAddress":
        AddressVerification av =
            new AddressVerification(city, state, mapQuestApiKey, mapHandler, parsingService);
        response.put("zoneID", av.verifyAddress(watsonArguments.getStreet1()));
        break;
      case "retrieveInformation":
        String zoneCodeInformation = polygonsRepository.findZONECODEByPOLYGONID(zoneId);
        ProcessRequest processRequestRetrieveInformation =
            new ProcessRequest(host, userName, password);
        response = new JSONObject(
            processRequestRetrieveInformation.retrieveInformation(watsonArguments.getType(),
                watsonArguments.getAction(), watsonArguments.getObject(), zoneCodeInformation));
        break;
      case "retrieveZoneSymbol":
        ProcessRequest processRequestRetrieveZoneSymbol =
            new ProcessRequest(host, userName, password);
        response = new JSONObject(processRequestRetrieveZoneSymbol.retrieveZoneSymbol(zoneId));
        break;
      case "retrieveStandard":
        String zoneCodeStandard = polygonsRepository.findZONECODEByPOLYGONID(zoneId);
        ProcessRequest processRequestRetrieveStandard =
            new ProcessRequest(host, userName, password);
        response = new JSONObject(
            processRequestRetrieveStandard.retrieveDevelopmentStandardsInfo(zoneCodeStandard));
        break;
      default:
        response.put("error", "Missing required webhookType parameter");
    }

    return response;
  }
}
