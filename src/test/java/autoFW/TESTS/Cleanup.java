package autoFW.TESTS;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class Cleanup {

	WebDriver driver;

	@Test
	public void CleanupTest() {
		 driver = Setup.driver;
		 driver.quit();
	}
}

