package com.sr.SvetlanaReznikTask.service;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sr.SvetlanaReznikTask.beans.Employee;
import com.sr.SvetlanaReznikTask.beans.Job;
import com.sr.SvetlanaReznikTask.exceptions.EntityAlreadyExistsException;
import com.sr.SvetlanaReznikTask.exceptions.EntityNotFoundException;
import com.sr.SvetlanaReznikTask.repos.EmployeeRepository;
import com.sr.SvetlanaReznikTask.repos.JobRepository;

import lombok.Data;

@Service
public class Company 
{
	private JobRepository jobRepo;
	private EmployeeRepository employeeRepo;
	
	//Constructor injection in order to avoid circular dependency
	public Company(JobRepository jobRepo, EmployeeRepository employeeRepo) {
		this.jobRepo = jobRepo;
		this.employeeRepo = employeeRepo;
	}
	
	public void addEmployee(Employee employee) throws EntityAlreadyExistsException 
	{
		Set<Job> jobs = employee.getJobs();
		jobs.forEach(null);
		for (Job job : jobs) {
			if(jobRepo.existsJobsByDescription(job.getDescription())) 
				throw new EntityAlreadyExistsException("employee");
		}
		employeeRepo.save(employee);
	}
	
	public Employee getOneEmployee (long id) throws EntityNotFoundException {
		Employee employee = employeeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("employee"));
		return employee;
	}
	
	public Set<Employee> getEmployeesByName (String name) throws EntityNotFoundException
	{
		Set<Employee> employees = employeeRepo.findEmployeesByName(name);
		if(employees.isEmpty())
			throw new EntityNotFoundException("employee by name.");
		return employees;
	}
	
	public List<Employee> getAllEmployees() throws EntityNotFoundException
	{
		List<Employee> employees = employeeRepo.findAll();
		if(employees.isEmpty())
			throw new EntityNotFoundException("No employees found.");
		return employees;
	}
	
	public List<Job> getAllJobs() throws EntityNotFoundException
	{
		List<Job> jobs = jobRepo.findAll();
		if(jobs.isEmpty())
			throw new EntityNotFoundException("No jobs found.");
		return jobs;
	}
	
	public Set<Job> getJobsByEndDate (Date endDate) throws EntityNotFoundException{
		Set<Job> jobs = jobRepo.findJobsByEndDate(endDate);
		if(jobs.isEmpty())
			throw new EntityNotFoundException("jobs by end date");
		return jobs;
	}
	
	public Set<Job> getJobsBetweenTwoDates (Date startDate, Date endDate) throws EntityNotFoundException{
		Set<Job> jobs = jobRepo.findJobsByEndDate(endDate);
		if(jobs.isEmpty())
			throw new EntityNotFoundException("jobs by end date");
		
		return jobs.stream().filter(j -> j.getEndDate().after(startDate) && j.getEndDate().before(endDate)).collect(Collectors.toSet());
	}
	
	
}
