package utils.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.testng.Assert;
import utils.selenium.NavigationHelper;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import static utils.framework.Logger.logger;


/**
 * Created by sourabh on 2/25/16.
 */
public class BaseDriver {
	public String browser;
	public String defaultBrowser = "firefox";
	private DesiredCapabilities capabilities;
	protected static WebDriver driver;
	protected static WebDriver UI;
	protected static WebDriver UICustom;
	protected static WebDriver UIArchive;
	public Properties globalProperties;
	public int timeOutInSeconds;
	public static String baseURL;
	Recorder recorder = new Recorder();
	public String reportLocation;
	public String screenshotLocation;
	public static String videoLocation;
	public static String testcaseId;
	public static String browserAgent;
	public String headerSearchTextBox;
	public String headerASSearchbutton;
	public String defaultDimentions;
	public int scrollDownForHeader;
	public Map<String, String> imageLib = new HashMap<String, String>();
	public static String smallWindowSize;
	public static String largeWindowSize;
	public String defaultImageComparisionFactor;
	public static String ASSET;
	public static String basePath;
	public static String imageLibPath;
	private int retries=0;
	private int maxRetries=5;
	public GeneratedTestData generatedTestData = new GeneratedTestData();
	public TestDataReader testDataReader;
	private static String sharedASSET;
	public static GlobalElementRepository globalElementRepository = new GlobalElementRepository();
	public String globalImageLib;
	public static String currentAgent;
	public static Boolean isMobile;


	public BaseDriver() throws Exception
	{
		setup();
		tearDown();
	}

	private void tearDown() {
		// Future : Used to bring system to base page as expected by next testcase.
	}

	public void setup() throws Exception
	{
		// TO be executed before start of every util initialization
		setGlobalProperties();
		instantiatingDriver();
		setGlobalLocators();

	}

	private void setGlobalLocators() {
		headerSearchTextBox="id=header-autosuggest-input";
		headerASSearchbutton=".autosuggest-search-btn";
	}

	public void instantiatingDriver() throws Exception {
		try {
			if (driver.toString().contains("null") || currentAgent.toString().contains("null")  || currentAgent != browserAgent)
			{
				instantiateDriver();
			}
		}
		catch (NullPointerException | SessionNotFoundException e) // Manage session Not found exception
		{
			instantiateDriver();
		}
	}
	private void instantiateDriver() throws Exception
	{
		UI = new WebDriver(browser,timeOutInSeconds);
		UI.triggerAlert("Initiating test..." + testcaseId);
		UI.acceptAlert();
		UI.maximizeWindow();
		UI.get(baseURL);
	}
	public void switchToCustomBrowser(String browserName,String browserAgent)
	{
		try {
			UICustom.triggerAlert("Initiating test...");
			UICustom.acceptAlert();
			UICustom.get(baseURL);
			UICustom.maximizeWindow();
		}
		catch (NullPointerException|SessionNotFoundException e) // Manage session Not found exception
		{
			System.out.println("Initiating session IPAD ");
			UICustom = new WebDriver(browserName,browserAgent,timeOutInSeconds);
			UICustom.triggerAlert("Initiating test...");
			UICustom.acceptAlert();
			UICustom.get(baseURL);
			UICustom.maximizeWindow();
		}
	}

	public  void setGlobalProperties() throws Exception {
		// read properties from config file that are applibale to framework
		globalProperties = new Properties();
		FileInputStream config = new FileInputStream("config/config.cfg");
		globalProperties.load(config);
		config.close();
		baseURL=globalProperties.getProperty("baseUrl");
		timeOutInSeconds=Integer.valueOf(globalProperties.getProperty("timeOutInSeconds"));
		browser = globalProperties.getProperty("browser","chrome");
		if (browserAgent == null)
		{
			browserAgent = globalProperties.getProperty("browserAgent", "default");
		}
		reportLocation = globalProperties.getProperty("reportLocation");
		screenshotLocation = globalProperties.getProperty("screenshotLocation");
		videoLocation = globalProperties.getProperty("videoLocation");
		scrollDownForHeader = Integer.parseInt(globalProperties.getProperty("scrollDownForHeader"));
		defaultImageComparisionFactor = globalProperties.getProperty("defaultImageComparisionFactor");
		smallWindowSize = "900,900";
		largeWindowSize = "1600,900";
		sharedASSET="sharedAssets";
		basePath= Paths.get(".").toAbsolutePath().normalize().toString();
		globalImageLib=basePath + "/testdata/globaltestdata/images/";
	}

	public static void close() {
		UI.quit();
	}
	public String  captureScreenshot() throws Exception {
		Screenshot screenshot = new Screenshot();
		{
			return screenshot.capture();
		}
	}
	public void captureScreenshot(String path) throws Exception {
		Screenshot screenshot = new Screenshot();
		{
			screenshot.capture(path);
		}
	}
	public void navigateTo(NavigationHelper.navigater navigateTo) throws Exception
	{
		NavigationHelper helper = new NavigationHelper();
		helper.navigateTo(navigateTo);
	}
	public void assertTrue(Boolean condition,String failureMessage) throws Exception{
		failureMessage = failureMessage + captureScreenshot();
		Assert.assertTrue(condition,failureMessage);
	}
	public void assertFalse(Boolean condition,String failureMessage) throws Exception{
		failureMessage = failureMessage + captureScreenshot();
		Assert.assertFalse(condition,failureMessage);
	}
	public void assertEquals(Boolean condition1,Boolean condition2,String failureMessage) throws Exception {
		failureMessage = failureMessage + captureScreenshot();
		Assert.assertEquals(condition1, condition2, failureMessage);
	}
	public void setImageLib() {
		imageLibPath=basePath + "/testdata/" + ASSET + "/images/";
		logger.debug("Set Image lib path to : " + imageLibPath);
	}
}
