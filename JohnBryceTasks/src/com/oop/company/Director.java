package com.oop.company;

public class Director extends Manager 
{
	private String group;

	public Director(String name, Double salary, String department, String group) {
		super(name, salary, department);
		setGroup(group); 
	}

	public String getGroup() {
		return group;
	}

	
	public void setGroup(String group) 
	{
		boolean isCorrect = false;
		
		for (Group g: Group.values()) {
			if(g.name().equals(group)) {
				this.group = group;
				isCorrect=true;
			}
		}
		if(!isCorrect)
			System.out.println("Incorrect department.Please try again!");
	}
	
	@Override
	public String toString() {
		return "Director: " + super.toString() + " group=" + group;
	}

	public boolean equals(Director dir)
	{
		if (super.equals(dir) && this.group==dir.group)
			return true;
		else
			return false;
	}
}
