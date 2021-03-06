package org.springframework.samples.petclinic.web;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( value = "/health")
public class HealthController implements HealthIndicator{
	
	@GetMapping
	public String listCauses(ModelMap model) {
		String v = "health/info";
		model.addAttribute("health", health().getStatus());
		return v;
	}

	@Override
    public Health health() {
        int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.down()
              .withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }
    
    public int check() {
    	// Our logic to check health
    	return 0;
    }

}
