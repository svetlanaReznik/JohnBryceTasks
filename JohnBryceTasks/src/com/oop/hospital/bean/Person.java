package com.oop.hospital.bean;

import static com.oop.hospital.utils.DataUtils.*;

public abstract class Person 
{
	private static int ID = 0;
	protected int id;
	protected String name;
	protected int age;
	
	public Person(String name) {
		setID();
		setAge();
		setName(name);
	}

	public int getID() {
		return id;
	}

	public void setID() {
		id = ++ID;
	}
	
	public void setID(int value) {
		id = ++ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name+id;
	}

	public int getAge() {
		return age;
	}

	public void setAge() {
		this.age = getRandomValue(18, 120);
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", age=" + age;
	}
}
