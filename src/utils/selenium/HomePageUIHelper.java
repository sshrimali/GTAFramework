package utils.selenium;

import org.sikuli.script.Pattern;
import org.testng.Assert;
import utils.framework.BaseDriver;
import utils.framework.GlobalElementRepository;

import utils.framework.TestDataReader;
import utils.sikuli.SikuliHelper;

import java.util.Properties;

/**
 * Created by sourabh on 2/25/16.
 */
public class HomePageUIHelper extends BaseDriver {
	private final Properties suiteProperties;

	Pattern signedOutHeaderWithoutCart = new Pattern();
	private String LogoHomePage;
	private String MenuHomePage;
	private String MenuHomePageScrolledUp;
	private String HPGurillaGlass;
	private String MenuHomePageScrolledDown;
	private SikuliHelper screen = new SikuliHelper();
	private int imageCount;
	private String imageFile;
	private String moreLink;


	public HomePageUIHelper(Properties suiteDataProp, String testcaseId) throws Exception
	{
		this.suiteProperties = suiteDataProp;
		BaseDriver.testcaseId = testcaseId;
		// read all test data
		//configure all element locators
		setElementId();
		setImageLib();
		getTestDataContext();
	}
	void setElementId() {
		// Configure all locators
		String loginLink="//span[contains(text(),'Log In')]";
	}

	private void getTestDataContext() {

		if (suiteProperties.containsKey(testcaseId + "_imageCount")) {
			imageCount = Integer.parseInt(suiteProperties.getProperty(testcaseId + "_imageCount"));
		}
		if (suiteProperties.containsKey(testcaseId + "_imageFile")) {
			imageFile = imageLibPath + suiteProperties.getProperty(testcaseId + "_imageFile");
		}
		testDataReader = new TestDataReader(suiteProperties,testcaseId);
		LogoHomePage = testDataReader.getData("LogoHomePage");
		MenuHomePage=testDataReader.getData( "MenuHomePage");
		MenuHomePageScrolledUp=testDataReader.getData( "MenuHomePageScrolledUp");
		HPGurillaGlass=testDataReader.getData( "HPGurillaGlass");
		MenuHomePageScrolledDown=testDataReader.getData( "MenuHomePageScrolledDown");
	}

	public void openHomePage() throws Exception {
		UI.get(baseURL);
	}
	public void verifySignedOutHomePageLookAndFeel() throws Exception {
		verifyHomePageLogo();
		verifyScrolledUpMenu();
		verifyGurillaGlass();
		UI.scrollDownBy(scrollDownForHeader);
		verifyScrolledDownMenu();
	}
	public void verifyImageCount() throws Exception {
		UI.scrollToTop();
		Thread.sleep(1000);
		int actualCount = screen.getCountOfImage(imageFile);
		Assert.assertEquals(actualCount,imageCount,"Actual was :  " + actualCount + ": Expected was : " + imageCount );
	}
	private void verifyScrolledDownMenu() throws Exception {
		screen.verifyImagePresent(MenuHomePageScrolledDown);
	}

	private void verifyGurillaGlass() throws Exception {
		screen.verifyImagePresent(HPGurillaGlass);
	}

	private void verifyScrolledUpMenu() throws Exception {
		screen.verifyImagePresent(MenuHomePageScrolledUp);
	}

	public void verifyHomePageLogo() throws Exception {
		UI.scrollToTop();
		screen.verifyImagePresent(LogoHomePage);
	}

	private void exceptionTest() throws Exception {
		UI.click("//sourabh");
	}

	public void verifyHomePageDropDownMenu() throws Exception {
		navigateTo(NavigationHelper.navigater.HOMEPAGE);
		UI.waitForElementPresent(GlobalElementRepository.NAVIGATION.moreLinkSignedOut);
		UI.click(GlobalElementRepository.NAVIGATION.moreLinkSignedOut);
		UI.waitForElementPresent(GlobalElementRepository.NAVIGATION.helpLink);
		UI.waitForElementPresent(GlobalElementRepository.NAVIGATION.createAccountLink);
		UI.waitForElementPresent(GlobalElementRepository.NAVIGATION.signInLink);
	}

	public void verifyResolution() throws Exception{
		verifyHomePageDropDownMenu();
		try {
			UI.resizeWindow("600,600");
			verifyHomePageDropDownMenu();
		}
		finally {
			UI.maximizeWindow();
		}

	}

	public void verifyHamburgerMenu() throws Exception {
		navigateTo(NavigationHelper.navigater.HOMEPAGE);
		UI.click(GlobalElementRepository.HomePage.hamburgerIcon);
		if (imageFile != null)
		{
			screen.verifyImagePresent(imageFile);
		}
	}
}
