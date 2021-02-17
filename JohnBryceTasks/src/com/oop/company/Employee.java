package com.oop.company;

import java.text.DecimalFormat;

public class Employee 
{
	protected String name;
	protected Double salary;
	
	public Employee(String name, Double salary) {
		setName(name);
		setSalary(salary); 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) 
	{
		if (name == null || name.isEmpty())
			System.out.println("Please enter a name.");
		else
		{
			if (isCorrectName(name)) {
				this.name = name;
			}
		}
	}

	private boolean isCorrectName(String name) 
	{
		boolean isLetter = true;
		for (char c : name.toCharArray()) {
		    if (!Character.isLetter(c)) {
		    	if (c == ' ') continue;
		    	isLetter = false;
		    	System.out.println("Please enter a valid name.");
		        break;
		    }
		}
		return isLetter;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) 
	{
		if(salary >= 1000 && salary <= 50000)
			this.salary = salary;
		else
			System.out.println("Invalid salary.");
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		return "Employee: name=" + name + ", salary=" + df.format(salary);
	}
	
	public boolean equals(Employee emp)
	{
		if (this.name==emp.name && this.salary==emp.salary)
			return true;
		else
			return false;
	}
}
