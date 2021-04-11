package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "causa")

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
	private Integer goal;
	
	@Column(name="organization")
	@NotNull(message = "Campo requerido")
	private String organization;
	
	
	
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
	public Integer getGoal() {
		return goal;
	}
	public void setGoal(Integer goal) {
		this.goal = goal;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	
}
