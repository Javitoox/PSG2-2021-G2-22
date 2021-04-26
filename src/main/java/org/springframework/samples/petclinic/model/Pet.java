/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

/**
 * Simple business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {

	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate birthDate;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private PetType type;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
	private Set<Visit> visits;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
	private Set<Reservation> reservations;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
	private Set<Adoption> adoptions;
	
	@NotNull
	@Column(name = "in_adoption")
	private Boolean inAdoption;

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public PetType getType() {
		return this.type;
	}

	public void setType(PetType type) {
		this.type = type;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public Boolean getInAdoption() {
		return this.inAdoption;
	}
	public void setInAdoption(Boolean inAdoption) {
		this.inAdoption = inAdoption;
	}

	protected Set<Visit> getVisitsInternal() {
		if (this.visits == null) {
			this.visits = new HashSet<>();
		}
		return this.visits;
	}

	protected void setVisitsInternal(Set<Visit> visits) {
		this.visits = visits;
	}

	public List<Visit> getVisits() {
		List<Visit> sortedVisits = new ArrayList<>(getVisitsInternal());
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedVisits);
	}
	

	public void addVisit(Visit visit) {
		getVisitsInternal().add(visit);
		visit.setPet(this);
	}
	
	public void removeVisit(Visit visit) {
		List<Visit> visits = this.getVisits();
		for (Visit v : visits) {
			if (v.getDescription() == null) {
				this.visits.remove(v);
			}
		}
		this.visits.remove(visit);
	}
	
	protected Set<Reservation> getReservationsInternal() {
		if (this.reservations == null) {
			this.reservations = new HashSet<>();
		}
		return this.reservations;
	}

	protected void setReservationsInternal(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Reservation> getReservations() {
		List<Reservation> sortedReservations = new ArrayList<>(getReservationsInternal());
		PropertyComparator.sort(sortedReservations, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedReservations);
	}
	public void removeReservation(Reservation reservation) {
		List<Reservation> reservations = this.getReservations();
		for (Reservation r : reservations) {
			this.reservations.remove(r);
		}
		this.reservations.remove(reservation);
	}
	
	protected Set<Adoption> getAdoptionsInternal() {
		if (this.adoptions == null) {
			this.adoptions  = new HashSet<>();
		}
		return this.adoptions;
	}
	
	protected void setAdoptionsInternal(Set<Adoption> adoptions) {
		this.adoptions = adoptions;
	}
	public List<Adoption> getAdoptions() {
		List<Adoption> sortedAdoptions = new ArrayList<>(getAdoptionsInternal());
		PropertyComparator.sort(sortedAdoptions, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedAdoptions);
	}
	

	public void addAdoption(Adoption adoption) {
		getAdoptionsInternal().add(adoption);
		adoption.setPet(this);
	}
	public void removeAdoption(Adoption adoption) {
		List<Adoption> adoptions = this.getAdoptions();
		for (Adoption a : adoptions) {
			if (a.getDescription() == null) {
				this.adoptions.remove(a);
			}
		}
		this.adoptions.remove(adoption);
	}

	
}
