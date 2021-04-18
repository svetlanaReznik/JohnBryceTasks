package com.creationalDP.prototype;

public class Person implements Cloneable
{
	private int id;
	private String lastName;
	private String firstName;
	private Address address;

	public Person build(){
		return new Person();
	}
	
	public Person id(int id) {
		setId(id);
		return this;
	}
	
	public Person lastName(String lastName){
		setLastName(lastName);
		return this;
	}
	
	public Person firstName(String firstName){
		setFirstName(firstName);
		return this;
	}
	
	public Person address(Address address){
		setAddress(address);
		return this;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	

	@Override
	public String toString() {
		return super.toString() + "Person [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", address=" + address
				+ "]";
	}

	@Override
	protected Person clone() throws CloneNotSupportedException {
		Person newPerson = (Person)super.clone();
		newPerson.setAddress(new Address(address));
		return newPerson;
	}
}
