package com.sr.SvetlanaReznikTask.clr;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sr.SvetlanaReznikTask.beans.Employee;
import com.sr.SvetlanaReznikTask.beans.Job;
import com.sr.SvetlanaReznikTask.repos.EmployeeRepository;
import com.sr.SvetlanaReznikTask.repos.JobRepository;
import com.sr.SvetlanaReznikTask.util.ArtUtils;
import com.sr.SvetlanaReznikTask.util.DateUtils;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class CompanyCRUDTesting implements CommandLineRunner {

	private final JobRepository jobRepo;
	private final EmployeeRepository employeeRepo;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println(ArtUtils.COMPANY_TESTING);
		
		Employee e1 = Employee.builder()
					  .name("AAA AAA")
					  .salary(15.000)
					  .build();
		
		Employee e2 = Employee.builder()
				      .name("BBB BBB")
				      .salary(25.000)
				      .build();
		
		Job j1 = Job.builder()
				.description("to sort")
				.endDate(DateUtils.setDate(2))
				.employee(e1)
				.build();
		
		Job j2 = Job.builder()
				.description("to filter")
				.endDate(DateUtils.setDate(10))
				.employee(e1)
				.build();
		
		Job j3 = Job.builder()
				.description("to summarize")
				.endDate(DateUtils.setDate(15))
				.employee(e1)
				.build();
		
		Job j4 = Job.builder()
				.description("to check")
				.endDate(DateUtils.setDate(1))
				.employee(e2)
				.build();
		
		Job j5 = Job.builder()
				.description("to find")
				.endDate(DateUtils.setDate(2))
				.employee(e2)
				.build();
		
		employeeRepo.saveAll(Arrays.asList(e1, e2));
		
		System.out.println(employeeRepo.getOne(1l));
		
		employeeRepo.findEmployeesByName("AAA").forEach(System.out::println);
		
		employeeRepo.findAll().forEach(System.out::println);
		
		jobRepo.findAll().forEach(System.out::println);
		
		jobRepo.findJobsByEndDate(DateUtils.setDate(15)).forEach(System.out::println);
		
		jobRepo.findJobsByEndDateBetween(DateUtils.setDate(0), DateUtils.setDate(21)).forEach(System.out::println);
	}
}
