package com.creationalDP.factoryMethod;

public class Android extends Mobile
{
	private int id;
	private String version;
	private String licenseVendor;
		
	public Android() {
		this.id = ++mobileID;
		this.version ="11";
		this.licenseVendor = "Google";
	}

	public String getVersion() {
		return version;
	}

	public int getId() {
		return id;
	}

	public String getLicenseVendor() {
		return licenseVendor;
	}

	@Override
	public String toString() {
		return "Android [id=" + id + ", version=" + version + ", licenseVendor=" + licenseVendor + "]";
	}
}
