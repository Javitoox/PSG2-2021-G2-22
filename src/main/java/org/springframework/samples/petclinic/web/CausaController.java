package org.springframework.samples.petclinic.web;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/causas")

public class CausaController {
	private CausaService causaService;


	@Autowired
	public CausaController(CausaService causaService) {
		this.causaService = causaService;
	}

	@GetMapping(value="/nuevaCausa")
    public String addNewCausa(ModelMap model) {
        model.addAttribute("causa",new Causa());
        return "causas/causaForm";
    }


	@PostMapping(value="/nuevaCausa")
    public String saveNewCausa(@Valid Causa causa, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
        	model.addAttribute("causa", causa);
        	return "causas/causaForm";
        } else {
        	causaService.saveCausa(causa);
        	return listadoCausas(model);
        }
    }
	
	@PostMapping
	public String createCausa(@Valid Causa causa, BindingResult result ) {
		if (result.hasErrors()) {
			return "causas/causaForm";
		}else {
			this.causaService.saveCausa(causa);
			return "welcome";
		}
	}
	@GetMapping(value="/list")
	public String listadoCausas(ModelMap model) {
		List<Causa> causas = causaService.findAll();
		model.addAttribute("causas", causas);
		return "causas/causasList";
	}
}
