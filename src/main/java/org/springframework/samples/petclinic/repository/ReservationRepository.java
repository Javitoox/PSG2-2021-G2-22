package org.springframework.samples.petclinic.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Integer>{

	List<Reservation> findByPetId(Integer petId) throws DataAccessException;
	
	@Query("select r from Reservation r where ((:start >= r.start and :start <= r.end) or (:end >= r.start and :end <= r.end)) and r.pet.id = :petId")
	Reservation findConcurrent(LocalDate start, LocalDate end, Integer petId) throws DataAccessException;
	
	@Query("select r from Reservation r join fetch r.pet p join fetch p.owner o where o.user.username = :username")
	Collection<Reservation> findByOwnerUsername(String username) throws DataAccessException;
	
}
