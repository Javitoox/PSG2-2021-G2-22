package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "adoptions")
public class Adoption extends BaseEntity{
	@NotEmpty
	@Column(name = "originalOwner")
	private String originalOwner;
	
	@NotEmpty
	@Column(name = "possibleOwner")
	private String possibleOwner;
	
	@NotEmpty
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	
	public String getOriginalOwner() {
		return this.originalOwner;
	}
	
	public void setOriginalOwner(String originalOwner) {
		this.originalOwner = originalOwner;
	}
	
	public String getPossibleOwner() {
		return this.description;
	}
	
	public void setPossibleOwner(String possibleOwner) {
		this.possibleOwner = possibleOwner;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Pet getPet() {
		return this.pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}
}
