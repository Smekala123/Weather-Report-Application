package com.forecast.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.forecast.data.ForecastItem;
import com.forecast.data.ForecastItemRepository;
import com.forecast.model.app.GeoLocation;
import com.forecast.model.app.Place;
import com.forecast.service.model.darksky.DarkskyResponse;

@Service
public class DarkskyForecastService{
	static private String DARKSKY_BASE_URI = "forecast.darksky.base.uri"; 
	private static final Logger LOGGER=LoggerFactory.getLogger(DarkskyResponse.class);
	
	@Autowired
	private ForecastItemRepository forecastRepo;
	@Autowired
	private Environment env;
	@Autowired
	private GeoLocationService geoLocationService;
	@Autowired
	private RestTemplate restTemplate;

	private List<ForecastItem> forecastsCache;

	/*
	 * Queries Darksky with the geographic locations passed
	 */
	public DarkskyResponse getWeatherForecast(GeoLocation location) {
		String locationArgs = location.getLatitude() +","+location.getLongitude();
		String uri = env.getProperty(DARKSKY_BASE_URI) + "/" + locationArgs;
		LOGGER.info("Outbound request to Darksky : {}", uri);
		return restTemplate.getForObject(uri, DarkskyResponse.class);
	}

	/*
	 * Returns the forecasts available in the cache for all the cities. 
	 * If the forecasts are not available in the cache then it fetches from the MongoDb.
	 * If the forecasts are not available in the MongoDb then it fetches from Darksky API 
	 */
	public List<Place> getForecastForAll() {
		if(forecastsCache != null && !forecastsCache.isEmpty()) return getPlacesFromForecastItems(forecastsCache);
		
		forecastsCache = forecastRepo.findAll();
		if(forecastsCache !=null && !forecastsCache.isEmpty()) return getPlacesFromForecastItems(forecastsCache);
		
		forecastsCache = new ArrayList<ForecastItem>();
		List<Place> places = new ArrayList<Place>();
		for(Entry<String, GeoLocation> location : geoLocationService.allLocations().entrySet()) {
			DarkskyResponse respone = getWeatherForecast(location.getValue());
			ForecastItem forecast = new ForecastItem();
			forecast.setDate(LocalDate.now());
			forecast.setPlace(location.getKey());
			forecast.setForecast(respone);
			forecastsCache.add(forecast);
			places.add(getPlaceFromForecastItem(forecast));
		}
		forecastRepo.save(forecastsCache);
		return places;
	}
	
	/*
	 * Prepares Place from ForecastItem
	 */
	private Place getPlaceFromForecastItem(ForecastItem forecast) {
		Place place = new Place();
		place.setPlaceName(forecast.getPlace());
		place.setHumidity(forecast.getForecast().getCurrently().getHumidity());
		place.setTemperature(forecast.getForecast().getCurrently().getTemperature());
		place.setWindSpeed(forecast.getForecast().getCurrently().getWindSpeed());
		return place;
	}
	
	/*
	 * Prepares Collection of Place from Collection of ForecastItem
	 */
	private List<Place> getPlacesFromForecastItems(List<ForecastItem> forecasts){
		List<Place> places = new ArrayList<Place>();
		for(ForecastItem forecast : forecasts) {
			places.add(getPlaceFromForecastItem(forecast));
		}
		return places;
	}
	
}
