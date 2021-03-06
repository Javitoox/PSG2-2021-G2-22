/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {
	
	private static final String VETS_REDIRECT = "redirect:/vets";

	private final VetService vetService;

	@Autowired
	public VetController(VetService clinicService) {
		this.vetService = clinicService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@ModelAttribute("specialties")
	public Collection<Specialty> populatePets() {
		try {
			return this.vetService.findSpecialties();
		}catch(Exception e) {
			return new ArrayList<Specialty>();
		}
	}
	
	@GetMapping(value = "/vets/new")
	public String initCreationForm(Map<String, Object> model) {
		Vet vet = new Vet();
		model.put("vet", vet);
		model.put("specialties", vetService.findSpecialties());
		return "vets/vetNew";
	}

	@PostMapping(value = "/vets/new")
	public String processCreationForm(@Valid Vet vet, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()|| vet.getSpecialty2()==null || vet.getSpecialty2()=="") {
			if(vet.getSpecialty2()==null || vet.getSpecialty2()=="") {
				FieldError e = new FieldError("vet", "specialty2", "seleccione alguna especialidad");
				result.addError(e);
			}
			return "vets/vetNew";
		}
		else {
			//creating owner, user and authorities
			Set<Specialty> specialtiesSelected = this.specialtiesParse(vet.getSpecialty2());
			vet.setSpecialtiesInternal(specialtiesSelected);
			this.vetService.saveVet(vet);	
			
			return VETS_REDIRECT;
		}
	}

	@GetMapping(value = { "/vets" })
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(value = { "/vets.xml"})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}

	
	@GetMapping(value = "/vets/{vetId}/edit")
	public String initUpdateVetForm(@PathVariable("vetId") int vetId, Map<String, Object> model) {
		Vet vet = this.vetService.findVetById(vetId);
		model.put("vet", vet);
		model.put("specialties", vetService.findSpecialties());
		return "vets/vetEdit";
	}

	@PostMapping(value = "/vets/{vetId}/edit")
	public String processUpdateVetForm(@Valid Vet vet, BindingResult result,@PathVariable("vetId") int vetId) {
		if (result.hasErrors() || vet.getSpecialty2()==null || vet.getSpecialty2()=="") {
			if(vet.getSpecialty2()==null || vet.getSpecialty2()=="") {
				FieldError e = new FieldError("vet", "specialty2", "seleccione alguna especialidad");
				result.addError(e);
			}
			vet.setId(vetId);
			return "vets/vetEdit";
		}
		else {
			Set<Specialty> specialtiesSelected = this.specialtiesParse(vet.getSpecialty2());
			vet.setSpecialtiesInternal(specialtiesSelected);
			vet.setId(vetId);
			this.vetService.saveVet(vet);
			return VETS_REDIRECT;
		}
	}
	
	public Set<Specialty> specialtiesParse(String text){
		Set<Specialty> res = new HashSet<>();
		
		String[] specialties = text.split(",");
		for(int i=0;i<specialties.length;i++) {
			String stringSpecialty = specialties[i];
			Specialty specialtyParsed = this.vetService.findSpecialtyByName(stringSpecialty);
			res.add(specialtyParsed);
		}
		
		return res;
	}

	@GetMapping(value = "/vets/{vetId}/delete")
	public String deleteVet(@PathVariable("vetId")int vetId, Model model) {
		Vet vet = vetService.findVetById(vetId);
		vetService.deleteVet(vet);
		return VETS_REDIRECT;
	}
  
}