package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity{
	
	@Column(name="start")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Future(message = "The date must be a future date")
	@NotNull(message = "Required field")
	private LocalDate start;
	
	@Column(name="end")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Future(message = "The date must be a future date")
	@NotNull(message = "Required field")
	private LocalDate end;
	
	private String numberRoom;
	
}