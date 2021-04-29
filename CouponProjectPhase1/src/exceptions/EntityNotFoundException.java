package exceptions;

public class EntityNotFoundException extends Exception {

	public EntityNotFoundException(String entityName) {
		super("EntityNotFoundException: Unable to find "+ entityName + "\n");
	}
}

