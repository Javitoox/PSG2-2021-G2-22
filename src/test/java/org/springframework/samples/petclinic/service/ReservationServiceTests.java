package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
class ReservationServiceTests {
	
	@Autowired
	protected ReservationService reservationService;
	
	@Autowired
	protected PetService petService;
	
	private static Reservation reservation;
	
	@BeforeAll
	void data() {
		reservation = new Reservation();
		Pet pet = this.petService.findPetById(7);
		reservation.setPet(pet);
		reservation.setStart(LocalDate.of(LocalDate.now().getYear()+1, 8, 13));
		reservation.setEnd(LocalDate.of(LocalDate.now().getYear()+1, 8, 15));
		reservation.setSpecialCares("Special foods with proteins");
		reservation.setLevel("VIP");
		this.reservationService.saveReservation(reservation);
	}
	
	@Test
	void shouldGetAtLeastOneReservations()  {
		Collection<Reservation> reservations = this.reservationService.allReservations();
		assertThat(reservations.size()).isGreaterThan(0);
	}
	
	@Test
	void shouldGetAConcurrentReservation()  {
		Boolean result = this.reservationService.findConcurrentReservation(LocalDate.of(LocalDate.now().getYear()+1, 8, 13), 
				LocalDate.of(LocalDate.now().getYear()+1, 8, 19), reservation.getPet().getId());
		assertThat(result).isEqualTo(true);
	}
	
	@Test
	void shouldNotGetAConcurrentReservation()  {
		Boolean result = this.reservationService.findConcurrentReservation(LocalDate.of(LocalDate.now().getYear()+2, 8, 13), 
				LocalDate.of(LocalDate.now().getYear()+2, 8, 19), reservation.getPet().getId());
		assertThat(result).isEqualTo(false);
	}
	
	@Test
	void shouldGetReservationByUsername()  {
		Collection<Reservation> reservations = this.reservationService.myReservations("owner1");
		assertThat(reservations.size()).isGreaterThan(0);
	}

}
