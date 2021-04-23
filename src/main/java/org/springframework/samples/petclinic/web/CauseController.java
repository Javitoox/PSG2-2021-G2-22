package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.CauseService;
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

	@Autowired
	public CauseController(CauseService causeService) {
		this.causeService = causeService;
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

//	@PostMapping(path="/save")
//	public String saveCause(@Valid Cause cause, BindingResult result, ModelMap modelmap) {
//		String v = "causes/listCause";
//		if(result.hasErrors()) {
//			modelmap.addAttribute("cause", cause);
//			return "causes/causeForm";
//		}else {
//			causeService.save(cause);
//			modelmap.addAttribute("message", "Causa guardada correctamente");
//			v = listCauses(modelmap);
//		}
//		return v;
//	}
	
	@GetMapping("/new")
    public String addNewCause(ModelMap model) {
        model.addAttribute("cause",new Cause());
        return "causes/causeForm";
    }
	
	@PostMapping("/donate/{id}")
    public String donateToCause(@PathVariable("id") Integer id, @Valid Cause cause, BindingResult result, ModelMap model) {
		if(cause.getDonations() == null || result.hasFieldErrors("donations")) {
			model.addAttribute("result", "Debe insertar un valor númerico");
		}else {
			Cause originalCause = this.causeService.findCausseById(id).orElse(null);
			Double total = originalCause.getDonations() + cause.getDonations();
			if(total > originalCause.getGoal()) {
				model.addAttribute("result", "Debe insertar un valor cuya suma a las donaciones no supere el objetivo de la causa");
			}else {
				originalCause.setDonations(total);
				this.causeService.saveCause(originalCause);
				model.addAttribute("result", "Donación realizada correctamente");
			}
		}
		Collection<Cause> causes = this.causeService.findAll();
		model.addAttribute("causes", causes);
		return "causes/listCause";
    }

	@PostMapping("/new")
    public String saveNewCausa(@Valid Cause cause, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
        	model.addAttribute("cause", cause);
        	return "causes/causeForm";
        } else {
        	cause.setDonations(0.);
        	causeService.saveCause(cause);
        	return listCauses(model);
        }
    }

}