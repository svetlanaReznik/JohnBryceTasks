package com.example.CouponProjectPhase3.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CouponProjectPhase3.beans.Customer;



public interface CustomerRepository extends JpaRepository<Customer, Integer> 
{
	boolean existsCustomersByEmail(String email);
	Customer findCustomersById(int id);
	Customer findCustomersByEmailAndPassword(String email, String password);
}
