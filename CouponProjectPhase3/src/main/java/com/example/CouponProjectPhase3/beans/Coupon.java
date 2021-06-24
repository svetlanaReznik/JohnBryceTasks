package com.example.CouponProjectPhase3.beans;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;

@Entity
@Table(name="coupons")
public class Coupon 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(length = 100)
	private String description;
	
	@Column(name="start_date")
	@FutureOrPresent
	private Date startDate;
	
	@Column(name="end_date")
	@FutureOrPresent
	private Date endDate;
	
	@Column(nullable = false)
	@Min(0)
	private int amount;
	
	@Column(nullable = false)
	@Min(0)
	private double price;
	
	@Column
	private String image;
	
	@Column 
	private CategoryType category;
	
	@ManyToOne(optional = false) // cannot add null to this column!
	private Company company;     //Containment of Company object in Coupon object
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy = "coupons")
	private Set<Customer> customers;
	
	public Coupon() {}

	public Coupon(String title, String description, @FutureOrPresent Date startDate, @FutureOrPresent Date endDate,
			@Min(0) int amount, @Min(0) double price, String image, CategoryType category, Company company) {
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
		this.category = category;
		this.company = company;
	}

	public Coupon(int id, String title, String description, @FutureOrPresent Date startDate,
			@FutureOrPresent Date endDate, @Min(0) int amount, @Min(0) double price, String image,
			CategoryType category, Company company) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
		this.category = category;
		this.company = company;
	}
	
	public void addCustomer(Customer customer) {
		this.customers.add(customer);
	}
	
	public void removeCustomer(Customer customer) {
		this.customers.remove(customer);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CategoryType getCategory() {
		return category;
	}

	public void setCategory(CategoryType category) {
		this.category = category;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

	public int getId() {
		return id;
	}

//	@Override
//	public int hashCode() {
//		return super.hashCode();
//	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", description=" + description + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", amount=" + amount + ", price=" + price + ", image=" + image
				+ ", category=" + category + ", company=" + company + "]";
	}
}
