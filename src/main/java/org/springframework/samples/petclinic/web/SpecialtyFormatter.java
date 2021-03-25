package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;

import org.springframework.format.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Component;

@Component
public class SpecialtyFormatter implements Formatter<Specialty>{
	
	private final VetService veService;

	@Autowired
	public SpecialtyFormatter(VetService veService) {
		this.veService = veService;
	}

	@Override
	public String print(Specialty specialty, Locale locale) {
		return specialty.getName();
	}

	@Override
	public Specialty parse(String text, Locale locale) throws ParseException {
		Collection<Specialty> findSpecialties = this.veService.findSpecialties();
		for (Specialty specialty : findSpecialties) {
			if (specialty.getName().equals(text)) {
				return specialty;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
