package program;

import static tests.MainTests.*;

import java.sql.SQLException;

import exceptions.CouponDateExpiredException;
import exceptions.CouponNotAvailableException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityNotFoundException;
import exceptions.LoginFailedException;

public class Program 
{
	public static void main(String[] args) {
		try {
			testAll();
		} catch (SQLException | InterruptedException | LoginFailedException | EntityAlreadyExistsException | EntityNotFoundException | CouponNotAvailableException | CouponDateExpiredException e) {
			  System.out.println(e.getMessage());
		  }
	}
}
