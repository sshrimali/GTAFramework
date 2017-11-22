package utils.selenium;

import org.testng.Assert;
import utils.framework.BaseDriver;
import utils.framework.TestDataReader;
import utils.sikuli.SikuliHelper;

import java.util.Properties;

/**
 * Created by sourabh on 2/25/16.
 */
public class MobileHomePageUIHelper extends BaseDriver {
	private final Properties suiteProperties;
	private SikuliHelper screen;
	private int imageCount;
	private String imageFile;
	private String landingPage;
	private boolean menuOpen;


	public MobileHomePageUIHelper(Properties suiteDataProp, String testcaseId) throws Exception
	{
		this.suiteProperties = suiteDataProp;
		BaseDriver.testcaseId = testcaseId;
		setElementId();
		getTestDataContext();
		setImageLib();
		screen=new SikuliHelper();
	}

	void setElementId() {
		// Configure all locators
		String loginLink="//span[contains(text(),'Log In')]";

	}

	private void getTestDataContext() {
		testDataReader = new TestDataReader(suiteProperties, testcaseId);
		imageCount = testDataReader.getInt("imageCount");
		menuOpen = Boolean.valueOf(testDataReader.getData("menuOpen"));
		landingPage = testDataReader.getData("landingPage");
		imageFile = testDataReader.getData("imageFile");
	}


	public void verifyImageCount() throws Exception {
		UI.scrollToTop();
		int actualCount = screen.getCountOfImage(imageFile);
		Assert.assertEquals(actualCount,imageCount,"Actual was :  " + actualCount + ": Expected was : " + imageCount );
	}
	public void verifyImage() throws Exception
	{
		if (landingPage != null)
		{
			navigateTo(NavigationHelper.navigater.valueOf(landingPage));
		}
		if (menuOpen)
		{
			UI.click(".icon--hamburger");
		}
		screen.verifyImagePresent(imageFile);
	}
}
