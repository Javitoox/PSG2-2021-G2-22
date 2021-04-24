package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cause;

import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
class CauseServiceTests {

	
	@Autowired
	protected CauseService causeService;

	private static Cause cause;
	
	@BeforeAll
	void data() {
		
		cause = new Cause();
		cause.setName("Operacion para Botas");
		cause.setDescription("El gato Botas necesita una operacion en un ojo");
		cause.setGoal(455.56);
		cause.setOrganization("SaveCats");
		this.causeService.saveCause(cause);
		
	}
	
	@Test
	void shouldGetAtLeastOneCause()  {
		Collection<Cause> cause = this.causeService.allCauses();
		assertThat(cause.size()).isGreaterThan(0);
	}
	
	
}
