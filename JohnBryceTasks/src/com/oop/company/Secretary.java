package com.oop.company;

public class Secretary extends Employee {
	private String office;

	public Secretary(String name, Double salary, String office) {
		super(name, salary);
		setOffice(office);
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		boolean isCorrect = false;
		for (Office o : Office.values()) {
			if (o.name().equals(office)) {
				this.office = office;
				isCorrect=true;
			}
		}

		if (!isCorrect)
			System.out.println("Incorrect office.Please try again!");
	}

	@Override
	public String toString() {
		return "Secretary: " + super.toString() + " office=" + office;
	}

	public boolean equals(Secretary sec) {
		if (super.equals(sec) && this.office == sec.office)
			return true;
		else
			return false;
	}
}
