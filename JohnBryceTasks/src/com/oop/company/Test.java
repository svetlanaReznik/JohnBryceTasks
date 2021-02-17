package com.oop.company;

import java.util.Random;

public class Test 
{
	public static final int NUM_OF_ROLES=5;
	public static final int SIZE=10;
	public static Random rand = new Random();
	
	public static void main(String[] args) 
	{
		Employee[] employees = generateEmployees();
		avgSalary(employees);
		managementAvgSalary(employees);
	}

	public static Employee[] generateEmployees() 
	{
		Employee[] employees = new Employee[SIZE];
		for (int i = 0; i < employees.length; i++) 
		{
			employees[i] = database();
			System.out.println(employees[i]);
		}
		return employees;
	}	

	public static Employee database() 
	{
		String[] firstName = {"Lily", "Kate", "Ted", "Marshall", "Robin", "Barney", "George", "Lizi", "Derek", "Christina"};
		String[] lastName = {"Odrin", "Bi", "Mosby", "Erikson", "Sherbatsky", "Stinson", "Omelly", "Lu", "Shepard", "Yang"};
		Employee[] roles = new Employee[NUM_OF_ROLES];
		
		roles[0] = new Employee(firstName[rand.nextInt(firstName.length)]+ " " + lastName[rand.nextInt(lastName.length)], (Math.random()*35001 + 5000));
		roles[1] = new Secretary(firstName[rand.nextInt(firstName.length)]+ " " + lastName[rand.nextInt(lastName.length)], (Math.random()*35001 + 5000), Office.getRandom().toString());
		roles[2] = new Engineer(firstName[rand.nextInt(firstName.length)]+ " " + lastName[rand.nextInt(lastName.length)], (Math.random()*35001 + 5000), Specialty.getRandom().toString());
		roles[3] = new Manager(firstName[rand.nextInt(firstName.length)]+ " " + lastName[rand.nextInt(lastName.length)], (Math.random()*35001 + 5000), Department.getRandom().toString());
		roles[4] = new Director(firstName[rand.nextInt(firstName.length)]+ " " + lastName[rand.nextInt(lastName.length)], (Math.random()*35001 + 5000), Department.getRandom().toString(), Group.getRandom().toString());
		return roles[(int)(Math.random()*5)];
	}
	
	public static void avgSalary(Employee[] employees) 
	{
		Double sum = 0.0d;
		for (Employee employee : employees) {
				sum += employee.getSalary();
		}
		System.out.println(String.format("Average of salary is: %.2f",(Double)(sum/employees.length)));
	}
	
	private static void managementAvgSalary(Employee[] employees) 
	{
		Double sum = 0.0d;
		int count = 0;
		for (Employee employee : employees) 
		{
				if(employee instanceof Manager) {
					sum += employee.getSalary();
					count++;
				}
		}
		if(count==0) {
			System.out.println("There is no manager.");
			return;
		}
		System.out.println(String.format("Average of management salary is: %.2f",(Double)(sum/count)));
	}
}
