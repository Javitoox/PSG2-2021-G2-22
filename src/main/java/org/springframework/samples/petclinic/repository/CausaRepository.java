package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Causa;



public interface CausaRepository extends Repository<Causa, Integer>{

	Collection<Causa> findAll() throws DataAccessException;

	Causa save(Causa entity);

	
	
}
