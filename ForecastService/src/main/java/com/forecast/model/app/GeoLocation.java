package com.forecast.model.app;

public class GeoLocation {
	private Double latitude, longitude;
	
	public GeoLocation(Double latitude, Double longitude) {
		if(latitude < -90 || latitude > 90) 
			throw new IllegalArgumentException("Latitude out of range. Accepted range is from -90 to 90.");
		if(longitude < -180 || longitude > 180) 
			throw new IllegalArgumentException("Longitude out of range. Accepted range is from -180 to 180.");
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return "GeoLocation [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
}
