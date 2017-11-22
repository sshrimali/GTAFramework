package utils.selenium;

import utils.framework.BaseDriver;
import utils.framework.GlobalElementRepository;
import utils.framework.TestDataReader;
import utils.sikuli.SikuliHelper;

import java.util.Properties;

/**
 * Created by sourabh on 2/25/16.
 */
public class MyAccountsHelper extends BaseDriver {
	private final Properties suiteProperties;
	private final SikuliHelper screen;

	private String searchString;
	private String firstResultSet;
	private String ChangePasswordPage;
	private String newPasswordTextBox;
	private String oldPasswordTextBox;
	private String oldPassword;
	private String newPassword;
	private String passwordChangeImage;
	private String savePasswordButton;
	private String myAccountsStudyLink;
	private String csNonMemberMyAccountsLandingImage;
	private String buttonContinue;
	private String buttonSubscribe;
	private String imageComparisionFactor;
	private String tutorsNonMemberMyAccountsLandingImage;
	private String myAccountsTutorsLink;
	private String orderIdLocator;
	private String updatePref;
	private String updatePrefUnsubCheckBox;

	public MyAccountsHelper(Properties suiteDataProp, String testcaseId) throws Exception {
		this.suiteProperties = suiteDataProp;
		BaseDriver.testcaseId = testcaseId;
		// read all test data
		getTestDataContext();
		//configure all element locators
		setElementId();
		setImageLib();
		screen=new SikuliHelper();
	}


	void setElementId() {
		// Configure all locators
		firstResultSet = "//li[@class='item-result'][1]";
		ChangePasswordPage="//span[text()='Change Password']";
		oldPasswordTextBox=".old-password";
		newPasswordTextBox = ".new-password";
		savePasswordButton = ".save-password";
		myAccountsStudyLink="//span[text()=' Study']";
		myAccountsTutorsLink="//span[text()=' Tutors']";

		buttonContinue=globalElementRepository.GetButtonLocatorByLabel("Continue");
		buttonSubscribe=globalElementRepository.GetButtonLocatorByLabel("Subscribe");
		orderIdLocator = ".order-id";
		updatePrefUnsubCheckBox= "//label[@for='unsubscribe']";


	}

	private void getTestDataContext() {
		testDataReader=new TestDataReader(suiteProperties,testcaseId);
		oldPassword = testDataReader.getData("oldPassword");
		newPassword = testDataReader.getData("newPassword");
		passwordChangeImage=testDataReader.getData("passwordChangeImage");
		csNonMemberMyAccountsLandingImage =testDataReader.getData("csNonMemberMyAccountsLandingImage");
		imageComparisionFactor=testDataReader.getData("imageComparisionFactor",defaultImageComparisionFactor);
		tutorsNonMemberMyAccountsLandingImage=testDataReader.getData("tutorsNonMemberMyAccountsLandingImage");
		updatePref=testDataReader.getData("updatePref");
	}
	public void searchCurrentPage() throws Exception
	{
		UI.scrollDownBy(scrollDownForHeader);
		//UI.click(".overlay");
		Thread.sleep(3000);
		UI.click(".hidden-overlay");
		Thread.sleep(3000);
		UI.type(headerSearchTextBox,searchString);
		UI.click(headerASSearchbutton);
	}
	public void clickFirstResult() throws Exception
	{
		Thread.sleep(10000);
		UI.click(firstResultSet);
	}

	public void verifySERPAdsPlacement() throws Exception {
		// TODO Verify ads positioning and search results visiblity in maximized window
		setElementId();
		UI.resizeWindow(largeWindowSize);

	}

	public void changePassword() throws Exception {
		navigateTo(NavigationHelper.navigater.MYACCOUNTS);
		UI.click(ChangePasswordPage);
		UI.type(oldPasswordTextBox,oldPassword);
		UI.type(newPasswordTextBox,newPassword);
		UI.click(savePasswordButton); // To remove focus
		if (passwordChangeImage != null)
		{
			screen.verifyImagePresent(passwordChangeImage);
		}
	}
	public void subscribeToStudy() throws Exception
	{
		navigateTo(NavigationHelper.navigater.MYACCOUNTS);
		UI.click(myAccountsStudyLink);
		if (csNonMemberMyAccountsLandingImage != null)
		{
			screen.verifyImagePresent(csNonMemberMyAccountsLandingImage,imageComparisionFactor);
		}
		UI.click(buttonSubscribe);
		UI.waitForElementPresent(buttonContinue);
		UI.click(buttonContinue);
		UI.waitForTextPresent("Checkout");
	}
	public void subscribeToTutors() throws Exception
	{
		navigateTo(NavigationHelper.navigater.MYACCOUNTS);
		UI.click(myAccountsTutorsLink);
		if (tutorsNonMemberMyAccountsLandingImage != null)
		{
			screen.verifyImagePresent(tutorsNonMemberMyAccountsLandingImage,imageComparisionFactor);
		}
		UI.click(buttonSubscribe);
	}

	public void verifyBooksIveSold() throws Exception {
		navigateTo(NavigationHelper.navigater.BOOKSIHAVESOLD);
		UI.waitForElementPresent(orderIdLocator);
	}

    public void updateNotificationPreferance() throws Exception {
		navigateTo(NavigationHelper.navigater.NOTIFICATION_PREF);
		if (updatePref == "unsubscribe") {
			UI.click(updatePrefUnsubCheckBox);
		}
		UI.click(GlobalElementRepository.buttonSave);
		UI.clickIfPresent(GlobalElementRepository.buttonOK);
		UI.waitForTextPresent("Your preferences have been updated");
    }
}
