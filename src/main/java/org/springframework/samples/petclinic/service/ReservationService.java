package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

	private ReservationRepository reservationRepository;
	
	@Autowired
	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}
	
	@Transactional
	public void saveReservation(Reservation reservation) throws DataAccessException{
		this.reservationRepository.save(reservation);
	}
	
	@Transactional(readOnly = true)
	public Collection<Reservation> allReservations() throws DataAccessException{
		return (Collection<Reservation>) this.reservationRepository.findAll();
	}
	
}
