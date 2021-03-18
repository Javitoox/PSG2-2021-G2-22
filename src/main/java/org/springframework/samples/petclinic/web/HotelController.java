package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/hotel")
public class HotelController {
	
	@Autowired
	public HotelController() {
		
	}
	
	@GetMapping
	public String init(Map<String, Object> model) {
		model.put("reservation", new Owner());
		return "hotel/reservation";
	}

}
