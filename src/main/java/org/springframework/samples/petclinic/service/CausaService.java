package org.springframework.samples.petclinic.service;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.repository.CausaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CausaService {

	private CausaRepository causaRepository;
	
	@Autowired
	public CausaService(CausaRepository causaRepository) {
		this.causaRepository = causaRepository;
	}


	@Transactional(readOnly=true)
	public Collection<Causa> findAll() {
		return causaRepository.findAll();
	}


	@Transactional
	public void save(@Valid Causa Causa) {
		causaRepository.save(Causa);

	}


}