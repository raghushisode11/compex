package autoFW.exceptions;

import org.testng.Reporter;

@SuppressWarnings("serial")
public class RobotException extends Exception {
	public RobotException(String msg) {
		msg = "<span style='color:red'>" + msg + "</span>";
		Reporter.log(msg);
	}
}
