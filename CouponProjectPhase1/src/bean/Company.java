package bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Company 
{	
	private int id;
	private String name;
	private String email;
	private String password;
	private List<Coupon> coupons = new ArrayList<>();
	
	//to retrieve data from DB without coupons
	public Company(int id, String name, String email, String password) {
		this(name, email, password);
		this.id = id;
	}
	
	//to put data to DB (id will be incremented automatically by MySQL)
	public Company(String name, String email, String password) {
		setName(name);
		setEmail(email);
		setPassword(password);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", coupons="
				+ coupons + "]";
	}
}
