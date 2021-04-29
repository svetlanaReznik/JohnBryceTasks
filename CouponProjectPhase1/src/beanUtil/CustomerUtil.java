package beanUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bean.Customer;

public class CustomerUtil 
{
	public static List<String> customerHeadersList = Arrays.asList("id", "firstName", "lastName", "email", "password");
	
	public static List<String> getCustomerHeadersList() {
		return customerHeadersList;
	}
	
	public static List<String> fillCustomerRow(Customer customer) 
	{
		List<String> rows = new ArrayList<>();
		rows.addAll(Arrays.asList(String.valueOf(customer.getId()), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword()));
		return rows;
	}
}
