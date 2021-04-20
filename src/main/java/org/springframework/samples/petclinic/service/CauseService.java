package org.springframework.samples.petclinic.service;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
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
		return (Collection<Cause>) causeRepository.findAll();
	}

	@Transactional
	public void save(@Valid Cause Causa) {
		causeRepository.save(Causa);
	}

}