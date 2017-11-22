package utils.selenium;

import utils.framework.BaseDriver;
import utils.framework.GlobalElementRepository;
import utils.framework.TestDataReader;
import utils.sikuli.SikuliHelper;

import java.util.Properties;

import static utils.selenium.NavigationHelper.navigater.SIGNUP;

/**
 * Created by sourabh on 2/25/16.
 */
public class AuthenticationHelper extends BaseDriver {
	private final Properties suiteProperties;
	private final SikuliHelper screen;

	private String searchString;
	private String firstResultSet;
	private String userProfileIcon;
	private String UserNameTextBox;
	private String userName;
	private String password;
	private String PasswordTextBox;
	private String createAccountButton;
	private String userType;
	private String ColegeStudentLocator;
	private String StudentLocator;
	private String universityLocator;
	private String universityName;
	private String yearInCollegeLocator;
	private String HSStudentLocator;
	private String yearInCollege;
	private String verifyPasswordImage;
	private String exitAfterPassword;
	private String verifyUserTypeAvailable;
	private String universityFirstResult;

	public AuthenticationHelper(Properties suiteDataProp, String testcaseId) throws Exception {
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
		userProfileIcon=".user-item";
		UserNameTextBox="input.txt-input.email";
		PasswordTextBox="input.txt-input.password";
		ColegeStudentLocator=".college";
		StudentLocator=".student";
		HSStudentLocator = "//label[@for='highschool']";
		universityLocator = "//div[@class='collegeSelect']//input[contains(@class,'autosuggest-input-field')]";
		universityFirstResult = "xpath=(//span[@class='result-header'])[1]";
		yearInCollegeLocator="//label[text()='" + yearInCollege +"']";
		createAccountButton="//button[text()='Create Account']";
	}

	private void getTestDataContext() throws Exception{
			testDataReader=new TestDataReader(suiteProperties,testcaseId);
			userName=testDataReader.getData("_userName");
			// Add dynamic string to data
			userName=System.currentTimeMillis() + "_" + userName;
			generatedTestData.writeData(testcaseId + "_userName",userName);
			password = testDataReader.getData("password");
			userType = testDataReader.getData("userType");
			yearInCollege = testDataReader.getData("yearInCollege");
			universityName = testDataReader.getData("universityName");
			verifyPasswordImage = testDataReader.getData("verifyPasswordImage");
			exitAfterPassword = testDataReader.getData("exitAfterPassword");
			verifyUserTypeAvailable = testDataReader.getData("verifyUserTypeAvailable");
	}
	public boolean isSignedIn() throws Exception
	{
		if(UI.isElementVisible(userProfileIcon)) {
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean isSignedOut() throws Exception
	{
		if(UI.isElementVisible(GlobalElementRepository.NAVIGATION.moreLinkSignedOut)) {
			return true;
		}
		else
		{
			return false;
		}
	}
	public void signUpUI() throws Exception
	{
		signOut();
		navigateTo(SIGNUP);
		UI.type(UserNameTextBox,userName);
		//Verify username accepted
		UI.type(PasswordTextBox,password);
		//verify passowrd requirement
		if (verifyPasswordImage != null) {
			verifyPassword();
			return;
		}
		if (verifyUserTypeAvailable != null)
		{
			screen.verifyImagePresent(verifyUserTypeAvailable);
			return;
		}
			selectUserType();
			UI.click(createAccountButton);
			waitForUserSignedIn();
	}

	private void verifyPassword() throws Exception {

		screen.verifyImagePresent(verifyPasswordImage);
	}

	private void selectUserType() throws Exception
	{
		//If college user
		if (userType.equalsIgnoreCase("college")) {
			UI.click(StudentLocator);
			// select college day
			UI.click(ColegeStudentLocator);
			UI.type(universityLocator,universityName);
			UI.click(universityFirstResult);
			UI.pressEnterKey(universityLocator);
			UI.click(yearInCollegeLocator);
		}
	}
	private void waitForUserSignedIn() throws Exception {
		UI.waitForElementPresent(userProfileIcon);
	}
	private void verifySignedIn() throws Exception {
			assertTrue(isSignedIn(),"Expected user to be signed in but found not signed in");
	}
	private void verifySignedOut() throws Exception {
		assertTrue(isSignedOut(),"Expected user to be signed out but found user signed in");
	}

	public void signIn() {
		//TODO : To be implemented later
	}

	public void signOut() throws Exception {
		if (isSignedIn()) {
			UI.waitForElementPresent(GlobalElementRepository.NAVIGATION.moreLinkSignedIn);
			UI.click(GlobalElementRepository.NAVIGATION.moreLinkSignedIn);
			UI.click(GlobalElementRepository.NAVIGATION.signOutLink);
			UI.waitForTextPresent("You've signed out, but before you go");
			verifySignedOut();
		}
	}
}
