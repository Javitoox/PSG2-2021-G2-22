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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping( value = "/causes")
public class CauseController {
	
	private static final String CAUSES_RESULT = "result";
	private static final String CAUSES_CAUSE = "cause";
	
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
		model.addAttribute(CAUSES_RESULT, "¡Bienvenid@ a las causas disponibles en nuestra web!");
		model.addAttribute(CAUSES_CAUSE, new Cause());
		return v;
	}
	
	@GetMapping("/{causeId}")
	public String showCause(@PathVariable("causeId") Integer causeId, ModelMap model) {
		Cause cause = this.causeService.findCauseById(causeId).orElse(null);
		if(cause == null) {
			return "redirect:/causes";
		}else {
			model.addAttribute(CAUSES_CAUSE, cause);
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
			model.put(CAUSES_CAUSE,new Cause());
			return "causes/causeForm";
		}
        
    }
	
	@PostMapping("/donate/{id}")
    public String donateToCause(@PathVariable("id") Integer id, @Valid Cause cause, BindingResult result, Authentication authentication, ModelMap model) {
		if(cause.getDonations() == null || result.hasFieldErrors("donations") || cause.getDonations()<0) {
			model.addAttribute(CAUSES_RESULT, "Debe insertar un valor númerico positivo con máximo 2 decimales");
		}else {
			Cause originalCause = this.causeService.findCauseById(id).orElse(null);
			if(originalCause == null) {
				model.addAttribute(CAUSES_RESULT, "La causa no existe");
			}else {
				Double total = originalCause.getDonations() + cause.getDonations();
				if(total > originalCause.getGoal()) {
					model.addAttribute(CAUSES_RESULT, "Debe insertar un valor cuya suma a las donaciones no supere el objetivo de la causa");
				}else {
					UserDetails userDetails = (UserDetails) authentication.getPrincipal();
					Owner owner = this.ownerService.findOwnerByUsername(userDetails.getUsername());
					Donation donation = new Donation();
					donation.setAmount(cause.getDonations());
					donation.setOwner(owner);
					donation.setDate(LocalDate.now());
					this.causeService.updateDonationsCause(originalCause, total, donation);
					model.addAttribute(CAUSES_RESULT, "Donación realizada correctamente");
				}
			}
		}
		Collection<Cause> causes = this.causeService.findAll();
		model.addAttribute("causes", causes);
		return "causes/listCause";
    }

	@PostMapping("/new")
    public ModelAndView saveNewCausa(@Valid Cause cause, BindingResult result,Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Owner owner = this.ownerService.findOwnerByUsername(userDetails.getUsername());
		
		ModelAndView mv = new ModelAndView();
        if (result.hasErrors()) {
        	mv.addObject(CAUSES_CAUSE, cause);
        	mv.setViewName("causes/causeForm");
        	return mv;
        } else {
        	cause.setDonations(0.);
        	cause.setOwner(owner);
        	causeService.saveCause(cause);
        	mv.setViewName("redirect:/causes");
        	return mv;
        }
    }

}