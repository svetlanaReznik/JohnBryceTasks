package dao;

import java.sql.SQLException;
import java.util.List;

import bean.Company;

public interface CRUDInterface <T>
{
	int add(T t) throws SQLException;
	void update(T t) throws SQLException;
	int delete(int id) throws SQLException;
	int deleteCouponPurchases(int id) throws SQLException;
	List<T> getAll() throws SQLException;
	T getOne(int id) throws SQLException;
}
