package com.project.ProjectPhase2.db;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ProjectPhase2.beans.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> 
{
	boolean existsCompaniesByNameOrEmail(String name, String email);
	Company findCompaniesById(int id);
	Company findCompaniesByEmailAndPassword(String email, String password);
}
