package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Component;

@Component
public class PetFormatter implements Formatter<Pet>{
	
	private final PetService petService;

	@Autowired
	public PetFormatter(PetService petService) {
		this.petService = petService;
	}

	@Override
	public String print(Pet object, Locale locale) {
		return object.getName()+" con identificador:"+object.getId();
	}

	@Override
	public Pet parse(String text, Locale locale) throws ParseException {
		String[] s = text.split(":");
		Pet result = this.petService.findPetById(Integer.parseInt(s[s.length-1]));
		if(result != null) {
			return result;
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
