package autoFW.autfns;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import autoFW.TESTS.Setup;
import autoFW.data.GetTestDataFromPropFile;
import autoFW.exceptions.ElementNotLocatedOnUIException;
import autoFW.exceptions.RobotException;
import autoFW.objects.ObjectUtilities;
import autoFW.xpaths.GetXPathFromPropFile;

public class AUTFunctions {

	public static WebDriver driver;
	ObjectUtilities objUtil;
	GetXPathFromPropFile propUtils;
	GetTestDataFromPropFile dataUtils;

	public void waitForSmallLoader() throws FileNotFoundException, IOException {
		objUtil = new ObjectUtilities();

		String xpath_elem_ajaxLoaderSmall = propUtils.getXpathFromPropFile("Generic", "elem_ajaxLoaderSmall");
		objUtil.Reporter__log("Waiting for smallLoader to appear");
		objUtil.waitUntilElementIsVisible(xpath_elem_ajaxLoaderSmall, Setup.smallTimeOutForObjectWait);
		objUtil.Reporter__log("Waiting for smallLoader to disappear");
		objUtil.waitUntilElementNotVisible(xpath_elem_ajaxLoaderSmall, Setup.defaultTimeOutForObjectWait);
	}

	public void waitForBigLoader() throws FileNotFoundException, IOException {
		objUtil = new ObjectUtilities();

		String xpath_elem_ajaxLoaderBig = propUtils.getXpathFromPropFile("Generic", "elem_ajaxLoaderBig");
		objUtil.Reporter__log("Waiting for bigLoader to appear");
		objUtil.waitUntilElementIsVisible(xpath_elem_ajaxLoaderBig, Setup.smallTimeOutForObjectWait);
		objUtil.Reporter__log("Waiting for bigLoader to disappear");
		objUtil.waitUntilElementNotVisible(xpath_elem_ajaxLoaderBig, Setup.defaultTimeOutForObjectWait);
	}

	public void waitForALoader(String xpath_loader) throws FileNotFoundException, IOException {
		objUtil = new ObjectUtilities();

		objUtil.Reporter__log("Waiting for appearance of Loader with xpath: " + xpath_loader);
		objUtil.waitUntilElementIsVisible(xpath_loader, Setup.smallTimeOutForObjectWait);
		objUtil.Reporter__log("Waiting for disappearance of Loader with xpath: " + xpath_loader);
		objUtil.waitUntilElementNotVisible(xpath_loader, Setup.defaultTimeOutForObjectWait);
	}

	public void loginIntoApplication(WebDriver driver, String UserName, String Password)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		// to pass XPath of login button on landinng page and then cick on it
		propUtils = new GetXPathFromPropFile();
		objUtil = new ObjectUtilities();
		dataUtils = new GetTestDataFromPropFile();

		String LandingPage_loginImagebutton = propUtils.getXpathFromPropFile("LandingPage",
				"LandingPage_loginImagebutton");
		String FirstwinHandle = driver.getWindowHandle();
		objUtil.clickElement(LandingPage_loginImagebutton);

		// to switch control to the newly opened login window
		objUtil.switchingToNewTabUsingID(FirstwinHandle);

		// to pass XPath of username on login page and enter text in it
		String LoginPage_userName = propUtils.getXpathFromPropFile("LoginPage", "LoginPage_userName");
		objUtil.typeInElement(LoginPage_userName, UserName);

		// to pass XPath of password on login page and enter text in it
		String LoginPage_password = propUtils.getXpathFromPropFile("LoginPage", "LoginPage_password");
		objUtil.typeInElement(LoginPage_password, Password);

		// to pass XPath of login button on login page and click on it
		String LoginPage_loginButton = propUtils.getXpathFromPropFile("LoginPage", "LoginPage_loginButton");
		objUtil.clickElement(LoginPage_loginButton);

		// to pass XPath of the login user label
		String HomePage_userNameText = propUtils.getXpathFromPropFile("HomePage", "HomePage_userNameText");
		String ActualUserName = driver.findElement(By.xpath(HomePage_userNameText)).getText();

		Assert.assertTrue((ActualUserName.contentEquals(UserName)), "Logged in Username has not matched");
		Reporter.log("Logged in Username has matched");
	}

	public void loginWithGivenUserName(String url, String userName, String password)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		propUtils = new GetXPathFromPropFile();
		objUtil = new ObjectUtilities();
		dataUtils = new GetTestDataFromPropFile();
		objUtil.Reporter__log("Logging in at url: " + url + "<br/ >User: " + userName + "<br/ >Pwd: " + password);

		driver = Setup.driver;
		driver.manage().window().maximize();
		driver.get(url);

		// click on login button
		String xpath_btn_login = propUtils.getXpathFromPropFile("LoginPage", "btn_login");
		objUtil.clickElement(xpath_btn_login);

		// enter username and password
		String xpath_input_username = propUtils.getXpathFromPropFile("LoginPage", "input_username");
		String xpath_input_pwd = propUtils.getXpathFromPropFile("LoginPage", "input_pwd");
		objUtil.typeInElement(xpath_input_username, userName);
		objUtil.typeInElement(xpath_input_pwd, password);

		// click continue button
		String xpath_btn_continue = propUtils.getXpathFromPropFile("LoginPage", "btn_continue");
		objUtil.clickElement(xpath_btn_continue);
		waitForSmallLoader();
		waitForBigLoader();

		// ensure logout link is present on screen
		String xpath_a_logout = propUtils.getXpathFromPropFile("HomePage", "a_logout");
		objUtil.waitForObject(xpath_a_logout);
		objUtil.Reporter__log("Logged in successfully.");
	}

	public void loginAsCustodian(String url, String userName, String password)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		propUtils = new GetXPathFromPropFile();
		objUtil = new ObjectUtilities();
		dataUtils = new GetTestDataFromPropFile();
		objUtil.Reporter__log(
				"Logging in at url: " + url + "<br/ >Custodian User: " + userName + "<br/ >Pwd: " + password);

		driver = Setup.driver;
		driver.manage().window().maximize();
		driver.get(url);

		// click on login
		String xpath_btn_login = propUtils.getXpathFromPropFile("LoginPage", "btn_login");
		objUtil.clickElement(xpath_btn_login);

		// enter username and password
		String xpath_input_username = propUtils.getXpathFromPropFile("LoginPage", "input_username");
		String xpath_input_pwd = propUtils.getXpathFromPropFile("LoginPage", "input_pwd");
		objUtil.typeInElement(xpath_input_username, userName);
		objUtil.typeInElement(xpath_input_pwd, password);

		// click continue button
		String xpath_btn_continue = propUtils.getXpathFromPropFile("LoginPage", "btn_continue");
		objUtil.clickElement(xpath_btn_continue);
		waitForSmallLoader();
		waitForBigLoader();

		// ensure logout link is present on screen
		String xpath_a_logout = propUtils.getXpathFromPropFile("HomePage", "a_logout");
		objUtil.waitForObject(xpath_a_logout);
		objUtil.Reporter__log("Logged in successfully.");
	}

	public void logout() throws ElementNotLocatedOnUIException, FileNotFoundException, IOException {
		String xpath_a_logout = propUtils.getXpathFromPropFile("HomePage", "a_logout");
		waitForBigLoader();
		objUtil.clickElement(xpath_a_logout);
		String xpath_btn_login = propUtils.getXpathFromPropFile("LoginPage", "btn_login");
		boolean loggedOutSuccessfully = objUtil.waitForObject(xpath_btn_login, Setup.bigTimeOutForObjectWait);
		if (loggedOutSuccessfully)
			objUtil.Reporter__log("Logged out successfully.");
		else {
			// try clicking logout one more time
			objUtil.Reporter__log("clicking LOGOUT once again as Login screen is not visible yet!", "fail");
			objUtil.clickElement(xpath_a_logout);
			loggedOutSuccessfully = objUtil.waitForObject(xpath_btn_login, Setup.bigTimeOutForObjectWait);
			if (loggedOutSuccessfully)
				objUtil.Reporter__log("Logged out successfully.");
		} 
	}

	@SuppressWarnings("deprecation")
	public String getCurrentDateinMMMDDYYYFormat() {
		// toget the today's date
		Date today = new Date();
		int day = today.getDate();
		int month = today.getMonth() + 1; // January is 0!
		String monthName = Month.of(month).name().substring(0, 1).toUpperCase()
				+ Month.of(month).name().substring(1, 3).toLowerCase();
		int year = Year.now().getValue();
		String TodayDate = day + " " + monthName + " " + year;
		return TodayDate;

	}

	public static Date subtractDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);

		return cal.getTime();
	}

	@SuppressWarnings("deprecation")
	public String getCurrentDateinDDMMYYYFormat() {
		// to get the today's date
		Date today = new Date();
		int day = today.getDate();
		int month = today.getMonth() + 1; // January is 0!
		int year = Year.now().getValue();
		String TodayDate = day + "/" + month + "/" + year;
		return TodayDate;
	}

	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public String ChangeGiventimeByAddingAMPM(WebDriver driver, String date) {
		String outTime1Split[] = date.split(" ");
		String outTime1Time = outTime1Split[1];
		String result = LocalTime.parse(outTime1Time, DateTimeFormatter.ofPattern("HH:mm"))
				.format(DateTimeFormatter.ofPattern("hh:mm a"));
		String resultSplit[] = result.split(":");
		if (Integer.parseInt(resultSplit[0]) < 12) {
			resultSplit[0] = resultSplit[0].replace("0", "");
			result = resultSplit[0] + ":" + resultSplit[1];
		}
		return result;
	}

	public String getCurrntDateInDDMMYY() {
		String[] month = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		String currntDate = getCurrentDateinMMMDDYYYFormat();

		String[] date = currntDate.split(" ");
		int iTemp;
		for (iTemp = 0; iTemp < month.length; iTemp++)

		{
			if (month[iTemp].equalsIgnoreCase(date[1]))
				break;

		}
		iTemp = iTemp + 1;

		String dateFormat = date[0] + "/" + iTemp + "/" + date[2];
		return dateFormat;
	}

	public String selectDateOtherThanCurrent(String date) {
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		String convertedDate;
		int year, month, day;

		if (date.equalsIgnoreCase("FIRST_DATE_OF_CURRENT_YEAR")) {
			int currenYear = Year.now().getValue();
			String firstDateOfYear = "01/01/" + currenYear + "";
			System.out.println("First Date of Current Year----" + firstDateOfYear);
			return firstDateOfYear;
		} else if (date.equalsIgnoreCase("FIRST_DATE_OF_CURRENT_MONTH")) {
			int currenYear = Year.now().getValue();
			int month1 = today.getMonth() + 1;
			String firstDateOfMonth = "01/" + month1 + "/" + currenYear + "";
			System.out.println("First Date of Current Year----" + firstDateOfMonth);
			return firstDateOfMonth;
		}

		String substrigDate = date.substring(12);

		if (!substrigDate.isEmpty()) {
			String operator = substrigDate.substring(0, 1);
			int days = Integer.parseInt(substrigDate.substring(1));

			if (operator.equals("+")) {
				Date dateAfterAddingDays = addDays(today, days);
				cal.setTime(dateAfterAddingDays);
				year = cal.get(Calendar.YEAR);
				month = cal.get(Calendar.MONTH) + 1;
				day = cal.get(Calendar.DAY_OF_MONTH);
				convertedDate = (day + "/" + month + "/" + year).toString();
				return convertedDate;
				// return addDays(today,days);
			} else if (operator.equals("-")) {
				Date dateAfterAddingDays = subtractDays(today, days);
				cal.setTime(dateAfterAddingDays);
				year = cal.get(Calendar.YEAR);
				month = cal.get(Calendar.MONTH) + 1;
				day = cal.get(Calendar.DAY_OF_MONTH);
				convertedDate = (day + "/" + month + "/" + year).toString();
				return convertedDate;
				// return subtractDays(today,days);
			}
		}

		cal.setTime(today);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DAY_OF_MONTH);
		convertedDate = (day + "/" + month + "/" + year).toString();
		return convertedDate;

	}

	public int getLastDayofCurrentMonth() {
		// toget the last day of the month from the current date
		Calendar c = Calendar.getInstance();

		int lastday = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		return lastday;

	}

	public String getPreviousWorkingDay(Date date) {
		int year, month, day;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int dayOfWeek;
		do {
			cal.add(Calendar.DAY_OF_MONTH, -1);
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		} while (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);

		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DAY_OF_MONTH);
		String convertedDate = (day + "/" + month + "/" + year).toString();
		return convertedDate;

	}

	public String generateRandomDateinDDMMMYYYY(String startRange, String endRange) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY");
		Calendar cal = Calendar.getInstance();

		cal.setTime(dateFormat.parse(startRange));
		Long value1 = cal.getTimeInMillis();

		cal.setTime(dateFormat.parse(endRange));
		Long value2 = cal.getTimeInMillis();
		long value3 = (long) (value1 + Math.random() * (value2 - value1));
		cal.setTimeInMillis(value3);

		return dateFormat.format(cal.getTime());
	}

	public String generate2DigitAlphaNumRandom() throws ParseException {
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 2; i++) {
			sb.append(chars[rnd.nextInt(chars.length)]);
		}

		return sb.toString();
	}

	public String generateRandomNumber(int length) throws ParseException {
		char[] chars = "1234567890".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(chars[rnd.nextInt(chars.length)]);
		}

		return sb.toString();
	}

	public void hoverMenuAndClickSubMenu(String menuXpath, String subMenuXPath) {

		Actions action = new Actions(driver);
		WebElement menu = driver.findElement(By.xpath(menuXpath));
		WebElement subMenu = driver.findElement(By.xpath(subMenuXPath));
		action.moveToElement(menu).perform();
		WebDriverWait wait = new WebDriverWait(driver, 20); // here, wait time is 20 seconds

		wait.until(ExpectedConditions.visibilityOf(subMenu)); // this will wait for elememt to be visible for 20 seconds
		subMenu.click();
	}

	public String getCurrntDateInYYMMDD() {
		String currntDate = getCurrntDateInDDMMYY();
		String[] date = currntDate.split("/");
		String dateFormat = date[2].substring(2) + "0" + date[1] + date[0];
		return dateFormat;
	}

	public void DemoSearchOrder(String tcFileName)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		String orderNum = dataUtils.getTestDataFromPropFile(tcFileName, "orderNum");
		objUtil.Reporter__log("Searching for order: " + orderNum);

		waitForBigLoader();
		String xpath_drp_orderNum = propUtils.getXpathFromPropFile("HomePage", "drp_orderNum");
		objUtil.clickElement(xpath_drp_orderNum);

		// move the mouse a bit to get rid of the tool tip
		objUtil.moveMouse(1, 1);

	//	String xpath_li_order = propUtils.getXpathFromPropFile("HomePage", "li_order");
		//objUtil.clickElement(xpath_li_order);
		String xpath_input_searchbar = propUtils.getXpathFromPropFile("HomePage", "input_searchbar");
		objUtil.typeInElement(xpath_input_searchbar, orderNum);
		String xpath_btn_search = propUtils.getXpathFromPropFile("HomePage", "btn_search");
		objUtil.clickElement(xpath_btn_search);

		waitForBigLoader();
		// verify order number is present in searchText on the popup
		String xpath_elem_searchTextOrderNum = propUtils.getXpathFromPropFile("HomePage", "elem_searchTextOrderNum");
		objUtil.waitForObject(xpath_elem_searchTextOrderNum);
		String search_popup_title_text = objUtil.getWebElement(xpath_elem_searchTextOrderNum).getText();
		objUtil.Reporter__log("searched popup title text: " + search_popup_title_text);
		Assert.assertTrue(search_popup_title_text.contains(" " + orderNum + " ]"),
				"Assertion failed: search popup does not contain '" + orderNum + "'");
		objUtil.Reporter__log("Assertion passed: search popup contains '" + orderNum + "'", "PASS");

		// click button to display orders
		String xpath_btn_displayOrders = propUtils.getXpathFromPropFile("HomePage", "btn_displayOrders");
		objUtil.clickElement(xpath_btn_displayOrders);

		// verify order number is present in 'reset' title
		String xpath_elem_resetTitlebar = propUtils.getXpathFromPropFile("HomePage", "elem_resetTitlebar");
		objUtil.waitForObject(xpath_elem_resetTitlebar);
		String search_reset_title_text = objUtil.getWebElement(xpath_elem_resetTitlebar).getText();
		objUtil.Reporter__log("searched reset title text: " + search_reset_title_text);
		Assert.assertTrue(search_reset_title_text.contains("\"" + orderNum + "\""),
				"Assertion failed: search reset title does not contain \"" + orderNum + "\"");
		objUtil.Reporter__log("Assertion passed: search reset title contains \"" + orderNum + "\"", "PASS");

		// click on correct order number in the table
		waitForBigLoader();
		String[] orderNumber = new String[] { orderNum };
		String xpath_dyn_elem_order = propUtils.getDynamicXpathFromPropFile("HomePage", "dyn_elem_order", orderNumber);
		objUtil.clickElement(xpath_dyn_elem_order);

		String xpath_tab_th = propUtils.getXpathFromPropFile("HomePage", "tab_orderRecord");
		int col_index_recordLoc = objUtil.getColIndex(xpath_tab_th, "RECORD LOCATION", false);

		String xpath_tab_records_tr = propUtils.getXpathFromPropFile("HomePage", "tab_records_tr");
		String redord1_loc = objUtil.getTableCellValue(xpath_tab_records_tr, 1, col_index_recordLoc);
		String redord3_loc = objUtil.getTableCellValue(xpath_tab_records_tr, 3, col_index_recordLoc);
		String redord4_loc = objUtil.getTableCellValue(xpath_tab_records_tr, 4, col_index_recordLoc);

	}

	public void SearchOrder(String tcFileName) throws Exception {
		String searchTerm = dataUtils.getTestDataFromPropFile(tcFileName, "orderNum");
		objUtil.Reporter__log("Searching for order: " + searchTerm);
		// specify prop file containing field values to be verified
		String searchOrderParams_ = tcFileName + "_001";

		performSearch(searchTerm, "Order#");

		// click on order --> suborder
		String suborder_ = dataUtils.getTestDataFromPropFile(tcFileName, "subOrderNum");
		String[] order_suborder = new String[] { searchTerm, suborder_ };
		String xpath_dyn_a_order_suborder = propUtils.getDynamicXpathFromPropFile("HomePage", "dyn_a_order_suborder",
				order_suborder);
		objUtil.clickElement(xpath_dyn_a_order_suborder);

		waitForBigLoader();
		String xpath_a_view_complete_order_info = propUtils.getXpathFromPropFile("HomePage",
				"a_view_complete_order_info");
		objUtil.waitForObject(xpath_a_view_complete_order_info, Setup.bigTimeOutForObjectWait);

		// verify order location
		String xpath_elem_loc_info = propUtils.getXpathFromPropFile("HomePage", "elem_loc_info");
		String location = objUtil.getWebElement(xpath_elem_loc_info).getText();
		objUtil.DoAssert(location, dataUtils.getTestDataFromPropFile(searchOrderParams_, "location"),
				"Verify order location");
		// click for second pop-up
		objUtil.clickElement(xpath_a_view_complete_order_info);
		waitForBigLoader();
		// verify order #
		String xpath_dyn_elem_order_info_num = propUtils.getDynamicXpathFromPropFile("HomePage",
				"dyn_elem_order_info_num", searchTerm);
		objUtil.DoAssert(objUtil.waitForObject(xpath_dyn_elem_order_info_num), true,
				"Verify order # '" + searchTerm + "' is present in order info pop-up");
		verifyOrderFieldsInSecondPopup(searchOrderParams_);
		// close first pop-up
		String xpath_a_order_details_close = propUtils.getXpathFromPropFile("HomePage", "a_order_details_close");
		objUtil.clickElement(xpath_a_order_details_close);

		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
	}

	private void verifyOrderFieldsInSecondPopup(String searchOrderParams_propFile) throws Exception {
		// verify other fields (in second pop-up)
		String[] orderFieldLabelsToBeVerified = new String[] { "Order By", "ATTORNEY", "Contact", "ADJUSTER", "CLAIM #",
				"File #", "PLAINTIFF / APPLICANT", "DEFENDANT / RESPONDENT", "CASE #", "Last Name" };
		String[] orderFieldValueKeysInPropFile = new String[] { "orderBy", "attorney", "contact", "adjuster",
				"claimNum", "fileNum", "plaintiff", "defendant", "caseNum", "recordInfo_lastName" };
		verifyOrderFieldsInSecondPopup(searchOrderParams_propFile, orderFieldLabelsToBeVerified,
				orderFieldValueKeysInPropFile);
	}

	private void verifyOrderFieldsInSecondPopup(String searchOrderParams_propFile,
			String[] orderFieldLabelsToBeVerified, String[] orderFieldValueKeysInPropFile) throws Exception {
		boolean failed = false;
		Exception excep = null;
		try {
			// verify other fields (in second pop-up)
			for (int ix = 0; ix < orderFieldLabelsToBeVerified.length; ix++) {
				String xpath_dyn_p_order_info_field = propUtils.getDynamicXpathFromPropFile("HomePage",
						"dyn_p_order_info_field", orderFieldLabelsToBeVerified[ix]);
				objUtil.DoAssert(driver.findElement(By.xpath(xpath_dyn_p_order_info_field)).getText(),
						dataUtils.getTestDataFromPropFile(searchOrderParams_propFile,
								orderFieldValueKeysInPropFile[ix]),
						"Verify field \"" + orderFieldLabelsToBeVerified[ix] + "\"");
			}
		} catch (Exception e) {
			objUtil.Reporter__log(
					"<br />Something went wrong in 'verifyOrderFieldsInSecondPopup()':<br />" + e.getMessage(), "fail");
			failed = true;
			excep = e; 
		} finally {
			// close second pop-up
			String xpath_a_order_info_close = propUtils.getXpathFromPropFile("HomePage", "a_order_info_close");
			if (objUtil.isElementPresent(xpath_a_order_info_close))
				objUtil.clickElement(xpath_a_order_info_close);
			if (failed) {
				// close first pop-up
				String xpath_a_order_details_close = propUtils.getXpathFromPropFile("HomePage",
						"a_order_details_close");
				if (objUtil.isElementPresent(xpath_a_order_details_close))
					objUtil.clickElement(xpath_a_order_details_close);

				// reset for a clean slate
				String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
				objUtil.clickElement(xpath_btn_resetSearch);
				waitForBigLoader();

				throw excep;
			}
		}
	}

	public void SearchByOrderNum(String tcFileName)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		String searchTerm = dataUtils.getTestDataFromPropFile(tcFileName, "orderNum");
		objUtil.Reporter__log("Searching for order: " + searchTerm);

		performSearch(searchTerm, "Order#");

		// click on order --> suborder
		String suborder_ = dataUtils.getTestDataFromPropFile(tcFileName, "subOrderNum");
		String[] order_suborder = new String[] { searchTerm, suborder_ };
		String xpath_dyn_a_order_suborder = propUtils.getDynamicXpathFromPropFile("HomePage", "dyn_a_order_suborder",
				order_suborder);
		objUtil.clickElement(xpath_dyn_a_order_suborder);
		waitForBigLoader();
		String xpath_a_view_complete_order_info = propUtils.getXpathFromPropFile("HomePage",
				"a_view_complete_order_info");
		objUtil.waitForObject(xpath_a_view_complete_order_info, Setup.bigTimeOutForObjectWait);

		// click for second pop-up
		objUtil.clickElement(xpath_a_view_complete_order_info);
		waitForBigLoader();
		// verify 'Order Info' pop-up exists
		String xpath_elem_order_info_num = propUtils.getXpathFromPropFile("HomePage", "elem_order_info_label");
		objUtil.DoAssert(objUtil.waitForObject(xpath_elem_order_info_num), true,
				"Verify 'Order Info' pop-up is present in order info pop-up");

		// report attorney's name
		String attorneyName = driver.findElement(By.xpath(propUtils.getXpathFromPropFile("HomePage", "p_attorney")))
				.getText();
		objUtil.Reporter__log("Attorney's name: " + attorneyName, "note");

		// close second pop-up
		String xpath_a_order_info_close = propUtils.getXpathFromPropFile("HomePage", "a_order_info_close");
		objUtil.clickElement(xpath_a_order_info_close);

		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
	}

	private void performSearch(String searchTerm, String searchCriteriaForSecondDrp)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		String xpath_drp_secondDropdown = propUtils.getXpathFromPropFile("HomePage", "drp_secondDropdown");
		waitForBigLoader();
		objUtil.clickElement(xpath_drp_secondDropdown);

		// move the mouse a bit to get rid of the tool tip
		objUtil.moveMouse(1, 1);

		String xpath_li_searchCriteria = propUtils.getDynamicXpathFromPropFile("HomePage", "dyn_li_searchCriteria",
				searchCriteriaForSecondDrp);
		objUtil.clickElement(xpath_li_searchCriteria);
		String xpath_input_searchbar = propUtils.getXpathFromPropFile("HomePage", "input_searchbar");
		objUtil.typeInElement(xpath_input_searchbar, searchTerm);
		String xpath_btn_search = propUtils.getXpathFromPropFile("HomePage", "btn_search");
		objUtil.clickElement(xpath_btn_search);
		waitForBigLoader();

		// verify search term is present in searchText on the popup
		String xpath_elem_searchTextOrderNum = propUtils.getXpathFromPropFile("HomePage", "elem_searchTextOrderNum");
		String search_popup_title_text = driver.findElement(By.xpath(xpath_elem_searchTextOrderNum)).getText();
		String substring_searchTerm = searchTerm;
		if (substring_searchTerm.length() > 15)
			substring_searchTerm = searchTerm.substring(0, 15);
		objUtil.DoAssert(search_popup_title_text.contains(" " + substring_searchTerm + " ]"), true,
				"Verify search popup title text contains '" + substring_searchTerm + "'", "", "soft");

		// click button to display orders
		String xpath_btn_displayOrders = propUtils.getXpathFromPropFile("HomePage", "btn_displayOrders");
		objUtil.clickElement(xpath_btn_displayOrders);

		ExpandAllOrders();
	} 

	private void ExpandAllOrders()
			throws InterruptedException, FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		// expand all order rows
		Thread.sleep(3000);
		String xpath_a_expandAllOrders = propUtils.getXpathFromPropFile("HomePage", "a_expandAllOrders");
		waitForBigLoader(); 
		if (!objUtil.waitForObject(xpath_a_expandAllOrders))
			return; // already expanded

		objUtil.clickElement(xpath_a_expandAllOrders);
		waitForBigLoader(); 
		boolean allExpanded = objUtil.waitForObject(propUtils.getXpathFromPropFile("HomePage", "a_collapseAllOrders"));
		if (!allExpanded) {
			// try clicking on 'Expand All' one more time
			objUtil.Reporter__log("clicking expand all once again as rows do not seem to be expanded yet!", "fail");
			objUtil.clickElement(xpath_a_expandAllOrders);
			waitForBigLoader();
			objUtil.waitForObject(propUtils.getXpathFromPropFile("HomePage", "a_collapseAllOrders"));
		}
		try { 
			Thread.sleep(1000); // be cautious, wait a bit more
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void SearchByClaimNum(String tcFileName) throws Exception {
		String searchTerm = dataUtils.getTestDataFromPropFile(tcFileName, "claimNum");
		objUtil.Reporter__log("Searching for orders with claim #: " + searchTerm);

		performSearch(searchTerm, "Claim#");

		// check order info of the last row
		String xpath_a_order_in_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"a_order_in_last_expanded_row");
		String xpath_td_mainOrderNum_of_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"td_mainOrderNum_of_last_expanded_row");
		objUtil.Reporter__log(
				"Navigating to order: " + objUtil.getWebElement(xpath_td_mainOrderNum_of_last_expanded_row).getText()
						+ " --> " + objUtil.getWebElement(xpath_a_order_in_last_expanded_row).getText());
		objUtil.clickElement(xpath_a_order_in_last_expanded_row); // clicking for first pop-up
		String View_Download = propUtils.getXpathFromPropFile("HomePage",
				"View_Download");
		objUtil.clickElement(View_Download); 
		waitForBigLoader();
		String xpath_a_view_complete_order_info = propUtils.getXpathFromPropFile("HomePage",
				"a_view_complete_order_info");
		objUtil.waitForObject(xpath_a_view_complete_order_info, Setup.bigTimeOutForObjectWait);

		// click on view complete info
		objUtil.clickElement(xpath_a_view_complete_order_info);
		waitForBigLoader();
		// verify ordering contact Ordering Contact
    	String[] orderFieldLabelsToBeVerified = new String[] { "Claim #" };
        String[] orderFieldValueKeysInPropFile = new String[] { "claimNum" };
	    verifyOrderFieldsInSecondPopup(tcFileName, orderFieldLabelsToBeVerified, orderFieldValueKeysInPropFile);
		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
	}

	public void SearchByFilm(String tcFileName)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		String searchTerm = dataUtils.getTestDataFromPropFile(tcFileName, "orderNum");
		String subOrderNum_or_Location = dataUtils.getTestDataFromPropFile(tcFileName, "subOrderNum");
		objUtil.Reporter__log("Searching for order (with Film) : " + searchTerm + " --> " + subOrderNum_or_Location);

		performSearch(searchTerm, "Order#");

		waitForBigLoader();

		// check if Films icon exists in the same row as Order-->Location (subOrder)
		String[] order_subOrder_for_Film = new String[] { searchTerm, subOrderNum_or_Location };
		String xpath_dyn_elem_icon_films = propUtils.getDynamicXpathFromPropFile("HomePage", "dyn_elem_icon_films",
				order_subOrder_for_Film);
		boolean wasFilmsIconPresent = objUtil.isElementPresent(xpath_dyn_elem_icon_films);
		objUtil.DoAssert(wasFilmsIconPresent, true, "Verifying presence of 'Films' icon in the same row as "
				+ searchTerm + " --> " + subOrderNum_or_Location);

		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
	}

	public void SearchByPlaintiff(String tcFileName) throws Exception {
		String searchTerm = dataUtils.getTestDataFromPropFile(tcFileName, "plaintiff");
		objUtil.Reporter__log("Searching for orders with Plaintiff Name: " + searchTerm);

		performSearch(searchTerm, "Plaintiff");

		// check order info of the last row
		String xpath_a_order_in_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"a_order_in_last_expanded_row");
		String xpath_td_mainOrderNum_of_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"td_mainOrderNum_of_last_expanded_row");
		objUtil.Reporter__log(
				"Navigating to order: " + objUtil.getWebElement(xpath_td_mainOrderNum_of_last_expanded_row).getText()
						+ " --> " + objUtil.getWebElement(xpath_a_order_in_last_expanded_row).getText());
		objUtil.clickElement(xpath_a_order_in_last_expanded_row); // clicking for first pop-up
		String View_Download = propUtils.getXpathFromPropFile("HomePage",
				"View_Download");
		objUtil.clickElement(View_Download); 
		waitForBigLoader();
		String xpath_a_view_complete_order_info = propUtils.getXpathFromPropFile("HomePage",
				"a_view_complete_order_info");
		objUtil.waitForObject(xpath_a_view_complete_order_info, Setup.bigTimeOutForObjectWait);

		// click on view complete info
		objUtil.clickElement(xpath_a_view_complete_order_info);
		waitForBigLoader();
		// verify Plaintiff Name
		String[] orderFieldLabelsToBeVerified = new String[] { "Plaintiff / Applicant" };
		String[] orderFieldValueKeysInPropFile = new String[] { "plaintiff" };
		verifyOrderFieldsInSecondPopup(tcFileName, orderFieldLabelsToBeVerified, orderFieldValueKeysInPropFile);
	
		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
	}  

	public void SearchBycaseNum(String tcFileName) throws Exception {
		String searchTerm = dataUtils.getTestDataFromPropFile(tcFileName, "caseNum");
		objUtil.Reporter__log("Searching for orders with caseNum : " + searchTerm);

		performSearch(searchTerm, "Case#");

		// check order info of the last row
		String xpath_a_order_in_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"a_order_in_last_expanded_row");
		String xpath_td_mainOrderNum_of_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"td_mainOrderNum_of_last_expanded_row");
		objUtil.Reporter__log(
				"Navigating to order: " + objUtil.getWebElement(xpath_td_mainOrderNum_of_last_expanded_row).getText()
						+ " --> " + objUtil.getWebElement(xpath_a_order_in_last_expanded_row).getText());
		objUtil.clickElement(xpath_a_order_in_last_expanded_row); // clicking for first pop-up
		String View_Download = propUtils.getXpathFromPropFile("HomePage",
				"View_Download");
		objUtil.clickElement(View_Download); 

		waitForBigLoader();
		String xpath_a_view_complete_order_info = propUtils.getXpathFromPropFile("HomePage",
				"a_view_complete_order_info");
		objUtil.waitForObject(xpath_a_view_complete_order_info, Setup.bigTimeOutForObjectWait);

		// click for second pop-up
		objUtil.clickElement(xpath_a_view_complete_order_info);
		waitForBigLoader();
		// verify caseNum
		String[] orderFieldLabelsToBeVerified = new String[] { "Case #" };
		String[] orderFieldValueKeysInPropFile = new String[] { "caseNum" };
		verifyOrderFieldsInSecondPopup(tcFileName, orderFieldLabelsToBeVerified, orderFieldValueKeysInPropFile);

		// close first pop-up
		//String xpath_a_order_details_close = propUtils.getXpathFromPropFile("HomePage", "a_order_details_close");
		//objUtil.clickElement(xpath_a_order_details_close);

		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
	}

	public void SearchByfileNum(String tcFileName) throws Exception {
		String searchTerm = dataUtils.getTestDataFromPropFile(tcFileName, "fileNum");
		objUtil.Reporter__log("Searching for orders with File Num : " + searchTerm);

		performSearch(searchTerm, "File#");

		// check order info of the last row
		String xpath_a_order_in_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"a_order_in_last_expanded_row");
		String xpath_td_mainOrderNum_of_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"td_mainOrderNum_of_last_expanded_row");
		objUtil.Reporter__log(
				"Navigating to order: " + objUtil.getWebElement(xpath_td_mainOrderNum_of_last_expanded_row).getText()
							+ " --> " + objUtil.getWebElement(xpath_a_order_in_last_expanded_row).getText());
		
		objUtil.clickElement(xpath_a_order_in_last_expanded_row); // clicking for first pop-up
		String View_Download = propUtils.getXpathFromPropFile("HomePage",
				"View_Download");
		objUtil.clickElement(View_Download);

		waitForBigLoader();
		String xpath_a_view_complete_order_info = propUtils.getXpathFromPropFile("HomePage",
				"a_view_complete_order_info");
		objUtil.waitForObject(xpath_a_view_complete_order_info, Setup.bigTimeOutForObjectWait);

		// click on view complete info
		objUtil.clickElement(xpath_a_view_complete_order_info);
		waitForBigLoader();
		// verify fileNum
		String[] orderFieldLabelsToBeVerified = new String[] { "File #" };
		String[] orderFieldValueKeysInPropFile = new String[] { "fileNum" };
		verifyOrderFieldsInSecondPopup(tcFileName, orderFieldLabelsToBeVerified, orderFieldValueKeysInPropFile);

		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
	}

	public void SearchBydefendant(String tcFileName) throws Exception {
		String searchTerm = dataUtils.getTestDataFromPropFile(tcFileName, "defendant");
		objUtil.Reporter__log("Searching for orders with Defendant : " + searchTerm);

		performSearch(searchTerm, "Defendant");

		// check order info of the last row
		String xpath_a_order_in_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"a_order_in_last_expanded_row");
		String xpath_td_mainOrderNum_of_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"td_mainOrderNum_of_last_expanded_row");
		objUtil.Reporter__log(
				"Navigating to order: " + objUtil.getWebElement(xpath_td_mainOrderNum_of_last_expanded_row).getText()
						+ " --> " + objUtil.getWebElement(xpath_a_order_in_last_expanded_row).getText());
		objUtil.clickElement(xpath_a_order_in_last_expanded_row); // clicking for first pop-up
		String View_Download = propUtils.getXpathFromPropFile("HomePage",
				"View_Download");
		objUtil.clickElement(View_Download); 

		waitForBigLoader();
		String xpath_a_view_complete_order_info = propUtils.getXpathFromPropFile("HomePage",
				"a_view_complete_order_info");
		objUtil.waitForObject(xpath_a_view_complete_order_info, Setup.bigTimeOutForObjectWait);

		// click on view complete info
		objUtil.clickElement(xpath_a_view_complete_order_info);
		waitForBigLoader();
		// verify defendant
		String[] orderFieldLabelsToBeVerified = new String[] { "Defendant / Respondent" };
		String[] orderFieldValueKeysInPropFile = new String[] { "defendant" };
		verifyOrderFieldsInSecondPopup(tcFileName, orderFieldLabelsToBeVerified, orderFieldValueKeysInPropFile);
		
		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
	}

	public void SearchByattorney(String tcFileName) throws Exception {
		String searchTerm = dataUtils.getTestDataFromPropFile(tcFileName, "attorney");
		objUtil.Reporter__log("Searching for orders with Attorney : " + searchTerm);

		performSearch(searchTerm, "Attorney");

		// check order info of the last row
		String xpath_a_order_in_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"a_order_in_last_expanded_row");
		String xpath_td_mainOrderNum_of_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"td_mainOrderNum_of_last_expanded_row");
		objUtil.Reporter__log(
				"Navigating to order: " + objUtil.getWebElement(xpath_td_mainOrderNum_of_last_expanded_row).getText()
						+ " --> " + objUtil.getWebElement(xpath_a_order_in_last_expanded_row).getText());
		objUtil.clickElement(xpath_a_order_in_last_expanded_row); // clicking for first pop-up
		String View_Download = propUtils.getXpathFromPropFile("HomePage",
				"View_Download");
		objUtil.clickElement(View_Download);
        waitForBigLoader();
		String xpath_a_view_complete_order_info = propUtils.getXpathFromPropFile("HomePage",
				"a_view_complete_order_info");
		objUtil.waitForObject(xpath_a_view_complete_order_info, Setup.bigTimeOutForObjectWait);

		// click on view complete info
	     objUtil.clickElement(xpath_a_view_complete_order_info);
		waitForBigLoader();
		// verify attorney
		String[] orderFieldLabelsToBeVerified = new String[] { "Attorney" }; 
		String[] orderFieldValueKeysInPropFile = new String[] { "attorney" };
		verifyOrderFieldsInSecondPopup(tcFileName, orderFieldLabelsToBeVerified, orderFieldValueKeysInPropFile);

		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
	}

	public void SearchByadjuster(String tcFileName) throws Exception {
		String searchTerm = dataUtils.getTestDataFromPropFile(tcFileName, "adjuster");
		objUtil.Reporter__log("Searching for orders with adjuster : " + searchTerm);

		performSearch(searchTerm, "Adjuster");

		// check order info of the last row
		String xpath_a_order_in_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"a_order_in_last_expanded_row");
		String xpath_td_mainOrderNum_of_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"td_mainOrderNum_of_last_expanded_row");
		objUtil.Reporter__log(
				"Navigating to order: " + objUtil.getWebElement(xpath_td_mainOrderNum_of_last_expanded_row).getText()
						+ " --> " + objUtil.getWebElement(xpath_a_order_in_last_expanded_row).getText());
		objUtil.clickElement(xpath_a_order_in_last_expanded_row); // clicking for first pop-up
		String View_Download = propUtils.getXpathFromPropFile("HomePage",
				"View_Download");
		objUtil.clickElement(View_Download);
		waitForBigLoader();
		String xpath_a_view_complete_order_info = propUtils.getXpathFromPropFile("HomePage",
				"a_view_complete_order_info");
		objUtil.waitForObject(xpath_a_view_complete_order_info, Setup.bigTimeOutForObjectWait);

		// click on view complete info
		objUtil.clickElement(xpath_a_view_complete_order_info);
		waitForBigLoader();
		// verify adjuster
		String[] orderFieldLabelsToBeVerified = new String[] { "Adjuster" };
		String[] orderFieldValueKeysInPropFile = new String[] { "adjuster" };
		verifyOrderFieldsInSecondPopup(tcFileName, orderFieldLabelsToBeVerified, orderFieldValueKeysInPropFile);

		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
	}

	public void SearchByrecordInfo_lastName(String tcFileName) throws Exception {
		String searchTerm = dataUtils.getTestDataFromPropFile(tcFileName, "recordInfo_lastName");
		objUtil.Reporter__log("Searching for orders with Record Info_lastName : " + searchTerm);

		performSearch(searchTerm, "Record Subject");

		// check order info of the last row
		String xpath_a_order_in_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"a_order_in_last_expanded_row");
		String xpath_td_mainOrderNum_of_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"td_mainOrderNum_of_last_expanded_row");
		objUtil.Reporter__log(
				"Navigating to order: " + objUtil.getWebElement(xpath_td_mainOrderNum_of_last_expanded_row).getText()
						+ " --> " + objUtil.getWebElement(xpath_a_order_in_last_expanded_row).getText());
		objUtil.clickElement(xpath_a_order_in_last_expanded_row); // clicking for first pop-up
		String View_Download = propUtils.getXpathFromPropFile("HomePage",
				"View_Download");
		objUtil.clickElement(View_Download);

		waitForBigLoader();
		String xpath_a_view_complete_order_info = propUtils.getXpathFromPropFile("HomePage",
				"a_view_complete_order_info");
		objUtil.waitForObject(xpath_a_view_complete_order_info, Setup.bigTimeOutForObjectWait);

		// click on view complete info
		objUtil.clickElement(xpath_a_view_complete_order_info);
		waitForBigLoader();
		String xpath_order_info_field =driver.findElement(By.xpath(propUtils.getXpathFromPropFile("HomePage","Last_Name"))).getText();
		Assert.assertTrue((xpath_order_info_field.contains(searchTerm)), "Last name diffrent ");
		Reporter.log("Record Information Search was Succesfull");
	
		// close first pop-up
		String xpath_a_order_details_close = propUtils.getXpathFromPropFile("HomePage",
				"a_order_details_close");
		if (objUtil.isElementPresent(xpath_a_order_details_close))
			objUtil.clickElement(xpath_a_order_details_close);
     
		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
	}

	public void SwitchClient(String tcFileName)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		String currentClient = dataUtils.getTestDataFromPropFile(tcFileName, "currentClient");
		String newClient = dataUtils.getTestDataFromPropFile(tcFileName, "newClient");

		// check if current client name is correctly displayed
		String xpath_elem_switch_client_name = propUtils.getXpathFromPropFile("HomePage", "elem_switch_client_name");
		objUtil.DoAssert(objUtil.getWebElement(xpath_elem_switch_client_name).getText(), currentClient,
				"Verifying current client name is correctly displayed");

		// switch client
		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "a_switch_client"));
		String xpath_btn_switch_client = propUtils.getXpathFromPropFile("HomePage", "btn_switch_client");
		objUtil.waitUntilElementIsVisible(xpath_btn_switch_client);
		// once again, verify correct client name (current selection) is displayed
		objUtil.DoAssert(
				objUtil.getWebElement(propUtils.getXpathFromPropFile("HomePage", "td_current_client")).getText(),
				currentClient, "Verifying current client name is correctly displayed in the pop-up window");
		// select & switch to new client
		objUtil.clickElement(propUtils.getDynamicXpathFromPropFile("HomePage", "dyn_radioBtn_new_client", newClient));
		objUtil.clickElement(xpath_btn_switch_client);
		waitForBigLoader();

		// check if new client name is correctly displayed
		objUtil.DoAssert(objUtil.getWebElement(xpath_elem_switch_client_name).getText().trim(), newClient.trim(),
				"Verifying new client name is correctly displayed");
	}

	/**
	 * 
	 * @param fullOrder - Set as 'True' for checking the box adjacent to Order. Set
	 *                  as 'False' for selecting just one order location.
	 */
	public void DownloadDocument(boolean fullOrder)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		// delete any pre-existing files in the download folder
		File dir = new File(Setup.downloadFolderPath);
	    File[] dirContents = dir.listFiles();
		for (int i = 0; i < dirContents.length; i++) {
			dirContents[i].delete();
		}

		ExpandAllOrders();
		// search for first subOrder(location) row which has no document
		String xpath_withDoc_rows = propUtils.getXpathFromPropFile("HomePage", "elem_rows_with_doc");
		List<WebElement> withDoc_orders = driver.findElements(By.xpath(xpath_withDoc_rows));
		int foundFilteredOrders = withDoc_orders.size();
		while (foundFilteredOrders == 0) {
			objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "elem_next_page"));
			waitForBigLoader();
			withDoc_orders = driver.findElements(By.xpath(xpath_withDoc_rows));
			foundFilteredOrders = withDoc_orders.size();
		}
		String[] foundOrder_id = withDoc_orders.get(0).getAttribute("id").split("-");
		String orderNum = foundOrder_id[1];
		String subOrderNum_or_Location = foundOrder_id[2];

		// select the checkbox adjacent to either "order-->subOrder" or "main Order"
		String xpath_dyn_elem_order_OR_orderSubOrder_checkbox = null;
		if (!fullOrder) {
			objUtil.Reporter__log("Accessing order --> location : " + orderNum + " --> " + subOrderNum_or_Location,
					"note");
			String[] order_subOrder = new String[] { orderNum, subOrderNum_or_Location };
			xpath_dyn_elem_order_OR_orderSubOrder_checkbox = propUtils.getDynamicXpathFromPropFile("HomePage",
					"dyn_elem_order_subOrder_checkbox", order_subOrder);
		} else {
			objUtil.Reporter__log("Accessing order : " + orderNum, "note");
			xpath_dyn_elem_order_OR_orderSubOrder_checkbox = propUtils.getDynamicXpathFromPropFile("HomePage",
					"dyn_elem_order_checkbox", orderNum);
		}
		String viewDownloadBtn = propUtils.getXpathFromPropFile("HomePage", "li_viewDownload");
		objUtil.clickElement(xpath_dyn_elem_order_OR_orderSubOrder_checkbox);

		// click on View/Download
		objUtil.clickElement(viewDownloadBtn);
		waitForBigLoader();
		// interact with the pop-up
		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "elem_myDocumentsTab"));
		waitForBigLoader();
		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "btn_download"));
		objUtil.DoAssert(objUtil.getWebElement(propUtils.getXpathFromPropFile("HomePage", "h1_popup")).getText(),
				"Downloading Multiple Documents", "Verifying title is correctly displayed in the pop-up window", "",
				"soft");
		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "btn_popup_ok"));

		// verify that file has been downloaded
		boolean downloaded = false;
		int tries = 1;
		int maxTries = 5;
		while (!downloaded) {
			if (tries > maxTries)
				break;

			File[] dirContents_current = dir.listFiles();
			if (dirContents_current.length > 0)
				downloaded = true;
			else {
				objUtil.Reporter__log(
						"Try " + tries + "/" + maxTries + ": File not downloaded yet. Waiting for a few seconds..");
				Thread.sleep(1000 * Setup.defaultTimeOutForObjectWait); // wait for a few seconds
			}
			tries += 1;
		}
		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "a_popup_close"));
		objUtil.DoAssert(downloaded, true, "Verifying File has been downloaded successfully");

		// 'uncheck' to reset to initial state
		objUtil.clickElement(xpath_dyn_elem_order_OR_orderSubOrder_checkbox);
	}
 
	/**
	 * 
	 * @param fullOrder - Set as 'True' for checking the box adjacent to Order. Set
	 *                  as 'False' for selecting just one order location.
	 */
	public void UploadDocument(boolean fullOrder) throws FileNotFoundException, IOException,
			ElementNotLocatedOnUIException, InterruptedException, RobotException, AWTException {
		ExpandAllOrders();
		// search for first subOrder(location) row which has no document
		String xpath_noDoc_rows = propUtils.getXpathFromPropFile("HomePage", "elem_rows_with_no_doc");
		List<WebElement> noDoc_orders = driver.findElements(By.xpath(xpath_noDoc_rows));
		int foundFilteredOrders = noDoc_orders.size();
		while (foundFilteredOrders == 0) {
			objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "elem_next_page"));
			waitForBigLoader();
			ExpandAllOrders();
			noDoc_orders = driver.findElements(By.xpath(xpath_noDoc_rows));
			foundFilteredOrders = noDoc_orders.size();
		}
		String[] foundOrder_id = noDoc_orders.get(0).getAttribute("id").split("-");
		String orderNum = foundOrder_id[1];
		String subOrderNum_or_Location = foundOrder_id[2];

		// select the checkbox adjacent to either "order-->subOrder" or "main Order"
		String xpath_dyn_elem_order_OR_orderSubOrder_checkbox = null;
		if (!fullOrder) {
			objUtil.Reporter__log("Accessing order --> location : " + orderNum + " --> " + subOrderNum_or_Location,
					"note");
			String[] order_subOrder = new String[] { orderNum, subOrderNum_or_Location };
			xpath_dyn_elem_order_OR_orderSubOrder_checkbox = propUtils.getDynamicXpathFromPropFile("HomePage",
					"dyn_elem_order_subOrder_checkbox", order_subOrder);
		} else {
			objUtil.Reporter__log("Accessing order : " + orderNum, "note");
			xpath_dyn_elem_order_OR_orderSubOrder_checkbox = propUtils.getDynamicXpathFromPropFile("HomePage",
					"dyn_elem_order_checkbox", orderNum);
		}
		String uploadBtn = propUtils.getXpathFromPropFile("HomePage", "li_upload");
		objUtil.clickElement(xpath_dyn_elem_order_OR_orderSubOrder_checkbox);

		objUtil.clickElement(uploadBtn);
		waitForBigLoader();
		// interact with the pop-ups
		WebElement browseBtn = objUtil.getWebElement(propUtils.getXpathFromPropFile("HomePage", "btn_browse"));
		objUtil.clickElement(browseBtn);

		Thread.sleep(2000); // allow windows popup to appear 
		Robot robot = new Robot();
		objUtil.sendRobotKeys(robot, Setup.uploadFile); 
		Thread.sleep(100);
		robot.keyPress(KeyEvent.VK_ENTER);

		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "btn_upload"));
		waitForBigLoader();
		objUtil.DoAssert( driver.findElement(By.xpath(propUtils.getXpathFromPropFile("HomePage", "elem_upload_success")))
				.getText(),"Files ready To Submit", "Verifying success message after file upload");
		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "btn_submitUpload"));
		waitForBigLoader();
		objUtil.DoAssert( driver.findElement(By.xpath(propUtils.getXpathFromPropFile("HomePage", "elem_submit_success")))
				.getText(),"Files submited successfully !!", "Verifying success message after file upload");
		String ok_button = propUtils.getXpathFromPropFile("HomePage", "btn_popup_ok");
		objUtil.clickElement(ok_button);
		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "elem_cancel"));
		waitForBigLoader();

		// search for order & verify icons are present in rows
		performSearch(orderNum, "Order#");
		waitForBigLoader();
		if (!fullOrder) {
			boolean documentExists = objUtil.waitForObject(propUtils.getDynamicXpathFromPropFile("HomePage",
					"elem_doc_in_row", new String[] { orderNum, subOrderNum_or_Location }));
			objUtil.DoAssert(documentExists, true, "Verifying that file icon is present in the table row now");
		} else {
			// need to verify for each location of this order
			String xpath_elem_dyn_subOrders_in_Order = propUtils.getDynamicXpathFromPropFile("HomePage",
					"elem_dyn_subOrders_in_Order", orderNum);
			List<WebElement> subOrders = driver.findElements(By.xpath(xpath_elem_dyn_subOrders_in_Order)); 
			for (int i = 1; i <= subOrders.size(); i++) {
				String subOrder = String.format("%03d", i);
				boolean documentExists = objUtil.waitForObject(propUtils.getDynamicXpathFromPropFile("HomePage",
						"elem_doc_in_row", new String[] { orderNum, subOrder }));
				objUtil.DoAssert(documentExists, true,
						"Verifying that file icon is present in the table row " + orderNum + "-->" + subOrder + " now");
			}
		}

		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
	}
 
	/**
	 * 
	 * @param fullOrder - Set as 'True' for checking the box adjacent to Order. Set
	 *                  as 'False' for selecting just one order location.
	 */
	public void CancelOrder(boolean fullOrder)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		ExpandAllOrders();
		// search for first subOrder(location) row which is still in 'inProcess' state &
		// not already cancelled
		String xpath_inProcess_rows = propUtils.getXpathFromPropFile("HomePage", "elem_inProcess_orders");
		List<WebElement> inProcess_orders = driver.findElements(By.xpath(xpath_inProcess_rows));
		int foundFilteredOrders = inProcess_orders.size();
		while (foundFilteredOrders == 0) {
			objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "elem_next_page"));
			waitForBigLoader();
			ExpandAllOrders();
			inProcess_orders = driver.findElements(By.xpath(xpath_inProcess_rows));
			foundFilteredOrders = inProcess_orders.size();
		}
		String[] foundOrder_id = inProcess_orders.get(0).getAttribute("id").split("-");
		String orderNum = foundOrder_id[1];
		String subOrderNum_or_Location = foundOrder_id[2];

		// select the checkbox adjacent to either "order-->subOrder" or "main Order"
		String xpath_dyn_elem_order_OR_orderSubOrder_checkbox = null;
		if (!fullOrder) {
			objUtil.Reporter__log("Accessing order --> location : " + orderNum + " --> " + subOrderNum_or_Location,
					"note");
			String[] order_subOrder = new String[] { orderNum, subOrderNum_or_Location };
			xpath_dyn_elem_order_OR_orderSubOrder_checkbox = propUtils.getDynamicXpathFromPropFile("HomePage",
					"dyn_elem_order_subOrder_checkbox", order_subOrder);
		} else {
			objUtil.Reporter__log("Accessing order : " + orderNum, "note");
			xpath_dyn_elem_order_OR_orderSubOrder_checkbox = propUtils.getDynamicXpathFromPropFile("HomePage",
					"dyn_elem_order_checkbox", orderNum);
		}
		String cancelBtn = propUtils.getXpathFromPropFile("HomePage", "li_cancel_order");
		objUtil.clickElement(xpath_dyn_elem_order_OR_orderSubOrder_checkbox);

		objUtil.clickElement(cancelBtn);
		waitForBigLoader();
		// interact with the pop-ups
		objUtil.typeInElement(propUtils.getXpathFromPropFile("HomePage", "elem_notes_textarea"),
				"Automation Cancel Note");
		Thread.sleep(1500); // takes some time for the button to be enabled after note is filled in
		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "btn_cancel_order_in_popup"));

		// handle browser alert popup
		objUtil.handlePopup(true);
		waitForBigLoader();
		objUtil.DoAssert(
				objUtil.getWebElement(propUtils.getXpathFromPropFile("HomePage", "elem_cancel_success_result"))
						.getText().contains("Order(s) Cancelled Successfully"),
				true, "Verifying success message \"Order(s) Cancelled Successfully\" after cancelling order");
		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "elem_close_popup"));

		// search for order & verify icons are present in rows
		performSearch(orderNum, "Order#");
		waitForBigLoader();
		if (!fullOrder) {
			boolean crossExists = objUtil.waitForObject(propUtils.getDynamicXpathFromPropFile("HomePage",
					"elem_cancelledIcon_in_row", new String[] { orderNum, subOrderNum_or_Location }));
			objUtil.DoAssert(crossExists, true, "Verifying that cancel (cross) icon is present in the table row now");
		} else {
			// need to verify for each location of this order
			String xpath_elem_dyn_subOrders_in_Order = propUtils.getDynamicXpathFromPropFile("HomePage",
					"elem_dyn_subOrders_in_Order", orderNum);
			List<WebElement> subOrders = driver.findElements(By.xpath(xpath_elem_dyn_subOrders_in_Order));
			for (int i = 1; i <= subOrders.size(); i++) {
				String subOrder = String.format("%03d", i);
				boolean crossExists = objUtil.waitForObject(propUtils.getDynamicXpathFromPropFile("HomePage",
						"elem_cancelledIcon_in_row", new String[] { orderNum, subOrder }));
				objUtil.DoAssert(crossExists, true, "Verifying that cancel (cross) icon is present in the table row "
						+ orderNum + "-->" + subOrder + " now");
			}
		}

		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
	}

	/**
	 * 
	 * @param fullOrder - Set as 'True' for checking the box adjacent to Order. Set
	 *                  as 'False' for selecting just one order location.
	 */
	public void AddComment(boolean fullOrder)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		ExpandAllOrders();

		// select the checkbox adjacent to either "order-->subOrder" or "main Order"
		String xpath_elem_order_OR_orderSubOrder_checkbox = null;
		String orderNum = null;
		String subOrderNum_or_Location = null;
		if (!fullOrder) {
			xpath_elem_order_OR_orderSubOrder_checkbox = propUtils.getXpathFromPropFile("HomePage",
					"dyn_elem_first_order_subOrder_checkbox");
			WebElement currOrder = objUtil.getWebElement(xpath_elem_order_OR_orderSubOrder_checkbox);
			String[] orderDeets = currOrder.getAttribute("name").split("_")[1].split("-");
			orderNum = orderDeets[0];
			subOrderNum_or_Location = orderDeets[1];
			objUtil.Reporter__log("Accessing order --> location : " + orderNum + " --> " + subOrderNum_or_Location,
					"note");
		} else {
			xpath_elem_order_OR_orderSubOrder_checkbox = propUtils.getXpathFromPropFile("HomePage",
					"dyn_elem_first_order_checkbox");
			WebElement currOrder = objUtil.getWebElement(xpath_elem_order_OR_orderSubOrder_checkbox);
			orderNum = currOrder.getAttribute("name").split("_")[1];
			objUtil.Reporter__log("Accessing order : " + orderNum, "note");
		}
		String addCommentBtn = propUtils.getXpathFromPropFile("HomePage", "li_addComment");
		objUtil.clickElement(xpath_elem_order_OR_orderSubOrder_checkbox);

		objUtil.clickElement(addCommentBtn);
		waitForBigLoader();
		// interact with the pop-up
		String autoGeneratedComment = "Automation comment on " + objUtil.getCurrentTimeStamp();
		objUtil.typeInElement(propUtils.getXpathFromPropFile("HomePage", "textarea_comment"), autoGeneratedComment);
		Thread.sleep(1500); // takes some time for the button to be enabled after note is filled in
		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "btn_add_comment"));
		waitForBigLoader();

		// verify note is successfully added
		objUtil.DoAssert(
				objUtil.getWebElement(propUtils.getXpathFromPropFile("HomePage", "elem_comment_success")).getText()
						.contains("Comments added successfully"),
				true, "Verifying success message \"Comments added successfully\" after adding comment");
		if (!fullOrder)
			objUtil.DoAssert(
					objUtil.getWebElement(propUtils.getXpathFromPropFile("HomePage", "elem_comment_in_table")).getText()
							.contains("[Super Admin] " + autoGeneratedComment),
					true, "Verifying comment \"[Super Admin] " + autoGeneratedComment + "\" is present in table");

		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "a_close_comment_popup"));
		driver.navigate().refresh(); // remove any checked boxes; reset state

		if (fullOrder) {
			// search for the order & verify comment present in each subOrder(location)
			performSearch(orderNum, "Order#");
			waitForBigLoader();
			// need to verify for each location of this order
			String xpath_elem_dyn_subOrders_in_Order = propUtils.getDynamicXpathFromPropFile("HomePage",
					"elem_dyn_subOrders_in_Order", orderNum);
			List<WebElement> subOrders = driver.findElements(By.xpath(xpath_elem_dyn_subOrders_in_Order));
			for (int i = 1; i <= 3; i++) {
				String subOrder = String.format("%03d", i);
				String chkBox_order_subOrder = propUtils.getDynamicXpathFromPropFile("HomePage",
						"dyn_elem_order_subOrder_checkbox", new String[] { orderNum, subOrder });
				objUtil.clickElement(chkBox_order_subOrder);
				objUtil.clickElement(addCommentBtn); 
				waitForBigLoader();
				Thread.sleep(1000);
				// interact with the pop-up & verify note is successfully added
				objUtil.DoAssert(
						driver.findElement(By.xpath(propUtils.getXpathFromPropFile("HomePage", "elem_comment_in_table")))
								.getText().contains("[Super Admin] " + autoGeneratedComment),
						true, "Verifying comment \"[Super Admin] " + autoGeneratedComment
								+ "\" is present in table for " + orderNum + " --> " + subOrder);
				objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "a_close_comment_popup"));
				objUtil.clickElement(chkBox_order_subOrder); // un-check-box
			}

			// reset for a clean slate
			String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
			objUtil.clickElement(xpath_btn_resetSearch);
			waitForBigLoader();
		} 
	}

	public void AddLocation()
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		ExpandAllOrders();

		// select the checkbox of first "main Order" & also, count its locations
		String xpath_elem_order_checkbox = propUtils.getXpathFromPropFile("HomePage", "dyn_elem_first_order_checkbox");
		WebElement currOrder = objUtil.getWebElement(xpath_elem_order_checkbox);
		String orderNum = currOrder.getAttribute("name").split("_")[1];
		objUtil.Reporter__log("Accessing order : " + orderNum, "note");
		String xpath_elem_dyn_subOrders_in_Order = propUtils.getDynamicXpathFromPropFile("HomePage",
				"elem_dyn_subOrders_in_Order", orderNum);
		List<WebElement> subOrders = driver.findElements(By.xpath(xpath_elem_dyn_subOrders_in_Order));
		int current_count_locations = subOrders.size();
		objUtil.Reporter__log("Initial count of locations : " + current_count_locations, "note");
		objUtil.clickElement(xpath_elem_order_checkbox);

		// add location
		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "li_addLocation"));
		objUtil.typeInElement(propUtils.getXpathFromPropFile("AddLocationPage", "input_searchLocation"), "alex");
		objUtil.clickElement(propUtils.getXpathFromPropFile("AddLocationPage", "elem_autoComplete_list"));
		objUtil.selectItemFromDropdown(propUtils.getXpathFromPropFile("AddLocationPage", "drp_recType"), "    Dental",
				true, false, -1);
		objUtil.clickElement(propUtils.getXpathFromPropFile("AddLocationPage", "a_confirmSelection"));
		waitForBigLoader();
		objUtil.typeInElement(propUtils.getXpathFromPropFile("AddLocationPage", "input_searchLocation"), "alex");
		objUtil.clickElement(propUtils.getXpathFromPropFile("AddLocationPage", "elem_autoComplete_list"));
		objUtil.selectItemFromDropdown(propUtils.getXpathFromPropFile("AddLocationPage", "drp_recType"), "    Dental",
				true, false, -1);
		objUtil.clickElement(propUtils.getXpathFromPropFile("AddLocationPage", "a_confirmSelection"));
		waitForBigLoader();
		objUtil.clickElement(propUtils.getXpathFromPropFile("AddLocationPage", "a_submit"));
		waitForBigLoader();
		// yes, needed twice
		objUtil.clickElement(propUtils.getXpathFromPropFile("AddLocationPage", "a_submit"));
		waitForBigLoader();
		
		objUtil.clickElement(propUtils.getXpathFromPropFile("AddLocationPage", "a_submit"));
		waitForBigLoader();
 
		objUtil.clickElement(propUtils.getXpathFromPropFile("HomePage", "a_myOrders"));
		performSearch(orderNum, "Order#");
		waitForBigLoader();

		subOrders = driver.findElements(By.xpath(xpath_elem_dyn_subOrders_in_Order));
		int updated_count_locations = subOrders.size();
		objUtil.Reporter__log("Updated count of locations : " + updated_count_locations, "note");
		driver.navigate().refresh(); // remove any checked boxes; reset state
		objUtil.DoAssert(String.valueOf(updated_count_locations), String.valueOf(current_count_locations + 2),
				"Verifying that the new count of locations is 1 more than the old count");
	}
	public void SearchByrecordLocation(String tcFileName) throws Exception {
		String searchTerm = dataUtils.getTestDataFromPropFile(tcFileName, "location");
		objUtil.Reporter__log("Searching for orders with Record Info_lastName : " + searchTerm);

		performSearch(searchTerm, "Record Location");

		// check order info of the last row
		String xpath_a_order_in_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"a_order_in_last_expanded_row");
		String xpath_td_mainOrderNum_of_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"td_mainOrderNum_of_last_expanded_row");
		objUtil.Reporter__log(
				"Navigating to order: " + objUtil.getWebElement(xpath_td_mainOrderNum_of_last_expanded_row).getText()
						+ " --> " + objUtil.getWebElement(xpath_a_order_in_last_expanded_row).getText());
		objUtil.clickElement(xpath_a_order_in_last_expanded_row); // clicking for first pop-up
		String View_Download = propUtils.getXpathFromPropFile("HomePage",
				"View_Download");
		objUtil.clickElement(View_Download);

		waitForBigLoader();
		String xpath_a_view_complete_order_info = propUtils.getXpathFromPropFile("HomePage",
				"a_view_complete_order_info");
		objUtil.waitForObject(xpath_a_view_complete_order_info, Setup.bigTimeOutForObjectWait);

		// click on view complete info
		objUtil.clickElement(xpath_a_view_complete_order_info);
		waitForBigLoader();
		String xpath_order_info_field =driver.findElement(By.xpath(propUtils.getXpathFromPropFile("HomePage","elem_location"))).getText();
		System.out.println(xpath_order_info_field);
		Assert.assertTrue((xpath_order_info_field.contains(searchTerm)), "Location name not same ");
		Reporter.log("Record Location Search was Succesfull");
	
		// close first pop-up
		String xpath_a_order_details_close = propUtils.getXpathFromPropFile("HomePage",
				"a_order_details_close");
		if (objUtil.isElementPresent(xpath_a_order_details_close))
			objUtil.clickElement(xpath_a_order_details_close);
     
		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader();
}
	public void SearchByOrderingContact(String tcFileName) throws Exception {
		String searchTerm = dataUtils.getTestDataFromPropFile(tcFileName, "contact");
		objUtil.Reporter__log("Searching for orders with ordering contact: " + searchTerm);

		performSearch(searchTerm, "Ordering Contact");

		// check order info of the last row
		String xpath_a_order_in_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"a_order_in_last_expanded_row");
		String xpath_td_mainOrderNum_of_last_expanded_row = propUtils.getXpathFromPropFile("HomePage",
				"td_mainOrderNum_of_last_expanded_row");
		objUtil.Reporter__log(
				"Navigating to order: " + objUtil.getWebElement(xpath_td_mainOrderNum_of_last_expanded_row).getText()
						+ " --> " + objUtil.getWebElement(xpath_a_order_in_last_expanded_row).getText());
		objUtil.clickElement(xpath_a_order_in_last_expanded_row); // clicking for first pop-up
		String View_Download = propUtils.getXpathFromPropFile("HomePage",
				"View_Download");
		objUtil.clickElement(View_Download); 
		waitForBigLoader();
		String xpath_a_view_complete_order_info = propUtils.getXpathFromPropFile("HomePage",
				"a_view_complete_order_info");
		objUtil.waitForObject(xpath_a_view_complete_order_info, Setup.bigTimeOutForObjectWait);

		// click on view complete info
		objUtil.clickElement(xpath_a_view_complete_order_info);
		waitForBigLoader();
		// verify claim # 
    	String[] orderFieldLabelsToBeVerified = new String[] { "Contact" };
        String[] orderFieldValueKeysInPropFile = new String[] { "contact" };
	    verifyOrderFieldsInSecondPopup(tcFileName, orderFieldLabelsToBeVerified, orderFieldValueKeysInPropFile);
		// reset for a clean slate
		String xpath_btn_resetSearch = propUtils.getXpathFromPropFile("HomePage", "btn_resetSearch");
		objUtil.clickElement(xpath_btn_resetSearch);
		waitForBigLoader(); 
	}
}
 