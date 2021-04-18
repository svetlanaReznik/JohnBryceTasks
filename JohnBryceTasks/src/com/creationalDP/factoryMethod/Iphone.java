package com.creationalDP.factoryMethod;

public class Iphone extends Mobile
{
	private int id;
	private String version;
	private String licenseVendor;
	
	public Iphone() {
		this.id = ++mobileID;
		this.version = "12";
		this.licenseVendor = "Apple";
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public String getLicenseVendor() {
		return licenseVendor;
	}

	@Override
	public String toString() {
		return "Iphone [id=" + id + ", version=" + version + ", licenseVendor=" + licenseVendor + "]";
	}
}
