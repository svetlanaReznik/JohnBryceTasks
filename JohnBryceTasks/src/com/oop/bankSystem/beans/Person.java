package com.oop.bankSystem.beans;

import static com.oop.bankSystem.util.DataUtils.getRandomValue;

import java.text.DecimalFormat;

public abstract class Person 
{
	private static final double MIN_AGE = 16.0;
	private static final double MAX_AGE = 120.0;
	
	private static int idCount = 0;
	protected int id;
	protected String name;
	protected double age;
	
	public Person() {
		setId(++idCount);
		setName(getClass().getSimpleName() + this.id);
		setAge(getRandomValue(MIN_AGE, MAX_AGE));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", age=" + Double.parseDouble(new DecimalFormat("##.#").format(age));
	}		
}
