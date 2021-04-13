package org.springframework.samples.petclinic.web;


import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import java.util.Collection;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.util.ReservationValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causas")
public class CausaController {

	@Autowired
	CausaService causaService;


	@GetMapping
	public String listCausas(ModelMap model) {
		String vista = "causas/listCausa";
		Collection<Causa> Causa = causaService.findAll();
		model.addAttribute("causas", Causa);
		return vista;
	}


	@PostMapping(path="/save")
	public String guardarCausa(@Valid Causa causa, BindingResult result, ModelMap modelmap) {
		String vista = "causas/listCausa";
		if(result.hasErrors()) {
			modelmap.addAttribute("causa", causa);
			return "causas/causaForm";
		}else {
			causaService.save(causa);
			modelmap.addAttribute("message", "Causa guardada correctamente");
			vista = listCausas(modelmap);
		}
		return vista;
	}

	@GetMapping(path="/new")
	public String crearCausa(ModelMap modelmap) {
		String vista = "causas/causaForm";
		modelmap.addAttribute("causa", new Causa());
		return vista;
	}

}