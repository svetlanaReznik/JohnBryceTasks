package com.sr.SvetlanaReznikTask.repos;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sr.SvetlanaReznikTask.beans.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
	Set<Employee> findEmployeesByName(String name);
}
