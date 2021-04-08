package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

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
	
	@Transactional(readOnly = true)
	public Collection<Reservation> myReservations(String username) throws DataAccessException{
		return this.reservationRepository.findByOwnerUsername(username);
	}
	
	@Transactional
	public Collection<Reservation> findReservationsByPetId(int petId) throws DataAccessException{
		return reservationRepository.findByPetId(petId);
	}
	
	@Transactional(readOnly = true)
	public Optional<Reservation> findReservationById(int id) throws DataAccessException {
		return reservationRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Boolean findConcurrentReservation(LocalDate start, LocalDate end, Integer petId) throws DataAccessException {
		return reservationRepository.findConcurrent(start, end, petId) != null ? true : false;
	}

	@Transactional
	public void deleteReservation(Reservation reservation) throws DataAccessException {		
			reservationRepository.delete(reservation);
		}
	
	@Transactional
	public void deleteAllReservations(Collection<Reservation> reservations) throws DataAccessException {
		for (Reservation r : reservations) {
			reservationRepository.delete(r);
		}
	}
	
}
