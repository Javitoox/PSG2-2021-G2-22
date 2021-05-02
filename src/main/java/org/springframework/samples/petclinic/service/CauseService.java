package org.springframework.samples.petclinic.service;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {

	private CauseRepository causeRepository;
	
	@Autowired
	public CauseService(CauseRepository causeRepository) {
		this.causeRepository = causeRepository;
	}

	@Transactional(readOnly=true)
	public Collection<Cause> findAll() throws DataAccessException{
		return this.causeRepository.findAll();
	}

	@Transactional
	public void saveCause(@Valid Cause cause)  throws DataAccessException {
		causeRepository.save(cause);
	}
	@Transactional(readOnly = true)
	public Collection<Cause> allCauses() throws DataAccessException{
		return this.causeRepository.findAll();
	}
	@Transactional(readOnly = true)
	public Optional<Cause> findCauseById(int id) throws DataAccessException {
		return causeRepository.findById(id);
	}
	
	@Transactional
	public void updateDonationsCause(Cause cause, Double total, Donation donation)  throws DataAccessException{
		cause.setDonations(total);
		cause.addDonation(donation);
		this.causeRepository.save(cause);
	}

}