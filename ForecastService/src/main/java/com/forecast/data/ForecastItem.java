package com.forecast.data;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.forecast.service.model.darksky.DarkskyResponse;

@Document
public class ForecastItem {
	@Id
	private String id;
	@Indexed
	private String place;
	private LocalDate date;
	private DarkskyResponse forecast;
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public DarkskyResponse getForecast() {
		return forecast;
	}
	public void setForecast(DarkskyResponse forecast) {
		this.forecast = forecast;
	}
}
