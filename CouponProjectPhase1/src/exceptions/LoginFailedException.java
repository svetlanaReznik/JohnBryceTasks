package exceptions;

/**
 * An exception that provides information on a login access error <br> as a result of incorrect email or password.
 */
public class LoginFailedException extends Exception {

	public LoginFailedException() {
		super("LoginFailedException: One or more details are incorrect!\n");
	}
}
