package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "causas")

public class Causa extends BaseEntity{

	@Column(name = "name")
	@NotNull(message = "Campo requerido")
	private String name;
	
	@Column(name="description")
	@NotNull(message = "Campo requerido")
	private String description;
	
	@Column(name="goal")
	@NotNull(message = "Campo requerido")
	@Positive(message = "El objetivo debe ser mayor de 0")
	private Double goal;
	
	@Column(name="organization")
	@NotNull(message = "Campo requerido")
	private String organization;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	
	
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getGoal() {
		return goal;
	}
	public void setGoal(Double goal) {
		this.goal = goal;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	
}
