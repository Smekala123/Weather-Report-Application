package com.forecast.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forecast.service.DarkskyForecastService;

@Controller
public class ForecastController {
	
	@Autowired
	private DarkskyForecastService darkskyForecastService;
	
	@RequestMapping("/")
	public String getHomePage(Model model) {
		model.addAttribute("time", LocalDate.now());
		model.addAttribute("places", darkskyForecastService.getForecastForAll());
		return "forecast";
	}
}