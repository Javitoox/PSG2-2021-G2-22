package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		return v;
	}
	
	@GetMapping("/{causaId}/details")
	public String causeDetails(ModelMap model, @PathVariable("causeId") int causeId) {
		model.addAttribute("cause", causeService.findCauseById(causeId));
		return "/causes/listCauseDetails";
	}

	
	@GetMapping("/new")
    public String addNewCause(ModelMap model,Authentication authentication) {
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


	@PostMapping("/new")
    public String saveNewCausa(@Valid Cause cause, BindingResult result, ModelMap model,Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Owner owner = this.ownerService.findOwnerByUsername(userDetails.getUsername());
		
		
        if (result.hasErrors()) {
        	model.addAttribute("cause", cause);
        	return "causes/causeForm";
        } else {
        	model.put("owner", owner.getId());
        	causeService.saveCause(cause);
        	return listCauses(model);
        }
    }

}