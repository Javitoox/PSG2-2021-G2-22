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
	@Column(name = "owner")
	private String owner;
	
//	@NotEmpty
//	@Column(name = "possibleOwner")
//	private String possibleOwner;
	
	@NotEmpty
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	

	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPossibleOwner() {
		return this.description;
	}
	
//	public void setPossibleOwner(String possibleOwner) {
//		this.possibleOwner = possibleOwner;
//	}
	
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
