package bean;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class Coupon 
{
	private int id;
	private int companyID;
	private int amount;
	private Category category;
	private String title;
	private String description;
	private String image;
	private Date startDate;
	private Date endDate;
	private double price;
	
	public static List<String> headersList = Arrays.asList("ID", "Company ID", "Amount", "Category", "Title", "Description", "Image", "Start date", "End date", "Price");
	
	//to retrieve data from DB
	public Coupon(int id, int companyID, int amount, Category category, String title, String description, String image, Date startDate, Date endDate, double price) {
		this(companyID, amount, category, title, description, image, startDate, endDate, price);
		this.id = id;
	}
	
	//to put data to DB (id will be incremented automatically by MySQL)
	public Coupon(int companyID, int amount, Category category, String title, String description, String image, Date startDate, Date endDate, double price) {
		setCompanyID(companyID);
		setAmount(amount);
		setCategory(category);
		setTitle(title);
		setDescription(description);
		setImage(image);
		setStartDate(startDate);
		setEndDate(endDate);
		setPrice(price);
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public List<String> getHeadersList() {
		return headersList;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", companyID=" + companyID + ", amount=" + amount + ", category=" + category
				+ ", title=" + title + ", description=" + description + ", image=" + image + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", price=" + price + "]";
	}
}
