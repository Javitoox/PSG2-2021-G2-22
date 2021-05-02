package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ReservationService;
import org.springframework.samples.petclinic.util.ReservationValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/hotel")
public class HotelController {
	
	private static final String RESERVATION = "reservation";
	private static final String HOTEL_RESERVATION = "hotel/reservation";
	
	private final ReservationService reservationService;
	private final OwnerService ownerService;
	
	@Autowired
	public HotelController(ReservationService reservationService, OwnerService ownerService) {
		this.reservationService = reservationService;
		this.ownerService = ownerService;
	}
	
	@ModelAttribute("pets")
	public Collection<Pet> populatePets(Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Owner owner = this.ownerService.findOwnerByUsername(userDetails.getUsername());
			return owner.getPets();
		}catch(Exception e) {
			return new ArrayList<>();
		}
	}
	
	@InitBinder("reservation")
	public void initEventoBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ReservationValidator());
	}
	
	@GetMapping
	public String initCreationForm(Map<String, Object> model, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Owner owner = this.ownerService.findOwnerByUsername(userDetails.getUsername());
		
		if(owner == null) {
			return "hotel/noPets";
		}else {
			model.put(RESERVATION, new Reservation());
			return HOTEL_RESERVATION;
		}
	}
	
	@PostMapping
	public String createHotelReservation(@Valid Reservation reservation, BindingResult result, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Owner owner = this.ownerService.findOwnerByUsername(userDetails.getUsername());
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);
		
		if (result.hasErrors() || !violations.isEmpty() || (reservation.getPet() != null && !owner.getPets().contains(reservation.getPet()))) {
			if (!violations.isEmpty()) {
				for (ConstraintViolation<Reservation> v : violations) {
					FieldError e = new FieldError(RESERVATION, v.getPropertyPath().toString(), v.getMessageTemplate());
					result.addError(e);
				}
			}
			if(reservation.getPet() != null && !owner.getPets().contains(reservation.getPet())) {
				FieldError e = new FieldError(RESERVATION, "pet", "Debes seleccionar una mascota asociada a ti");
				result.addError(e);
			}
			return HOTEL_RESERVATION;
		}else {
			Boolean alreadyExists = this.reservationService.findConcurrentReservation(reservation.getStart(), 
					reservation.getEnd(), reservation.getPet().getId());
			if(Boolean.FALSE.equals(alreadyExists)) {
				this.reservationService.saveReservation(reservation);
				return "welcome";
			}else {
				FieldError e = new FieldError(RESERVATION, "start", "Ya ha realizado una reserva para dicha mascota con fechas solapadas");
				result.addError(e);
				return HOTEL_RESERVATION;
			}
		}
	}
	
	@GetMapping(value = "/list")
	public String listReservations(Map<String, Object> model) {
		model.put("reservations", this.reservationService.allReservations());
		return "hotel/reservationList";
	}
	
	@GetMapping(value = "/myReservations")
	public String myReservations(Map<String, Object> model, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		model.put("reservations", this.reservationService.myReservations(userDetails.getUsername()));
		return "hotel/reservationList";
	}
	
}
