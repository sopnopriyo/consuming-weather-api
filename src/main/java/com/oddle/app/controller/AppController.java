package com.oddle.app.controller;

import javax.validation.Valid;

import com.oddle.app.model.City;
import com.oddle.app.model.CityWeatherRecord;
import com.oddle.app.service.CityService;
import com.oddle.app.service.CityWeatherRecordService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@PropertySource(value = { "classpath:application.properties" })
@Controller
public class AppController {

    @Autowired
    private Environment environment;

    @Autowired
    CityService cityService;

    @Autowired
    CityWeatherRecordService cityWeatherRecordService;

    @RequestMapping(value = {"/","/weather"}, method = RequestMethod.GET)
    public String list(ModelMap model) {
        City city = new City();
        model.addAttribute("city",city);
        model.addAttribute("weatherHistories",cityWeatherRecordService.findAllCityWeatherRecord());
        return "weather";
    }

    @RequestMapping(value = {"/list"}, method = RequestMethod.POST)
    public String search(ModelMap model, @Valid City cityForm, BindingResult result) {

        City cityVar = new City();
        model.addAttribute("city", cityVar);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(environment.getProperty("weather.api_url"))
                .queryParam("q", cityForm.getCityName())
                .queryParam("appid", environment.getProperty("weather.app_id"));
        HttpEntity<?> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(
                    builder.build().encode().toUri(),
                    HttpMethod.GET,
                    entity,
                    String.class);
        }
        catch (HttpClientErrorException e){
            model.addAttribute("errorMessage","Please enter a valid city name".toString());
            return "weather";
        }
       catch (HttpServerErrorException e){
           model.addAttribute("errorMessage","Error occured while processing the request".toString());
           return "weather";
       }
        try{

            JSONParser jsonParser = new JSONParser();
            JSONObject rootJsonObject = (JSONObject) jsonParser.parse(response.getBody());
            JSONArray rootJsonArray = (JSONArray) rootJsonObject.get("list");

            for(Object jsonObject:rootJsonArray){

                JSONObject jsonObjectArrayItem = (JSONObject) jsonObject;
                City city = new City();
                city.setCityName(jsonObjectArrayItem.get("name").toString());
                JSONArray jsonWeatherArray = (JSONArray) jsonObjectArrayItem.get("weather");
                String weatherCondition = (String) ((JSONObject)jsonWeatherArray.get(0)).get("main");
                JSONObject jsonObjectForMainInfo = (JSONObject) jsonObjectArrayItem.get("main");
                String windSpeed = ((JSONObject) jsonObjectArrayItem.get("wind")).get("speed").toString();

                CityWeatherRecord cityWeatherRecord  = new CityWeatherRecord();
                cityWeatherRecord.setTimestamp(Integer.parseInt(jsonObjectArrayItem.get("dt").toString()));
                cityWeatherRecord.setTemperature(Double.parseDouble(jsonObjectForMainInfo.get("temp").toString()));
                cityWeatherRecord.setPressure(Double.parseDouble(jsonObjectForMainInfo.get("pressure").toString()));
                cityWeatherRecord.setHumidity(Double.parseDouble(jsonObjectForMainInfo.get("humidity").toString()));
                cityWeatherRecord.setTemperatureMin(Double.parseDouble(jsonObjectForMainInfo.get("temp_min").toString()));
                cityWeatherRecord.setTemperatureMax(Double.parseDouble(jsonObjectForMainInfo.get("temp_max").toString()));
                cityWeatherRecord.setWindSpeed(Double.parseDouble(windSpeed));
                cityWeatherRecord.setWeatherCondition(weatherCondition);
                if(cityService.isUniqueCity(city.getCityName())==true){

                    //duplicate record will be removed in the DAO
                    cityService.save(city);
                    cityWeatherRecord.setCity(cityService.findByCityName(city.getCityName()));
                }
                else {
                    cityService.findByCityName(city.getCityName());
                    cityWeatherRecord.setCity(cityService.findByCityName(city.getCityName()));
                }

                //duplicate record will be removed in the DAO
                cityWeatherRecordService.save(cityWeatherRecord);

            }

        }catch (Exception e){
            model.addAttribute("errorMessage","Error occured while processing your reqest".toString());
            return "weather";
        }
        model.addAttribute("weatherHistories",cityWeatherRecordService.findAllByCityName(cityForm.getCityName()));
        return "weather-list";
    }

	@RequestMapping(value = { "/delete-weather-record-{id}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable Integer id) {
		cityWeatherRecordService.deleteById(id);
		return "redirect:/list";
	}



}
