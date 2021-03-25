package org.springframework.samples.petclinic.util;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ReservationValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Reservation.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Reservation reservation = (Reservation) target;

		LocalDate start = reservation.getStart();
		LocalDate end = reservation.getEnd();
		String level = reservation.getLevel();
		Pet pet = reservation.getPet();
		
		if (start != null && end != null && start.isAfter(end)) {
			errors.rejectValue("start", "The start date cannot be later than the end date",
					"The start date cannot be later than the end date");
		}
		
		if(level != null && !level.equals("STANDARD") && !level.equals("VIP")) {
			errors.rejectValue("level", "The reservation level must be standard or vip",
					"The reservation level must be standard or vip");
		}
		
		if(pet == null) {
			errors.rejectValue("pet", "Required valid field", "Required valid field");
		}

	}

}