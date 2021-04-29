package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;
import static db.DBConsts.*;

public class ConnectionPool 
{
	private static ConnectionPool instance = null; 
	private static final int MAX_CONNECTIONS = 10;
	private Stack<Connection> connections = new Stack<>(); //Taxi station

	private ConnectionPool() throws SQLException 
	{
		//System.out.println("CTOR IN ACTION : " + this.getClass().getSimpleName());
		openAllConnections();
	}
	
	private void openAllConnections() throws SQLException 
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //JDBC - STEP 1
		} catch (ClassNotFoundException e) {System.out.println(e.getMessage() + " was not found!");}
		
		for (int i = 0; i < MAX_CONNECTIONS; i++) 
		{
			//System.out.println("Creating connection #" + i);
			Connection connection = DriverManager.getConnection(url, username, password); //JDBC - STEP 2
			connections.push(connection);
		}
	}

	public static ConnectionPool getInstance() throws SQLException 
	{
		if (instance == null) {
			synchronized (ConnectionPool.class) {
				if (instance == null) {
					instance = new ConnectionPool();
				}
			}
		}
		return instance;
	}

	public Connection getConnection() throws InterruptedException 
	{
		synchronized (connections) //to get free connection one-by-one from stack
		{
			if(connections.isEmpty()) {
				connections.wait(); //to wait for returning connection back to stack
			}
		}
		return connections.pop(); //get first free connection
	}

	public void restoreConnection(Connection connection) 
	{
		synchronized (connections) //'synchronized' possible to use on thread-safe data structure collection
		{						  // 'synchronized' handles in mutual resource of edge issues
			connections.push(connection);
			connections.notify(); //to awake whom is waiting for connection in wait()
		}
	}

	public void closeAllConnections() throws InterruptedException 
	{
		synchronized (connections) 
		{
			while(connections.size()<MAX_CONNECTIONS) {
				connections.wait(); //to wait to all connections get back to stack 
			}
		}
		connections.removeAllElements(); //to release all connections
	}
}
