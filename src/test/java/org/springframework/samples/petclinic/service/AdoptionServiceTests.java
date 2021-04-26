package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.AdoptionStateType;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AdoptionServiceTests {
	
	 @Autowired
		protected AdoptionService adoptionService;
	
	@Test
	void acceptAdoptionApplicationTest() {
		Adoption adoption = new Adoption();
		adoption.setId(1);
		adoption.setOwner("owner1");
		adoption.setPossibleOwner("owner4");
		adoption.setPet(new Pet());
		adoption.setAdoptionStateType(AdoptionStateType.PENDING);
		
		this.adoptionService.acceptAdoptionApplication(adoption);
		this.adoptionService.saveAdoption(adoption);
		
		assertThat(adoption.getAdoptionStateType()).isEqualTo(AdoptionStateType.ACCEPTED);
		
	}
	
	@Test
	void denyAdoptionApplicationTest() {
		Adoption adoption = new Adoption();
		adoption.setId(1);
		adoption.setOwner("owner1");
		adoption.setPossibleOwner("owner4");
		adoption.setPet(new Pet());
		adoption.setAdoptionStateType(AdoptionStateType.PENDING);
		
		this.adoptionService.denyAdoptionApplication(adoption);
		this.adoptionService.saveAdoption(adoption);
		
		assertThat(adoption.getAdoptionStateType()).isEqualTo(AdoptionStateType.DECLINED);
	}
	
	@Test
	void findAllAdoptionsWithPendingStateTest() {
		Adoption adoption = new Adoption();
		adoption.setId(1);
		adoption.setOwner("owner1");
		adoption.setPossibleOwner("owner4");
		adoption.setPet(new Pet());
		adoption.setAdoptionStateType(AdoptionStateType.PENDING);
		
		Adoption adoption2 = new Adoption();
		adoption2.setId(2);
		adoption2.setOwner("owner2");
		adoption2.setPossibleOwner("owner3");
		adoption2.setPet(new Pet());
		adoption2.setAdoptionStateType(AdoptionStateType.PENDING);
		
		Adoption adoption3 = new Adoption();
		adoption3.setId(3);
		adoption3.setOwner("owner6");
		adoption3.setPossibleOwner("owner7");
		adoption3.setPet(new Pet());
		adoption3.setAdoptionStateType(AdoptionStateType.ACCEPTED);
		
		List<Adoption> adoptions = new ArrayList<>();
		adoptions.add(adoption);
		adoptions.add(adoption2);
		this.adoptionService.findAllAdoptionsWithPendingState(adoptions);
		
		assertThat(adoptions.size()).isEqualTo(2);
	}
	

}
