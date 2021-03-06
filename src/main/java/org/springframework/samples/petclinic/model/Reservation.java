package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity{
	
	@Column(name = "start")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Future(message = "La fecha debe ser futura")
	@NotNull(message = "Campo requerido")
	private LocalDate start;
	
	@Column(name = "end")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Future(message = "La fecha debe ser futura")
	@NotNull(message = "Campo requerido")
	private LocalDate end;
	
	@Column(name = "special_cares")
	private String specialCares;
	
	@Column(name = "level")
	@NotBlank(message = "Campo requerido")
	private String level;
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public String getSpecialCares() {
		return specialCares;
	}

	public void setSpecialCares(String specialCares) {
		this.specialCares = specialCares;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

}