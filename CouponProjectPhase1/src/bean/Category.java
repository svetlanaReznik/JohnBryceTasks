package bean;

public enum Category 
{
	VACATION(1),
	RESTAURANT(2),
	ATTRACTIONS(3),
	GAMES(4),
	BOOKS(5),
	MOVIES(6);
	
	private int value;
	
	private Category(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
