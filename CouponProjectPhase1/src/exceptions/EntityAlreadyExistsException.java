package exceptions;

public class EntityAlreadyExistsException extends Exception {

	public EntityAlreadyExistsException(String entityName) {
		super("EntityAlreadyExistsException: " + entityName + " already exists! \n");
	}
}

