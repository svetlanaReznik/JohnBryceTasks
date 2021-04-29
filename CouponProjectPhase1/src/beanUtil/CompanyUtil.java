package beanUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bean.Company;

public class CompanyUtil 
{
	public static List<String> companyHeadersList = Arrays.asList("id", "name", "email", "password");
	
	public static List<String> getCompanyHeadersList() {
		return companyHeadersList;
	}
	
	public static List<String> fillCompanyRow(Company company) 
	{
		List<String> rows = new ArrayList<>();
		rows.addAll(Arrays.asList(String.valueOf(company.getId()), company.getName(), company.getEmail(), company.getPassword()));
		return rows;
	}
}
