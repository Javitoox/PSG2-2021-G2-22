package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=CauseController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class CauseControllerTests {
	
	@MockBean
	private CauseService causeService;
	
	@MockBean
	private OwnerService ownerService;
	
	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(value = "spring")
    @Test
	void testListCauses() throws Exception {
		given(this.causeService.findAll()).willReturn(new ArrayList<>());
		
		mockMvc.perform(get("/causes"))
		        .andExpect(status().isOk())
		        .andExpect(model().attributeExists("causes"))
		        .andExpect(model().attributeExists("result"))
		        .andExpect(model().attributeExists("cause"))
				.andExpect(view().name("causes/listCause"));
     }
	
	@WithMockUser(value = "spring")
	@Test
	void testDonateToCause() throws Exception {
		Cause cause = new Cause();
		cause.setId(1);
		cause.setDonations(0.);
		cause.setGoal(100.);
		Optional<Cause> optional = Optional.of(cause);
		given(this.causeService.findCauseById(1)).willReturn(optional);
		given(this.causeService.findAll()).willReturn(new ArrayList<>());
		given(this.ownerService.findOwnerByUsername(any())).willReturn(new Owner());
		
		mockMvc.perform(post("/causes/donate/1")
							.with(csrf())
							.param("donations", "20.0"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("result", "Donación realizada correctamente"))
				.andExpect(model().attributeExists("causes"))
				.andExpect(view().name("causes/listCause"));
		
		verify(causeService, times(1)).updateDonationsCause(any(), any(), any());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDonateToCauseNotNumber() throws Exception {
		Cause cause = new Cause();
		cause.setId(1);
		cause.setDonations(0.);
		cause.setGoal(100.);
		Optional<Cause> optional = Optional.of(cause);
		given(this.causeService.findCauseById(1)).willReturn(optional);
		given(this.causeService.findAll()).willReturn(new ArrayList<>());
		given(this.ownerService.findOwnerByUsername(any())).willReturn(new Owner());
		
		mockMvc.perform(post("/causes/donate/1")
							.with(csrf())
							.param("donations", "dddd"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("result", "Debe insertar un valor númerico"))
				.andExpect(model().attributeExists("causes"))
				.andExpect(view().name("causes/listCause"));
		
		verify(causeService, times(0)).updateDonationsCause(any(), any(), any());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDonateToCauseExcesive() throws Exception {
		Cause cause = new Cause();
		cause.setId(1);
		cause.setDonations(0.);
		cause.setGoal(100.);
		Optional<Cause> optional = Optional.of(cause);
		given(this.causeService.findCauseById(1)).willReturn(optional);
		given(this.causeService.findAll()).willReturn(new ArrayList<>());
		given(this.ownerService.findOwnerByUsername(any())).willReturn(new Owner());
		
		mockMvc.perform(post("/causes/donate/1")
							.with(csrf())
							.param("donations", "200."))
				.andExpect(status().isOk())
				.andExpect(model().attribute("result", "Debe insertar un valor cuya suma a las donaciones no supere el objetivo de la causa"))
				.andExpect(model().attributeExists("causes"))
				.andExpect(view().name("causes/listCause"));
		
		verify(causeService, times(0)).updateDonationsCause(any(), any(), any());
	}
	
}
