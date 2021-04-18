package com.creationalDP.prototype;

public class Address 
{		
	private String streetName;
	private int buildingNumber;
	private int apartmentNumber;

	public Address() {}

	public static Address build() {
		return new Address();
	}
	
	public Address(Address address) {
		setStreetName(address.streetName);
		setBuildingNumber(address.buildingNumber);
		setApartmentNumber(address.apartmentNumber);
	}
	
	public Address streetName(String street) {
		setStreetName(street);
		return this;
	}
	
	public Address buildingNumber(int building) {
		setBuildingNumber(building);
		return this;
	}
	
	public Address apartmentNumber(int apartment) {
		setApartmentNumber(apartment);
		return this;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public int getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(int buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public int getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(int apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}
}
