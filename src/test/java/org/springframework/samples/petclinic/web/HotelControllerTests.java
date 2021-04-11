package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ReservationService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=HotelController.class,
includeFilters = @ComponentScan.Filter(value = PetFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class HotelControllerTests {
	
	@MockBean
	private ReservationService reservationService;
	
	@MockBean
	private OwnerService ownerService;
	
	@MockBean
	private PetService petService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(value = "spring")
    @Test
	void testInitCreationForm() throws Exception {
		given(this.ownerService.findOwnerByUsername(any())).willReturn(new Owner());
		
		mockMvc.perform(get("/hotel"))
		        .andExpect(status().isOk())
		        .andExpect(model().attributeExists("reservation"))
				.andExpect(view().name("hotel/reservation"));
     }
	
	@WithMockUser(value = "spring")
    @Test
	void testInitCreationNoPets() throws Exception {
		mockMvc.perform(get("/hotel"))
		        .andExpect(status().isOk())
				.andExpect(view().name("hotel/noPets"));
     }
	
	@WithMockUser(value = "spring")
	@Test
	void testCreateHotelReservation() throws Exception {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setId(1);
		owner.addPet(pet);
		given(this.ownerService.findOwnerByUsername(any())).willReturn(owner);
		given(this.petService.findPetById(anyInt())).willReturn(pet);
		given(this.reservationService.findConcurrentReservation(any(), any(), any())).willReturn(false);
		
		mockMvc.perform(post("/hotel")
							.with(csrf())
							.param("start", "2028-08-13")
							.param("end", "2028-08-15")
							.param("specialCares", "Food with a lot of proteins")
							.param("pet", "Leo with identifier:1")
		                    .param("level", "VIP"))
				.andExpect(status().isOk())
				.andExpect(view().name("welcome"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCreateHotelReservationAlreadyExists() throws Exception {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setId(1);
		owner.addPet(pet);
		given(this.ownerService.findOwnerByUsername(any())).willReturn(owner);
		given(this.petService.findPetById(anyInt())).willReturn(pet);
		given(this.reservationService.findConcurrentReservation(any(), any(), any())).willReturn(true);
		
		mockMvc.perform(post("/hotel")
							.with(csrf())
							.param("start", "2028-08-13")
							.param("end", "2028-08-15")
							.param("specialCares", "Food with a lot of proteins")
							.param("pet", "Leo with identifier:1")
		                    .param("level", "VIP"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("reservation", "start"))
				.andExpect(view().name("hotel/reservation"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCreateHotelReservationPetNull() throws Exception {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setId(1);
		owner.addPet(pet);
		given(this.ownerService.findOwnerByUsername(any())).willReturn(owner);
		given(this.petService.findPetById(anyInt())).willReturn(pet);
		
		mockMvc.perform(post("/hotel")
							.with(csrf())
							.param("start", "2028-08-13")
							.param("end", "2028-08-15")
							.param("specialCares", "Food with a lot of proteins")
							.param("pet", "")
		                    .param("level", "VIP"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("reservation", "pet"))
				.andExpect(model().attributeHasFieldErrorCode("reservation", "pet", "Campo requerido"))
				.andExpect(view().name("hotel/reservation"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCreateHotelReservationPastDate() throws Exception {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setId(1);
		owner.addPet(pet);
		given(this.ownerService.findOwnerByUsername(any())).willReturn(owner);
		given(this.petService.findPetById(anyInt())).willReturn(pet);
		
		mockMvc.perform(post("/hotel")
							.with(csrf())
							.param("start", "2018-08-13")
							.param("end", "2028-08-15")
							.param("specialCares", "Food with a lot of proteins")
							.param("pet", "Leo with identifier:1")
		                    .param("level", "VIP"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("reservation", "start"))
				.andExpect(view().name("hotel/reservation"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCreateHotelReservationNotPetAssociated() throws Exception {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setId(1);
		Pet pet2 = new Pet();
		pet.setId(2);
		owner.addPet(pet);
		given(this.ownerService.findOwnerByUsername(any())).willReturn(owner);
		given(this.petService.findPetById(anyInt())).willReturn(pet2);
		
		mockMvc.perform(post("/hotel")
							.with(csrf())
							.param("start", "2028-08-13")
							.param("end", "2028-08-15")
							.param("specialCares", "Food with a lot of proteins")
							.param("pet", "Max with identifier:2")
		                    .param("level", "VIP"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("reservation", "pet"))
				.andExpect(view().name("hotel/reservation"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCreateHotelReservationNotAllowLevel() throws Exception {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setId(1);
		owner.addPet(pet);
		given(this.ownerService.findOwnerByUsername(any())).willReturn(owner);
		given(this.petService.findPetById(anyInt())).willReturn(pet);
		
		mockMvc.perform(post("/hotel")
							.with(csrf())
							.param("start", "2028-08-13")
							.param("end", "2028-08-15")
							.param("specialCares", "Food with a lot of proteins")
							.param("pet", "Leo with identifier:1")
		                    .param("level", "VIPsss"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("reservation", "level"))
				.andExpect(model().attributeHasFieldErrorCode("reservation", "level", "El nivel de reserva debe ser STANDARD o VIP"))
				.andExpect(view().name("hotel/reservation"));
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testListReservations() throws Exception {
		mockMvc.perform(get("/hotel/list"))
		        .andExpect(status().isOk())
				.andExpect(view().name("hotel/reservationList"));
     }
	
}
