package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/hotel")
public class HotelController {
	
	private final ReservationService reservationService;
	
	private final OwnerService ownerService;
	
	@Autowired
	public HotelController(ReservationService reservationService, OwnerService ownerService) {
		this.reservationService = reservationService;
		this.ownerService = ownerService;
	}
	
	@GetMapping
	public String initCreationForm(Map<String, Object> model, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Owner owner = this.ownerService.findOwnerByUsername(userDetails.getUsername());
		model.put("reservation", new Reservation());
		return "hotel/reservation";
	}
	
	@PostMapping
	public String createHotelReservation(@Valid Reservation reservation, BindingResult result ) {
		if (result.hasErrors()) {
			return "hotel/reservation";
		}else {
			this.reservationService.saveReservation(reservation);
			return "welcome";
		}
	}
	
	@GetMapping(value = "/list")
	public String listReservations(Map<String, Object> model) {
		model.put("reservations", this.reservationService.allReservations());
		return "hotel/reservationList";
	}

}
