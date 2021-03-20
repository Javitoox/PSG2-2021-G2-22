package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/hotel")
public class HotelController {
	
	private final ReservationService hotelService;
	
	@Autowired
	public HotelController(ReservationService hotelService) {
		this.hotelService = hotelService;
	}
	
	@GetMapping
	public String initCreationForm(Map<String, Object> model) {
		model.put("reservation", new Reservation());
		return "hotel/reservation";
	}
	
	@PostMapping
	public String createHotelReservation(@Valid Reservation reservation, BindingResult result ) {
		if (result.hasErrors()) {
			return "hotel/reservation";
		}else {
			this.hotelService.saveReservation(reservation);
			return "welcome";
		}
	}
	
	@GetMapping(value = "/list")
	public String listReservations() {
		return null;
	}

}
