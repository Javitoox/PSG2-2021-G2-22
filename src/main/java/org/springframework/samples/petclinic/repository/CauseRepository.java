package org.springframework.samples.petclinic.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Cause;

public interface CauseRepository extends CrudRepository<Cause, Integer>{
	
	List<Cause> findAll();
	
}
