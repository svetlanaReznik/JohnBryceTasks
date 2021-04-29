package db;

import static db.DBUtils.runQuery;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import bean.Category;

public class DBManager 
{
	private static final String DROP_TABLE_COMPANIES = "DROP TABLE IF EXISTS COMPANIES";
	private static final String DROP_TABLE_CUSTOMERS = "DROP TABLE IF EXISTS CUSTOMERS";
	private static final String DROP_TABLE_CATEGORIES = "DROP TABLE IF EXISTS CATEGORIES";
	private static final String DROP_TABLE_COUPONS = "DROP TABLE IF EXISTS COUPONS";
	private static final String DROP_TABLE_CUSTOMERS_VS_COUPONS = "DROP TABLE IF EXISTS CUSTOMERS_VS_COUPONS";
	private static final String INSERT_CATEGORY = "INSERT INTO categories (`id`, `name`) VALUES (?, ?)";

	
	public static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `companies` (\r\n"
														+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
														+ "  `name` VARCHAR(45) NOT NULL,\r\n"
														+ "  `email` VARCHAR(45) NOT NULL,\r\n"
														+ "  `password` VARCHAR(45) NOT NULL,\r\n"
														+ "  PRIMARY KEY (`id`));";
	
	private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `customers` (\r\n"
														+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
														+ "  `first_name` VARCHAR(45) NOT NULL,\r\n"
														+ "  `last_name` VARCHAR(45) NOT NULL,\r\n"
														+ "  `email` VARCHAR(45) NOT NULL,\r\n"
														+ "  `password` VARCHAR(45) NOT NULL,\r\n"
														+ "  PRIMARY KEY (`id`));";
	
	
	private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE `categories` (\r\n"
														+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
														+ "  `name` VARCHAR(45) NOT NULL,\r\n"
														+ "  PRIMARY KEY (`id`));";
	
	private static final String CREATE_TABLE_COUPONS = "CREATE TABLE `coupons` (\r\n"
														+ "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
														+ "  `company_id` INT NOT NULL,\r\n"
														+ "  `category_id` INT NOT NULL,\r\n"
														+ "  `title` VARCHAR(45) NOT NULL,\r\n"
														+ "  `description` VARCHAR(45) NOT NULL,\r\n"
														+ "  `start_date` DATE NOT NULL,\r\n"
														+ "  `end_date` DATE NOT NULL,\r\n"
														+ "  `amount` INT NOT NULL,\r\n"
														+ "  `price` DOUBLE NOT NULL,\r\n"
														+ "  `image` VARCHAR(45) NOT NULL,\r\n"
														+ "  PRIMARY KEY (`id`),\r\n"
														+ "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\r\n"
														+ "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,\r\n"
														+ "  CONSTRAINT `company_id`\r\n"
														+ "    FOREIGN KEY (`company_id`)\r\n"
														+ "    REFERENCES `coupons_system`.`companies` (`id`)\r\n"
														+ "    ON DELETE NO ACTION\r\n"
														+ "    ON UPDATE NO ACTION,\r\n"
														+ "  CONSTRAINT `category_id`\r\n"
														+ "    FOREIGN KEY (`category_id`)\r\n"
														+ "    REFERENCES `coupons_system`.`categories` (`id`)\r\n"
														+ "    ON DELETE NO ACTION\r\n"
														+ "    ON UPDATE NO ACTION);";
	
	private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS ="CREATE TABLE `customers_vs_coupons` (\r\n"
														+ "  `customer_id` INT NOT NULL,\r\n"
														+ "  `coupon_id` INT NOT NULL,\r\n"
														+ "  PRIMARY KEY (`customer_id`, `coupon_id`),\r\n"
														+ "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\r\n"
														+ "  CONSTRAINT `customer_id`\r\n"
														+ "    FOREIGN KEY (`customer_id`)\r\n"
														+ "    REFERENCES `coupons_system`.`customers` (`id`)\r\n"
														+ "    ON DELETE NO ACTION\r\n"
														+ "    ON UPDATE NO ACTION,\r\n"
														+ "  CONSTRAINT `coupon_id`\r\n"
														+ "    FOREIGN KEY (`coupon_id`)\r\n"
														+ "    REFERENCES `coupons_system`.`coupons` (`id`)\r\n"
														+ "    ON DELETE NO ACTION\r\n"
														+ "    ON UPDATE NO ACTION);\r\n";

	public static void dropAndCreate() throws SQLException 
	{
		dropTables();
		createTables();
	}
	
	private static void createTables() throws SQLException 
	{
		runQuery(CREATE_TABLE_CATEGORIES);
		fillCategoriesTable();
		runQuery(CREATE_TABLE_COMPANIES);
		runQuery(CREATE_TABLE_CUSTOMERS);
		runQuery(CREATE_TABLE_COUPONS);
		runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);	
	}

	private static void dropTables() throws SQLException 
	{
		runQuery(DROP_TABLE_CUSTOMERS_VS_COUPONS);
		runQuery(DROP_TABLE_COUPONS);
		runQuery(DROP_TABLE_CUSTOMERS);
		runQuery(DROP_TABLE_COMPANIES);
		runQuery(DROP_TABLE_CATEGORIES);
	}
	
	public static void fillCategoriesTable() throws SQLException {
		for (Category category : Category.values()) {
			addCategory(category);
		}
	}
	
	public static void addCategory(Category category) throws SQLException {
		Map<Integer, Object> map = new HashMap<>();
		map.put(1, category.getValue());
		map.put(2, category.name());
		DBUtils.runQuery(INSERT_CATEGORY, map);
	}
}	