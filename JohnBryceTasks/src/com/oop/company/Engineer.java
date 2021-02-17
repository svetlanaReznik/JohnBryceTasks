package com.oop.company;

public class Engineer extends Employee 
{
	private String specialty;
	
	public Engineer(String name, Double salary, String specialty) {
		super(name, salary);
		setSpecialty(specialty);
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) 
	{
		boolean isCorrect = false;
		for (Specialty s: Specialty.values()) {
			if(s.name().equals(specialty)) {
				this.specialty = specialty;
				isCorrect=true;
			}
		}
		if(!isCorrect)
			System.out.println("Incorrect specialty.Please try again!");
	}
	
	@Override
	public String toString() {
		return "Engineer: " + super.toString() + " specialty=" + specialty;
	}

	public boolean equals(Engineer eng)
	{
		if (super.equals(eng) && this.specialty==eng.specialty)
			return true;
		else
			return false;
	}
}
