package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/support")
public class SupportController {

	@GetMapping
	public String initView(Map<String, Object> model) {
		return "support/page";
	}
	
}
