package autoFW.TESTS;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class Setup {

	public static WebDriver driver;
	public static int defaultTimeOutForObjectWait = 25;
	public static int smallTimeOutForObjectWait = 3;
	public static int bigTimeOutForObjectWait = 40;
	public static String fileName = "src/test/resources/TestData/Global_Test_Data.xlsx";;
	public static String sheetName_Global = "Globals";
	public static String downloadFolderPath = "\\src\\test\\resources\\Downloads";
	public static String uploadFolderPath = "\\src\\test\\resources\\Uploads\\";
	public static String uploadFile = "automationUpload.png";

	@Test
	public void Init() {
		System.out.println("BeforeClassSetup");
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver108.exe");
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromePref = new HashMap<>();

		uploadFolderPath = System.getProperty("user.dir") + uploadFolderPath;
		uploadFolderPath = uploadFolderPath.replace("\\\\", "\\");
		uploadFile = uploadFolderPath + uploadFile;
		
		downloadFolderPath = System.getProperty("user.dir") + downloadFolderPath;
		chromePref.put("download.default_directory", downloadFolderPath);
		options.setExperimentalOption("prefs", chromePref);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	}
}
