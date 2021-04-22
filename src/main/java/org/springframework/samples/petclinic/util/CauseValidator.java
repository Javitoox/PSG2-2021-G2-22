package org.springframework.samples.petclinic.util;

import org.springframework.samples.petclinic.model.Cause;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CauseValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Cause.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Cause cause = (Cause) target;
		Double goal = cause.getGoal();
		
		
		
		if (goal <= 0) {
			errors.rejectValue("goal", "El valor del objetivo debe ser mayor que 0",
					"El valor del objetivo debe ser mayor que 0");
		}
		
		if(goal > 20000) {
			errors.rejectValue("goal", "El objetivo marcado no puede ser mayor a 20000",
					"El objetivo marcado no puede ser mayor a 20000");
		}
		if(String.class.isInstance(goal)) {
			errors.rejectValue("goal", "El objetivo marcado no puede contener caracteres del alfabeto",
					"El objetivo marcado no puede contener caracteres del alfabeto");
		}
		
	

	}

}
