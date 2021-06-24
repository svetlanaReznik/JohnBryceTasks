package com.example.CouponProjectPhase3.db;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CouponProjectPhase3.beans.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> 
{
	boolean existsCompaniesByNameOrEmail(String name, String email);
	Company findCompaniesById(int id);
	Company findCompaniesByEmailAndPassword(String email, String password);
}
