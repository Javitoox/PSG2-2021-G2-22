package org.springframework.samples.petclinic.util;

import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class AdoptionValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return Adoption.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Adoption adoption = (Adoption) target;
		String description = adoption.getDescription();
		
		if(description == null) {
			errors.rejectValue("description", "Campo requerido", "Campo requerido");
		}
	}
}
