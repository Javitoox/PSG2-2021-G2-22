package org.springframework.samples.petclinic.web;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ReservationService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.ReservationValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/hotel")
public class HotelController {
	
	private final ReservationService reservationService;
	private final PetService petService;
	private final OwnerService ownerService;
	
	@Autowired
	public HotelController(ReservationService reservationService, OwnerService ownerService,PetService petService) {
		this.reservationService = reservationService;
		this.ownerService = ownerService;
		this.petService = petService;
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
			model.put("pets", owner.getPets());
			model.put("reservation", new Reservation());
			return "hotel/reservation";
		}
	}
	
	@PostMapping
	public String createHotelReservation(@Valid Reservation reservation, BindingResult result) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);
		if (result.hasErrors() || violations.size() > 0) {
			if (violations.size() > 0) {
				for (ConstraintViolation<Reservation> v : violations) {
					FieldError e = new FieldError("reservation", v.getPropertyPath().toString(), v.getMessageTemplate());
					result.addError(e);
				}
			}
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

//	@GetMapping(value = "/hotel/{reservationId}/delete")
//	public String deleteReservation(@PathVariable("reservationId") int reservationId, ModelMap model) {
//		Optional<Reservation> reservation = reservationService.findReservationById(reservationId);
//		System.out.println("-------------------------------    "+ reservation);
//			Pet pet = reservation.get().getPet();
//			pet.removeReservation(reservation.get());
//			try {
//				petService.savePet(pet);
//			} catch (DataAccessException | DuplicatedPetNameException e) {
//				e.printStackTrace();
//			}
//		
//			try {
//				reservationService.deleteReservation(reservation.get());
//			} catch (DataAccessException e) {
//				e.printStackTrace();
//			}
//			return "hotel/reservationList";
//	}
	
}