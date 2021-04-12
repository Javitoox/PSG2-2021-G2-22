package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Adoption;



public interface AdoptionRepository  extends Repository<Adoption, Integer>  {
	void save(Adoption adoption) throws DataAccessException;

	void delete(Adoption adoption) throws DataAccessException;

}
