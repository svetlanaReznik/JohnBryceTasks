package com.sr.SvetlanaReznikTask.beans;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Entity
@Table(name="employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique=true, length = 100, nullable = false)
	private String name;
	
	@Column
	private double salary;
	
	@OneToMany(mappedBy="employee", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@Singular
	private Set<Job> jobs;
}
