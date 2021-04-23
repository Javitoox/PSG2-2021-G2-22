package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AdoptionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AdoptionControllerTests {

	private static final int TEST_OWNER_ID = 1;

	private static final int TEST_PET_ID = 1;

	@MockBean
	private AdoptionService adoptionService;

	@MockBean
	private OwnerService ownerService;

	@MockBean
	private PetService petService;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(value = "spring")
	@Test
	void testGetAdoptionList() throws Exception {
		given(this.ownerService.findOwnerByUsername(any())).willReturn(new Owner());
		given(this.petService.findPetsInAdoption()).willReturn(new ArrayList<Pet>());

		mockMvc.perform(get("/adoptions"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("possibleOwner"))
			.andExpect(model().attributeExists("pets"))
			.andExpect(view().name("adoptions/adoptionList"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitApplyForm() throws Exception {
		Owner owner = new Owner();
		owner.setId(TEST_OWNER_ID);

		Pet pet = new Pet();
		pet.setId(TEST_PET_ID);

		given(this.ownerService.findOwnerByUsername("spring")).willReturn(owner);
		given(this.petService.findPetById(TEST_PET_ID)).willReturn(pet);

		mockMvc.perform(get("/adoptions/{petId}/applicationForm", TEST_PET_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("possibleOwner"))
			.andExpect(model().attributeExists("originalOwner"))
			.andExpect(model().attributeExists("adoption"))
			.andExpect(view().name("/adoptions/applicationForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationNoRegisteredOwner() throws Exception {
		given(this.ownerService.findOwnerByUsername(any())).willReturn(null);

		mockMvc.perform(get("/adoptions/{petId}/applicationForm", TEST_PET_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testSendApplicationForm() throws Exception {
		Pet pet = new Pet();
		pet.setId(TEST_PET_ID);

		given(this.petService.findPetById(TEST_PET_ID)).willReturn(pet);

		mockMvc.perform(post("/adoptions/{petId}/applicationForm", TEST_PET_ID).with(csrf())
				.param("owner", "owner1")
				.param("possibleOwner", "owner2")
				.param("description", "My description")
				.param("adoptionStateType", "PENDING")
				.param("pet","TEST_PET_ID"))
		.andExpect(status().isOk())
		.andExpect(view().name("welcome"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testSendApplicationFormWithoutDescription() throws Exception {
		Pet pet = new Pet();
		pet.setId(TEST_PET_ID);

		given(this.petService.findPetById(TEST_PET_ID)).willReturn(pet);

		mockMvc.perform(post("/adoptions/{petId}/applicationForm", TEST_PET_ID).with(csrf()).param("owner", "owner1")
				.param("possibleOwner", "owner2")
				.param("description", "")
				.param("AdoptionStateType", "PENDING"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasFieldErrors("adoption", "description"))
		.andExpect(model().attributeHasFieldErrorCode("adoption", "description", "Campo requerido"))
		.andExpect(view().name("adoptions/applicationForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testSendApplicationFormAlreadyExists() throws Exception {
		Owner owner = new Owner();
		owner.setId(TEST_OWNER_ID);
		
		Pet pet = new Pet();
		pet.setId(TEST_PET_ID);
		
		given(this.ownerService.findOwnerByUsername(any())).willReturn(owner);
		given(this.petService.findPetById(TEST_PET_ID)).willReturn(pet);
		given(adoptionService.findAdoptionByPossibleOwnerAndPet(owner.getUser().getUsername()
				, petService.findPetById(TEST_PET_ID))).willReturn(new Adoption());

		mockMvc.perform(post("/adoptions/{petId}/applicationForm", TEST_PET_ID).with(csrf()).param("owner", "owner1")
				.param("possibleOwner", "owner2")
				.param("description", "")
				.param("AdoptionStateType", "PENDING"))
		.andExpect(status().isOk())
		.andExpect(view().name("adoptions/existingAdoption"));
	}

}
