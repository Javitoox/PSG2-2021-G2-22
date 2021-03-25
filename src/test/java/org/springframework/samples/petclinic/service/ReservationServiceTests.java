package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ReservationServiceTests {
	
	@Autowired
	protected ReservationService reservationService;
	
	@Test
	void shouldGetAtLeastOneReservations()  {
		Reservation reservation = new Reservation();
		reservation.setStart(LocalDate.of(LocalDate.now().getYear()+1, 8, 13));
		reservation.setEnd(LocalDate.of(LocalDate.now().getYear()+1, 8, 15));
		reservation.setSpecialCares("Special foods with proteins");
		reservation.setLevel("VIP");
		this.reservationService.saveReservation(reservation);
		
		Collection<Reservation> reservations = this.reservationService.allReservations();
		assertThat(reservations.size()).isGreaterThan(0);
	}

}
