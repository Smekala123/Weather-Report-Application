package com.forecast.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.forecast.model.app.GeoLocation;

@Service
public class GeoLocationService {

	private static Map<String, GeoLocation> locationMap;
	
	//Temporary Hardcoding
	static {
		locationMap = new HashMap<String, GeoLocation>();
		locationMap.put("Campbell, CA", new GeoLocation(37.2807589,-121.9900006));
		locationMap.put("Omaha, NE", new GeoLocation(41.291774,-96.221333));
		locationMap.put("Austin, TX", new GeoLocation(30.3074624,-98.0335937));
		locationMap.put("Niseko, Japan", new GeoLocation(42.7926027,140.6145));
		locationMap.put("Nara, Japan", new GeoLocation(34.6578714,135.7520051));
		locationMap.put("Jakarta, Indonesia", new GeoLocation(-6.229728,106.6894305));
	}
	
	//Temporary Hardcoding
	public List<String> getPlaces(){
		return new ArrayList<>(locationMap.keySet());
	}
	
	public GeoLocation getGeoLocation(String place) {
		return locationMap.get(place);
	}
	
	public Map<String, GeoLocation> allLocations(){
		return locationMap;
	}
}
