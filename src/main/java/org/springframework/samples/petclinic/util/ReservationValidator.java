package org.springframework.samples.petclinic.util;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ReservationValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Reservation.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Reservation reservation = (Reservation) target;

		LocalDate start = reservation.getStart();
		LocalDate end = reservation.getEnd();
		String level = reservation.getLevel();
		Pet pet = reservation.getPet();
		
		if (start != null && end != null && start.isAfter(end)) {
			errors.rejectValue("start", "La fecha de inicio no puede ser posterior que la fecha de fin",
					"La fecha de inicio no puede ser posterior que la fecha de fin");
		}
		
		if(level != null && !level.equals("STANDARD") && !level.equals("VIP")) {
			errors.rejectValue("level", "El nivel de reserva debe ser STANDARD o VIP",
					"El nivel de reserva debe ser STANDARD o VIP");
		}
		
		if(pet == null) {
			errors.rejectValue("pet", "Campo requerido", "Campo requerido");
		}

	}

}