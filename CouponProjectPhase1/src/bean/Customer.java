package bean;

import java.util.Arrays;
import java.util.List;

public class Customer {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private List<Coupon> coupons;
	
	public static List<String> customerHeadersList = Arrays.asList("ID", "First name", "Last name", "Email", "Password", "Coupons");

	
	public Customer(int id, String firstName, String lastName, String email, String password){
		this(firstName, lastName, email,password);
		this.id = id;
	}
	
	public Customer(String firstName, String lastName, String email, String password){
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassword(password);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public List<String> getCustomerHeadersList() {
		return customerHeadersList;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", coupons=" + coupons + "]";
	}
}

