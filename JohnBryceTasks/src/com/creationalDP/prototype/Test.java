package com.creationalDP.prototype;

public class Test 
{
	public static void main(String[] args) throws CloneNotSupportedException 
	{
		Person p1 = new Person().build().address(new Address());
		Person p2 = p1.clone();
	
		System.out.println(p1);
		System.out.println(p2);
	}
}
