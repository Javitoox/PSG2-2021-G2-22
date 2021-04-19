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
		return v;
	}

	@PostMapping(path="/save")
	public String saveCause(@Valid Cause cause, BindingResult result, ModelMap modelmap) {
		String v = "causes/listCause";
		if(result.hasErrors()) {
			modelmap.addAttribute("cause", cause);
			return "causes/causeForm";
		}else {
			causeService.save(cause);
			modelmap.addAttribute("message", "Causa guardada correctamente");
			v = listCauses(modelmap);
		}
		return v;
	}

	@GetMapping(path="/new")
	public String createCause(ModelMap modelmap) {
		String v = "causes/causeForm";
		modelmap.addAttribute("cause", new Cause());
		return v;
	}

}