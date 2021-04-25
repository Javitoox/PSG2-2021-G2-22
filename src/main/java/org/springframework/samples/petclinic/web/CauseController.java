package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( value = "/causes")
public class CauseController {
	
	private final CauseService causeService;
	private final OwnerService ownerService;

	@Autowired
	public CauseController(CauseService causeService, OwnerService ownerService) {
		this.causeService = causeService;
		this.ownerService = ownerService;
	}


	@GetMapping
	public String listCauses(ModelMap model) {
		String v = "causes/listCause";
		Collection<Cause> causes = this.causeService.findAll();
		model.addAttribute("causes", causes);
		model.addAttribute("result", "¡Bienvenid@ a las causas disponibles en nuestra web!");
		model.addAttribute("cause", new Cause());
		return v;
	}
	
	@GetMapping("/{causeId}")
	public String showCause(@PathVariable("causeId") Integer causeId, ModelMap model) {
		Cause cause = this.causeService.findCauseById(causeId).orElse(null);
		if(cause == null) {
			return "redirect:/causes";
		}else {
			model.addAttribute("cause", cause);
			return "/causes/listCauseDetails";
		}
	}
	
	@GetMapping("/new")
    public String addNewCause(ModelMap model, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Owner owner = this.ownerService.findOwnerByUsername(userDetails.getUsername());
		
		if(owner == null) {
			return "redirect:/login";
		}else {
			model.put("owner", owner.getId());
			model.put("cause",new Cause());
			return "causes/causeForm";
		}
        
    }
	
	@PostMapping("/donate/{id}")
    public String donateToCause(@PathVariable("id") Integer id, @Valid Cause cause, BindingResult result, Authentication authentication, ModelMap model) {
		if(cause.getDonations() == null || result.hasFieldErrors("donations")) {
			model.addAttribute("result", "Debe insertar un valor númerico");
		}else {
			Cause originalCause = this.causeService.findCauseById(id).orElse(null);
			Double total = originalCause.getDonations() + cause.getDonations();
			if(total > originalCause.getGoal()) {
				model.addAttribute("result", "Debe insertar un valor cuya suma a las donaciones no supere el objetivo de la causa");
			}else {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				Owner owner = this.ownerService.findOwnerByUsername(userDetails.getUsername());
				Donation donation = new Donation();
				donation.setAmount(cause.getDonations());
				donation.setOwner(owner);
				donation.setDate(LocalDate.now());
				this.causeService.updateDonationsCause(originalCause, total, donation);
				model.addAttribute("result", "Donación realizada correctamente");
			}
		}
		Collection<Cause> causes = this.causeService.findAll();
		model.addAttribute("causes", causes);
		return "causes/listCause";
    }

	@PostMapping("/new")
    public String saveNewCausa(@Valid Cause cause, BindingResult result, ModelMap model,Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Owner owner = this.ownerService.findOwnerByUsername(userDetails.getUsername());
		
		
        if (result.hasErrors()) {
        	model.addAttribute("cause", cause);
        	return "causes/causeForm";
        } else {
        	cause.setDonations(0.);
        	model.put("owner", owner.getId());
        	causeService.saveCause(cause);
        	return listCauses(model);
        }
    }

}