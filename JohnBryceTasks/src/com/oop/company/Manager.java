package com.oop.company;

public class Manager extends Employee 
{
	private String department;
	
	public Manager(String name, Double salary, String department) {
		super(name, salary);
		setDepartment(department);
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) 
	{
		boolean isCorrect = false;
		
		for (Department d: Department.values()) {
			if(d.name().equals(department)) {
				this.department = department;
				isCorrect=true;
			}
		}
		if(!isCorrect)
			System.out.println("Incorrect department.Please try again!");
	}
	
	@Override
	public String toString() {
		return "Manager: " + super.toString() + " department=" + department;
	}

	public boolean equals(Manager mng)
	{
		if (super.equals(mng) && this.department==mng.department)
			return true;
		else
			return false;
	}
}
