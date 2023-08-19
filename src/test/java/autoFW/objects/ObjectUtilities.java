package autoFW.objects;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import autoFW.TESTS.Setup;
import autoFW.exceptions.ElementNotLocatedOnUIException;
import autoFW.exceptions.RobotException;

public class ObjectUtilities {

	WebDriver driver = Setup.driver;

	public WebElement getWebElement(String xpath) {
		if (waitForObject(xpath))
			return driver.findElement(By.xpath(xpath));
		else
			return null;
	}

	public boolean waitForObject(WebElement element) {
		return waitForObject(element, Setup.defaultTimeOutForObjectWait);
	}

	public boolean waitForObject(String xpath) {
		return waitForObject(xpath, Setup.defaultTimeOutForObjectWait);
	}

	public boolean waitForObject(WebElement element, int timeoutInSec) {
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			String elemOH = "";
			try {
				elemOH = element.getAttribute("outerHTML");
			} catch (Exception eOH) {
				elemOH = "~";
			}
			Reporter__log("Waited successfully for element with outerHTML: " + elemOH);
			return true;
		} catch (Exception e) {
			Reporter__log("<div style='color:red'>Waited unsuccessfully for WebElement</div>");
			String screenShot = getScreenshotPath(driver);
			Reporter__log("<br/><a href=\\" + screenShot + " target='_blank'>Click Here For Screenshot</a><br />");
			return false;
		}
	}

	public boolean waitForObject(String xpath, int timeoutInSec) {
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			Reporter__log("Waited successfully for element with xpath: \"" + xpath + "\"");
			return true;
		} catch (Exception e) {
			Reporter__log(
					"<div style='color:red'>Waited unsuccessfully for element with xpath: \"" + xpath + "\"</div>");
			String screenShot = getScreenshotPath(driver);
			Reporter__log("<br/><a href=\\" + screenShot + " target='_blank'>Click Here For Screenshot</a><br />");
			return false;
		}
	}

	public void typeInElementByJS(WebElement element, String content) throws ElementNotLocatedOnUIException {
		Reporter__log("Attempting to type in element via JS..");
		if (waitForObject(element, Setup.defaultTimeOutForObjectWait)) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].value=arguments[1];", element, content);
			try {
				Reporter__log("Typed successfully in WebElement via JS: " + element.getAttribute("outerHTML")
						+ ", content: '" + content + "'");
			} catch (Exception e) {
				Reporter__log("Typed successfully in WebElement via JS, content: '" + content + "'");
			}
		} else
			throw (new ElementNotLocatedOnUIException());

	}

	public void clickElementByJS(WebElement element) throws ElementNotLocatedOnUIException {
		Reporter__log("Attempting to click via JS..");
		if (waitForObject(element, Setup.defaultTimeOutForObjectWait)) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
			try {
				Reporter__log("Clicked successfully on WebElement via JS: " + element.getAttribute("outerHTML"));
			} catch (Exception e) {
				Reporter__log("Clicked successfully on WebElement via JS");
			}
		} else
			throw (new ElementNotLocatedOnUIException());

	}

	public void clickElement(WebElement element) throws ElementNotLocatedOnUIException {
		clickElement(element, Setup.defaultTimeOutForObjectWait);
	}

	public void clickElement(String xpath) throws ElementNotLocatedOnUIException {
		clickElement(xpath, Setup.defaultTimeOutForObjectWait);
	}

	public void clickElement(WebElement element, int timeoutInSec) throws ElementNotLocatedOnUIException {
		Reporter__log("Attempting to click..");
		if (waitForObject(element, timeoutInSec)) {
			try {
				element.click();
			} catch (ElementClickInterceptedException ex) {
				// some other element is hogging the limelight; so wait a bit
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				element.click();
			}
			try {
				Reporter__log("Clicked successfully on WebElement: " + element.getAttribute("outerHTML"));
			} catch (Exception e) {
				Reporter__log("Clicked successfully on WebElement");
			}
		} else
			throw (new ElementNotLocatedOnUIException());
	}

	public void clickElement(String xpath, int timeoutInSec) throws ElementNotLocatedOnUIException {
		Reporter__log("Attempting to click..");
		if (waitForObject(xpath, timeoutInSec)) {
			try {
				driver.findElement(By.xpath(xpath)).click();
			} catch (ElementClickInterceptedException ex) {
				// some other element is hogging the limelight; so wait a bit
				try {
					Reporter__log("Some other element is hogging the limelight; so waiting a bit...");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				driver.findElement(By.xpath(xpath)).click();
			}
			Reporter__log("Clicked successfully on WebElement: \"" + xpath + "\"");
		} else
			throw (new ElementNotLocatedOnUIException(xpath));
	}

	public void typeInElement(WebElement element, String content) throws ElementNotLocatedOnUIException {
		typeInElement(element, content, Setup.defaultTimeOutForObjectWait);
	}

	public void typeInElement(String xpath, String content) throws ElementNotLocatedOnUIException {
		typeInElement(xpath, content, Setup.defaultTimeOutForObjectWait);
	}

	public void typeInElement(WebElement element, String content, int timeoutInSec)
			throws ElementNotLocatedOnUIException {
		Reporter__log("Attempting to type..");
		if (waitForObject(element, timeoutInSec)) {
			element.clear();
			element.sendKeys(content);
			try {
				Reporter__log("Typed successfully into WebElement: " + element.getAttribute("outerHTML"));
			} catch (Exception e) {
				Reporter__log("Typed successfully into WebElement");
			}
		} else
			throw (new ElementNotLocatedOnUIException());
	}

	public void typeInElement(String xpath, String content, int timeoutInSec) throws ElementNotLocatedOnUIException {
		Reporter__log("Attempting to type..");
		if (waitForObject(xpath, timeoutInSec)) {
			driver.findElement(By.xpath(xpath)).clear();
			driver.findElement(By.xpath(xpath)).sendKeys(content);
			Reporter__log("Typed successfully into WebElement: \"" + xpath + "\", content: \"" + content + "\"");
		} else
			throw (new ElementNotLocatedOnUIException(xpath));
	}

	public void selectItemFromDropdown(String xpath, String content, boolean byVisibleText, boolean byValue,
			int byIndex) throws ElementNotLocatedOnUIException {
		selectItemFromDropdown(xpath, content, byVisibleText, byValue, byIndex, Setup.defaultTimeOutForObjectWait);
	}

	public void selectItemFromDropdown(String xpath, String content, boolean byVisibleText, boolean byValue,
			int byIndex, int timeoutInSec) throws ElementNotLocatedOnUIException {
		Reporter__log("Attempting to select item from dropdown..");
		if (waitForObject(xpath, timeoutInSec)) {
			Select drp = new Select(driver.findElement(By.xpath(xpath)));
			if (byVisibleText)
				drp.selectByVisibleText(content);
			else if (byValue)
				drp.selectByValue(content);
			else
				drp.selectByIndex(byIndex);
			Reporter__log("Selected successfully item: " + content + " from dropdown: \"" + xpath + "\"");
		} else
			throw (new ElementNotLocatedOnUIException(xpath));
	}

	public void switchingToNewTabUsingID(String winHandleBefore) {
		Set<String> handles = driver.getWindowHandles();
		for (String winHandleAfter : handles) {
			if (!winHandleAfter.equals(winHandleBefore)) {
				driver.switchTo().window(winHandleAfter);
			}
		}

	}

	public void switchingBackToOldTabUsingID(String winHandleAfter) {
		Set<String> handles = driver.getWindowHandles();
		for (String winHandleBefore : handles) {
			if (!winHandleBefore.equals(winHandleAfter)) {
				driver.switchTo().window(winHandleBefore);
			}
		}

	}

	public void EnterDateInControl(WebDriver driver, String fromDate, String controlId)
			throws ElementNotLocatedOnUIException, InterruptedException {

		// Split the date time to get only the date part

		String fromDate_dd_MM_yyyy[] = fromDate.split("/");
		int day = Integer.parseInt(fromDate_dd_MM_yyyy[0]);
		int month = Integer.parseInt(fromDate_dd_MM_yyyy[1]);
		int year = Integer.parseInt(fromDate_dd_MM_yyyy[2]);
		String monthName = Month.of(month).name().substring(0, 1).toUpperCase()
				+ Month.of(month).name().substring(1, 3).toLowerCase();
		driver.findElement(By.xpath("//td[@id='" + controlId + "']")).click();
		driver.findElement(By.xpath("//a[text()='" + monthName + "']")).click();

		String selectNextYearArrowLOC = "//table[@id='dtDOJ_calendar_FastNavPopup']//td[@id='rcMView_NextY']//a";
		String selectPrevYearArrowLOC = "//table[@id='dtDOJ_calendar_FastNavPopup']//td[@id='rcMView_PrevY']//a";
		String getMaxYearValueLOC = "//table[@id='dtDOJ_calendar_FastNavPopup']//tr[5]//td[4]//a";
		String getMinYearValueLOC = "//table[@id='dtDOJ_calendar_FastNavPopup']//tr[1]//td[3]//a";
		for (int iTemp = 0; iTemp < 20; iTemp++) {
			boolean yearStatus = isElementPresent("//a[text()='" + year + "']");
			if (yearStatus) {
				break;
			} else {
				int minYear = Integer.parseInt(driver.findElement(By.xpath(getMinYearValueLOC)).getText());
				int maxYear = Integer.parseInt(driver.findElement(By.xpath(getMaxYearValueLOC)).getText());
				if (year < minYear) {
					waitForObject(selectPrevYearArrowLOC);
					clickElement(selectPrevYearArrowLOC);
				} else if (year > maxYear) {
					waitForObject(selectNextYearArrowLOC);
					clickElement(selectNextYearArrowLOC);
				}
			}

			Thread.sleep(500);

		}
		driver.findElement(By.xpath("//a[text()='" + year + "']")).click();
		driver.findElement(By.xpath("//input[@value='OK']")).click();
		Thread.sleep(2000);
		System.out.println("Clicking on date...");
		driver.findElement(
				By.xpath("//div[@class='RadCalendarPopup RadCalendarPopupShadows']//a[text()='" + day + "']")).click();

		System.out.println("Clicked on date...");

	}

	public boolean isElementPresent(String xpath) {
		try {
			driver.findElement(By.xpath(xpath));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isElementVisible(String xpath) {
		try {
			return driver.findElement(By.xpath(xpath)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void waitUntilElementIsVisible(String xpath) {
		waitUntilElementIsVisible(xpath, Setup.defaultTimeOutForObjectWait);
	}

	public void waitUntilElementIsVisible(String xpath, int timeoutInSec) {
		try {
			Reporter__log("waitUntilElementIsVisible: For element with xpath \"" + xpath + "\"");
			@SuppressWarnings("deprecation")
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		} catch (TimeoutException e) {
			Reporter__log("<div style='color:orange'><b>WARNING:</b><br />" + e.getMessage() + "</div><br />");
		}
	}

	public void waitUntilElementNotVisible(String xpath) {
		waitUntilElementNotVisible(xpath, Setup.defaultTimeOutForObjectWait);
	}

	public void waitUntilElementNotVisible(String xpath, int timeoutInSec) {
		try {
			if (isElementVisible(xpath)) {
				Reporter__log("waitUntilElementNotVisible: For element with xpath \"" + xpath + "\"");
				@SuppressWarnings("deprecation")
				WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
			} else {
				Reporter__log(
						"waitUntilElementNotVisible: Element with xpath \"" + xpath + "\" was already invisible.");
			}
		} catch (TimeoutException e) {
			Reporter__log("<div style='color:orange'><b>WARNING:</b><br />" + e.getMessage() + "</div><br />");
		}
	}

	public void waitForAjaxRefresh() {
		Reporter__log("Waiting for Ajax Refresh");
		try {
			WebDriverWait wait = new WebDriverWait(driver, Setup.defaultTimeOutForObjectWait);
			final JavascriptExecutor javascript = (JavascriptExecutor) (driver instanceof JavascriptExecutor ? driver
					: null);

			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					boolean outcome = Boolean
							.parseBoolean(javascript.executeScript("return jQuery.active == 0").toString());
					return outcome;
				}
			});
		} catch (TimeoutException ex) {
			throw new TimeoutException("Timed out after " + String.valueOf(Setup.defaultTimeOutForObjectWait)
					+ " seconds while waiting for Ajax to complete.");
		} catch (WebDriverException e) {
			Reporter__log(
					"JQuery libraries are not present on page " + driver.getCurrentUrl() + " - " + driver.getTitle());
		}
	}

	public void waitForAjaxRefresh(int timeout) {
		Reporter__log("Waiting for Ajax Refresh");
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			final JavascriptExecutor javascript = (JavascriptExecutor) (driver instanceof JavascriptExecutor ? driver
					: null);

			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					boolean outcome = Boolean
							.parseBoolean(javascript.executeScript("return jQuery.active == 0").toString());
					return outcome;
				}
			});
		} catch (TimeoutException ex) {
			throw new TimeoutException(
					"Timed out after " + String.valueOf(timeout) + " seconds while waiting for Ajax to complete.");
		} catch (WebDriverException e) {
			Reporter__log(
					"JQuery libraries are not present on page " + driver.getCurrentUrl() + " - " + driver.getTitle());
		}
	}

	public void waitForAlert() throws InterruptedException {
		{
			try {
				@SuppressWarnings("deprecation")
				WebDriverWait wait = new WebDriverWait(Setup.driver, 60);
				wait.until(ExpectedConditions.alertIsPresent());
				Alert alert = driver.switchTo().alert();
			} catch (NoAlertPresentException e) {
				Thread.sleep(1000);
			}
		}
	}

	public void dragAndDropElement(String sourceXpath, String destinationXpath) {
		WebElement sourceElement = driver.findElement(By.xpath(sourceXpath));
		WebElement destinationElement = driver.findElement(By.xpath(destinationXpath));
		dragAndDropElement(sourceElement, destinationElement);

	}

	public void dragAndDropElement(WebElement sourceElement, WebElement targetElement) {
		Reporter__log("Dragging the source element and dropping on destination element..");
		try {
			Actions action = new Actions(driver);
			waitForObject(sourceElement);
			waitForObject(targetElement);
			action.dragAndDrop(sourceElement, targetElement).build().perform();
			Reporter__log("Successfully dragged and droped element..");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getScreenshotPath(WebDriver driver) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\src\\test\\resources\\ErrorScreenshots\\"
				+ System.currentTimeMillis() + ".png";
		try {
			FileUtils.copyFile(src, new File(destination));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destination;
	}

	public String ConvertGivenDateToDDMMMYYYFormat(String date) {

		String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		String fromDate_dd_MM_yyyy[] = date.split("/");
		int day = Integer.parseInt(fromDate_dd_MM_yyyy[0]);
		int iTemp;
		for (iTemp = 0; iTemp < monthName.length; iTemp++) {
			if (iTemp + 1 == Integer.parseInt(fromDate_dd_MM_yyyy[1]))
				break;
		}
		String month = monthName[iTemp];
		int year = Integer.parseInt(fromDate_dd_MM_yyyy[2]);
		String convertedDate = day + " " + month + " " + year;
		return convertedDate;

	}

	public void moveMouse(int x_offset, int y_offset) {
		Reporter__log("Moving mouse a bit..");
		Actions builder = new Actions(driver);
		Actions x = builder.moveByOffset(x_offset, y_offset);
		Action y = x.build();
		y.perform();
		Reporter__log("Mouse moved successfully");
	}

	public void moveMouse(String xpath) {
		Reporter__log("Moving mouse to element with xpath \"" + xpath + "\"");
		WebElement element = getWebElement(xpath);
		Actions builder = new Actions(driver);
		Actions x = builder.moveToElement(element);
		Action y = x.build();
		y.perform();
		Reporter__log("Mouse moved successfully");
	}

	public void DoAssert(boolean actual, boolean expected, String checkpointMsg) {
		DoAssert(String.valueOf(actual), String.valueOf(expected), checkpointMsg, "", "hard");
	}

	public void DoAssert(boolean actual, boolean expected, String checkpointMsg, String errMsg) {
		DoAssert(String.valueOf(actual), String.valueOf(expected), checkpointMsg, errMsg, "hard");
	}

	public void DoAssert(boolean actual, boolean expected, String checkpointMsg, String errMsg, String assertType) {
		DoAssert(String.valueOf(actual), String.valueOf(expected), checkpointMsg, errMsg, assertType);
	}

	public void DoAssert(String actual, String expected, String checkpointMsg) {
		DoAssert(actual, expected, checkpointMsg, "", "hard");
	}

	public void DoAssert(String actual, String expected, String checkpointMsg, String errMsg) {
		DoAssert(actual, expected, checkpointMsg, errMsg, "hard");
	}

	/**
	 * 
	 * @param actual
	 * @param expected
	 * @param checkpointMsg
	 * @param errMsg        - default is "".
	 * @param assertType    - default is "hard". Can also be "soft".
	 */
	public void DoAssert(String actual, String expected, String checkpointMsg, String errMsg, String assertType) {
		try {
			Reporter__log("<b style='color:blue'>" + checkpointMsg + "</b>");
			Assert.assertEquals(actual, expected, errMsg);
			Reporter__log("<b>Expected:</b><br />" + expected + "<br /><b>Actual:</b><br />" + actual + "<br />",
					"pass");
		} catch (AssertionError e) {
			errMsg = errMsg + "<b>Expected:</b><br />" + expected + "<br /><b>Actual:</b><br />" + actual;
			if (assertType.equalsIgnoreCase("hard")) {
				Reporter__log(errMsg + "<br />", "fail");
				throw e;
			} else {
				// must be 'soft'
				Reporter__log(errMsg + "<br />", "soft-fail");
			}
		}
	}

	public void Reporter__log(String msg) {
		Reporter__log(msg, "");
	}

	public void Reporter__log(String msg, String mode) {
		if (mode.equalsIgnoreCase("pass"))
			msg = "<span style='color:green'>" + msg + "</span>";
		else if (mode.equalsIgnoreCase("fail"))
			msg = "<span style='color:red'>" + msg + "</span>";
		else if (mode.equalsIgnoreCase("soft-fail"))
			msg = "<span style='color:orange'>" + msg + "</span>";
		else if (mode.equalsIgnoreCase("note"))
			msg = "<span style='color:blue'><b>" + msg + "</b></span>";
		// else, we have normal logging

		msg = msg + "<br />";
		Reporter.log(msg);
		msg = msg.replaceAll("<br.*?>", "\n").replaceAll("&nbsp;", " ").replaceAll("<p>", "\n\n")
				.replaceAll("</.*?>", "").replaceAll("<.*?>", "");
		System.out.println(msg);
	}

	public int getColIndex(String xpath_tab_th, String colName, boolean debug) {
		Reporter__log("Fetching colIndex of '" + colName + "' from '" + xpath_tab_th + "'");
		int indx = -1;
		if (waitForObject(xpath_tab_th)) {
			List<WebElement> ths = driver.findElements(By.xpath(xpath_tab_th));
			for (int i = 0; i < ths.size(); i++) {
				if (debug)
					Reporter__log(String.valueOf(i + 1) + "-->" + ths.get(i).getText());
				if (ths.get(i).getText().equals(colName)) {
					indx = i + 1;
					break;
				}
			}
		}
		Reporter__log("Fetched colIndex: " + String.valueOf(indx));
		return indx;
	}

	public String getTableCellValue(String xpath_tab_records_tr, int row, int column) {
		Reporter__log("Fetching tableCellValue in row " + String.valueOf(row) + " & column " + String.valueOf(column)
				+ " with xpath '" + xpath_tab_records_tr + "'");
		String fetchedVal = "<span style='color:red'>NULL-VALUE</span>";

		try {
			if (waitForObject(xpath_tab_records_tr)) {
				WebElement tr = driver.findElements(By.xpath(xpath_tab_records_tr)).get(row - 1);
				WebElement td = tr.findElements(By.tagName("td")).get(column - 1);
				fetchedVal = td.getText();
			}
		} catch (Exception e) {
			Reporter__log("<br /><div style='color:red'>" + e.getMessage() + "</div><br />");
		} finally {
			Reporter__log("Fetched tableCellValue from row " + String.valueOf(row) + " & column "
					+ String.valueOf(column) + " is: " + fetchedVal);
		}
		return fetchedVal;
	}

	public void sendRobotKeys(Robot robot, String keySequence) throws RobotException {
		Reporter__log("Typing via Robot: " + keySequence);
		for (char c : keySequence.toCharArray()) {
			try {
				int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
				if (KeyEvent.CHAR_UNDEFINED == keyCode) {
					throw new RobotException("Robot keyCode not found for character '" + c + "'");
				}
				if (c == ':') {
					// on some platforms, like mine, directly pressing colon does not work
					// so using shift + semicolon
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_SEMICOLON);
					robot.delay(10);
					robot.keyRelease(KeyEvent.VK_SEMICOLON);
					robot.keyRelease(KeyEvent.VK_SHIFT);
				} else {
					if (Character.isUpperCase(c))
						robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(keyCode);
					robot.delay(10);
					robot.keyRelease(keyCode);
					if (Character.isUpperCase(c))
						robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.delay(10);
				}
			} catch (Exception e) {
				throw new RobotException(
						"Robot Exception while processing character '" + c + "'<br />" + e.getMessage());
			}
		}
	}

	/**
	 * Fetch the text of a pop-up, then either accept it (true) or dismiss it
	 * (false)
	 */
	public String handlePopup(boolean acceptDialog) {
		WebDriverWait wait = new WebDriverWait(driver, Setup.smallTimeOutForObjectWait);
		Alert alert = wait.until(new ExpectedCondition<Alert>() {
			@Override
			public Alert apply(WebDriver driver) {
				try {
					return driver.switchTo().alert();
				} catch (NoAlertPresentException nap) {
					return null;
				}
			}
		});

		String alertText = alert.getText();
		Reporter__log("Alert present with text: " + alertText + ". We are "
				+ (acceptDialog ? "accepting" : "dismissing") + " this dialog");

		if (acceptDialog) {
			alert.accept();
		} else {
			alert.dismiss();
		}

		return alertText.trim();
	}
	
	public String getCurrentTimeStamp() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

}
