package autoFW.TESTS;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import autoFW.autfns.AUTFunctions;
import autoFW.exceptions.ElementNotLocatedOnUIException;
import autoFW.exceptions.RobotException;
import autoFW.objects.ObjectUtilities;
import autoFW.readWrite.OpenReadWriteExcelFunctions;
import autoFW.xpaths.GetXPathFromPropFile;

public class Compex_tests {

	String fileName;
	String sheetName_Global;
	public static WebDriver driver;
	ObjectUtilities objUtil;
	GetXPathFromPropFile propUtils;
	AUTFunctions aut;
	OpenReadWriteExcelFunctions excelOperations;

	@BeforeClass
	public void testSetup() {
		fileName = Setup.fileName;
		sheetName_Global = Setup.sheetName_Global;
		driver = Setup.driver;
		excelOperations = new OpenReadWriteExcelFunctions();
		propUtils = new GetXPathFromPropFile();
		objUtil = new ObjectUtilities();
		aut = new AUTFunctions();
	}

	@Test
	@Parameters({ "AUTurl", "CustodianUsername", "CustodianPwd" })
	public void VerifyLoginAsCustodian(String url, String userName, String pwd)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		aut.loginAsCustodian(url, userName, pwd); 
		aut.logout();
	}
	

	@Test
	@Parameters({ "AUTurl", "SuperUsername", "SuperPwd" })
	public void LoginAsSuperUser(String url, String userName, String pwd)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		aut.loginWithGivenUserName(url, userName, pwd);
	}

	
	@Test
	@Parameters({ "AUTurl", "SuperUsername", "SuperPwd" })
	public void VerifyLoginAsSuperUser(String url, String userName, String pwd)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		aut.loginWithGivenUserName(url, userName, pwd);

		String xpath_drp_firstDropdownCurr = propUtils.getXpathFromPropFile("HomePage",
				"drp_firstDropdownCurrSelection");
		WebElement firstDrpCurr = objUtil.getWebElement(xpath_drp_firstDropdownCurr);
		objUtil.DoAssert(firstDrpCurr.getText(), "All Clients",
				"Default selection in first dropdown should be 'All Clients'");
		objUtil.clickElement(firstDrpCurr);
		objUtil.moveMouse(1, 1);

		String xpath_drp_firstDropdown = propUtils.getXpathFromPropFile("HomePage", "drp_firstDropdown");
		WebElement firstDrp = objUtil.getWebElement(xpath_drp_firstDropdown);
		objUtil.DoAssert(firstDrp.getText(), "My Account\nAll Contacts\nAll Clients",
				"Verifying all options in first dropdown");
		objUtil.clickElement(firstDrpCurr);
		Logout();
	}
	

	@Test
	@Parameters({ "AUTurl", "ClientManagerUsername", "ClientManagerPwd" })
	public void LoginAsClientManager(String url, String userName, String pwd)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		aut.loginWithGivenUserName(url, userName, pwd);
	}

	@Test
	@Parameters({ "AUTurl", "ClientManagerUsername", "ClientManagerPwd" })
	public void VerifyLoginAsClientManager(String url, String userName, String pwd)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		aut.loginWithGivenUserName(url, userName, pwd);

		String xpath_drp_firstDropdownCurr = propUtils.getXpathFromPropFile("HomePage",
				"drp_firstDropdownCurrSelection");
		WebElement firstDrpCurr = objUtil.getWebElement(xpath_drp_firstDropdownCurr);
		objUtil.DoAssert(firstDrpCurr.getText(), "My Account,All Contacts",
				"Default selection in first dropdown should be 'My Account,All Contacts'");
		objUtil.clickElement(firstDrpCurr);
		objUtil.moveMouse(1, 1);

		String xpath_drp_firstDropdown = propUtils.getXpathFromPropFile("HomePage", "drp_firstDropdown");
		WebElement firstDrp = objUtil.getWebElement(xpath_drp_firstDropdown);
		objUtil.DoAssert(firstDrp.getText(), "My Account\nAll Contacts", "Verifying all options in first dropdown");
		objUtil.clickElement(firstDrpCurr);
		Logout();
	}

	@Test
	@Parameters({ "AUTurl", "ClientContactUsername", "ClientContactPwd" })
	public void LoginAsClientContact(String url, String userName, String pwd)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		aut.loginWithGivenUserName(url, userName, pwd);
	}

	@Test
	@Parameters({ "AUTurl", "ClientContactUsername", "ClientContactPwd" })
	public void VerifyLoginAsClientContact(String url, String userName, String pwd)
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		aut.loginWithGivenUserName(url, userName, pwd);
		String xpath_drp_myAccount = propUtils.getXpathFromPropFile("HomePage", "drp_myAccount");
		objUtil.DoAssert(objUtil.waitForObject(xpath_drp_myAccount), true, "Dropdown 'My Account' should exist");
		Logout();
	}

	@Test
	public void Logout() throws FileNotFoundException, ElementNotLocatedOnUIException, IOException {
		aut.logout();
	}


	@Test
	public void DemoSearchOrder() throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		aut.DemoSearchOrder("TC001");
 
		// Demo: Fetch data from excel
		excelOperations.openExcelFile(fileName);
		String appName = excelOperations.readExcelData(
				excelOperations.getMatchRowNumber("AppName", 1, fileName, sheetName_Global), 2, sheetName_Global);
		String userRole = excelOperations.readExcelData(
				excelOperations.getMatchRowNumber("UserRole", 1, fileName, sheetName_Global), 2, sheetName_Global);
		excelOperations.closeExcel();

		objUtil.Reporter__log("Fetching from Excel:", "Pass");
		objUtil.Reporter__log(appName);
		objUtil.Reporter__log(userRole);

	}


	@Test
	public void SearchOrder() throws Exception {
		aut.SearchOrder("SearchOrderParams");
	}

	@Test
	public void SearchByOrderNum1()
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		aut.SearchByOrderNum("SearchOrderParams");
	}
	
	@Test
	public void SearchByOrderNum2()
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		aut.SearchByOrderNum("SearchOrderParams2");
	}
	

	@Test
	public void SearchByClaimNum() throws Exception {
		aut.SearchByClaimNum("SearchOrderParams_001");
	}


	@Test
	public void SearchByPlaintiff() throws Exception {
		aut.SearchByPlaintiff("SearchOrderParams_001");
	}


@Test
	public void SearchBycaseNum() throws Exception {
		aut.SearchBycaseNum("SearchOrderParams_001");
	}

	@Test
	public void SearchBydefendant() throws Exception {
		aut.SearchBydefendant("SearchOrderParams_001");
	}

	@Test
	public void SearchByattorney() throws Exception {
		aut.SearchByattorney("SearchOrderParams_001");
	}



	@Test
	public void SearchByadjuster() throws Exception {
		aut.SearchByadjuster("SearchOrderParams_001");
	}


	@Test
	public void SearchByFilm()
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		aut.SearchByFilm("SearchOrderParams_ForFilms_002");
	}


	@Test
	public void VerifyClientSwitch() throws FileNotFoundException, IOException, ElementNotLocatedOnUIException {
		aut.SwitchClient("SwitchClients");
	}


	@Test
	public void DownloadDocument_FullOrder()
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		aut.DownloadDocument(true);
	}

	@Test
	public void DownloadDocument_SingleOrderLocation()
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		aut.DownloadDocument(false); 
	}
	

	@Test
	public void UploadDocument_FullOrder() throws FileNotFoundException, IOException, ElementNotLocatedOnUIException,
			InterruptedException, AWTException, RobotException {
		aut.UploadDocument(true); 
	}
	@Test
	public void UploadDocument_SingleOrder() throws FileNotFoundException, IOException, ElementNotLocatedOnUIException,
			InterruptedException, AWTException, RobotException {
		aut.UploadDocument(false); 
	}

	@Test
	public void Cancel_FullOrder()
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		aut.CancelOrder(true);
	}

	@Test
	public void Cancel_SingleOrderLocation()
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		aut.CancelOrder(false);
	}

	@Test
	public void AddComment_SingleOrderLocation()
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		aut.AddComment(false);
	}

	@Test
	public void AddComment_FullOrder() 
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		aut.AddComment(true);
	}

	@Test
	public void AddLocation()
			throws FileNotFoundException, IOException, ElementNotLocatedOnUIException, InterruptedException {
		aut.AddLocation();
	}
	@Test 
	public void SearchByrecordInfo_lastName() throws Exception {
		aut.SearchByrecordInfo_lastName("SearchOrderParams_001");
	}

	@Test
	public void SearchByfileNum() throws Exception {
		aut.SearchByfileNum("SearchOrderParams_001");
	}

@Test
public void UploadDocument_SingleOrderLocation() throws FileNotFoundException, IOException,
		ElementNotLocatedOnUIException, InterruptedException, AWTException, RobotException {
	aut.UploadDocument(false);
}
@Test
public void SearchByrecordLocation() throws Exception {
	aut.SearchByrecordLocation("SearchOrderParams_001");
} 
@Test
public void SearchByOrderingContact() throws Exception {
	aut.SearchByOrderingContact("SearchOrderParams_001");
}
}