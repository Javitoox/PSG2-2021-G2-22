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

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ReservationService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
	private static final String OWNERS_INIT_REDIRECT = "redirect:/owners/{ownerId}";

	private final PetService petService;
	private final OwnerService ownerService;
	private final ReservationService reservationService;

	@Autowired
	public PetController(PetService petService, OwnerService ownerService, ReservationService reservationService) {
		this.petService = petService;
		this.ownerService = ownerService;
		this.reservationService = reservationService;

	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.petService.findPetTypes();
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") int ownerId) {
		return this.ownerService.findOwnerById(ownerId);
	}

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("pet")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PetValidator());
	}

	@GetMapping(value = "/pets/new")
	public String initCreationForm(Owner owner, ModelMap model) {
		Pet pet = new Pet();
		owner.addPet(pet);
		model.put("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/pets/new")
	public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				owner.addPet(pet);
				this.petService.savePet(pet);
			} catch (DuplicatedPetNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
			}
			return OWNERS_INIT_REDIRECT;
		}
	}

	@GetMapping(value = "/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable("petId") int petId, ModelMap model) {
		Pet pet = this.petService.findPetById(petId);
		model.put("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	/**
	 *
	 * @param pet
	 * @param result
	 * @param petId
	 * @param model
	 * @param owner
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/pets/{petId}/edit")
	public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, @PathVariable("petId") int petId,
			ModelMap model) {
		if (result.hasErrors()) {
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			Pet petToUpdate = this.petService.findPetById(petId);
			BeanUtils.copyProperties(pet, petToUpdate, "id", "owner", "visits");
			try {
				this.petService.savePet(petToUpdate);
			} catch (DuplicatedPetNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
			}
			return OWNERS_INIT_REDIRECT;
		}
	}

	@GetMapping(value = "/pets/{petId}/delete")
	public String processDeletePet(@PathVariable("petId") int petId, Owner owner, Model model) {
		Pet pet = petService.findPetById(petId);
		Collection<Reservation> reservations = reservationService.findReservationsByPetId(petId);
		if (pet != null && pet.getOwner().equals(owner)) {
			ownerService.findOwnerById(owner.getId()).removePet(pet);
			reservationService.deleteAllReservations(reservations);
			petService.deletePet(pet);
			ownerService.saveOwner(owner);
			return OWNERS_INIT_REDIRECT;
		} else {
			return OWNERS_INIT_REDIRECT;
		}
	}

	@GetMapping(value = "/pets/{petId}/inAdoption")
	public ModelAndView inAdoption(@PathVariable("petId") int petId, Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Integer loggedOwnerId = this.ownerService.findOwnerByUsername(userDetails.getUsername()).getId();
		mav.addObject("loggedOwner", loggedOwnerId);

		Pet pet = petService.findPetById(petId);

		if (pet.getOwner().getId() != loggedOwnerId) {
			mav.setViewName("noPermission");
			return mav;
		} else if (pet.getInAdoption() != true) {

			pet.setInAdoption(true);
			try {
				petService.savePet(pet);
			} catch (DataAccessException | DuplicatedPetNameException e) {
				e.printStackTrace();
			}
			mav.setViewName("redirect:/adoptions");
			return mav;
		} else {
			throw new IllegalArgumentException("Pet already in adoption");
		}
	}

}
