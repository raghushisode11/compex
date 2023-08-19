package autoFW.exceptions;

import org.testng.Reporter;

@SuppressWarnings("serial")
public class ElementNotLocatedOnUIException extends Exception {
	String message;

	public ElementNotLocatedOnUIException() {
		message = "Element not found on UI!";
		Reporter.log(getMessage());
	}

	public ElementNotLocatedOnUIException(String xpath) {
		message = "Element with xpath: \"" + xpath + "\" not found on UI!";
		Reporter.log(getMessage());
	}

	public String getMessage() {
		return message;
	}
}
