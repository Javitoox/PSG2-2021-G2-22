package org.springframework.samples.petclinic.util;

import java.time.LocalDate;
import java.util.logging.Logger;

import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ReservationValidator implements Validator {
	
	private static final Logger log = Logger.getLogger(ReservationValidator.class.getName());

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
		
		if (end != null && start.isAfter(end)) {
			errors.rejectValue("start", "The start date cannot be later than the end date",
					"The start date cannot be later than the end date");
		}
		
		log.info(level);
		if(level != "STANDARD" && level != "VIP") {
			errors.rejectValue("level", "The reservation level must be standard or vip",
					"The reservation level must be standard or vip");
		}

	}

}