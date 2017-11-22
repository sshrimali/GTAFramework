package utils.selenium;

import utils.framework.BaseDriver;
import utils.framework.GeneratedTestData;
import utils.framework.GlobalElementRepository;
import utils.framework.TestDataReader;
import utils.sikuli.SikuliHelper;
import utils.selenium.NavigationHelper.*;

import java.util.Properties;

import static utils.selenium.NavigationHelper.navigater.*;

/**
 * Created by sourabh on 2/25/16.
 */
public class OrdersHelper extends BaseDriver {
	private final Properties suiteProperties;
	private final SikuliHelper screen;

	private String searchString;
	private String firstResultSet;
	private String ChangePasswordPage;
	private String newPasswordTextBox;
	private String oldPasswordTextBox;
	private String oldPassword;
	private String newPassword;
	private String messageExpected;
	private String passwordChangeImage;
	private String savePasswordButton;
	private String myAccountsStudyLink;
	private String csNonMemberMyAccountsLandingImage;
	private String buttonContinue;
	private String buttonSubscribe;
	private String imageComparisionFactor;
	private String tutorsNonMemberMyAccountsLandingImage;
	private String myAccountsTutorsLink;
	private String shippingAddressFName;
	private String shippingAddressLName;
	private String shippingAddressLine1;
	private String shippingAddressLine2;
	private String shippingAddressCity;
	private String shippingAddressState;
	private String shippingAddressZip;
	private String shippingAddressPhone;
	private String paymentInfoSameAsShipping;
	private String paymentInfoName;
	private String paymentInfoCardNumber;
	private String paymentInfoCVV;
	private String shippingAddressLNameLocator;
	private String shippingAddressLine1Locator;
	private String shippingAddressLine2Locator;
	private String shippingAddressCityLocator;
	private String shippingAddressStateLocator;
	private String shippingAddressZipLocator;
	private String shippingAddressPhoneLocator;
	private String paymentInfoSameAsShippingLocator;
	private String paymentInfoNameLocator;
	private String paymentInfoCardNumberLocator;
	private String paymentInfoCVVLocator;
	private String shippingAddressFNameLocator;
	private String paymentInfoExpiryMonthLocator;
	private String paymentInfoExpiryYearLocator;
	private String paymentInfoMonthExpiry;
	private String paymentInfoYearExpiry;
	private String noThanks;
	private String orderKey;
	private String orderKeyLocator;
	private String tutorsPromoContinueButton;
	private static boolean containsTutorsGift;

	public OrdersHelper(Properties suiteDataProp, String testcaseId) throws Exception {
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

		shippingAddressFNameLocator = ".address-fname";
		shippingAddressLNameLocator = ".address-lname";
		shippingAddressLine1Locator = ".address-line1";
		shippingAddressLine2Locator = ".address-line2";
		shippingAddressCityLocator = ".address-city";
		shippingAddressStateLocator = ".address-state";
		shippingAddressZipLocator = ".address-zip";
		shippingAddressPhoneLocator = ".address-phone";
		paymentInfoSameAsShippingLocator = ".address-title > label.form-checkbox"; //TODO : PickUp : Initialize locators
		paymentInfoNameLocator = ".test-card-name";
		paymentInfoCardNumberLocator = ".test-card-number";
		paymentInfoExpiryMonthLocator = ".exp-month";
		paymentInfoExpiryYearLocator = ".exp-year";
		paymentInfoCVVLocator = ".test-card-code";
		noThanks = "css=button.thank-you.btn-secondary-lg";
		orderKeyLocator = "css=.order-key";
		tutorsPromoContinueButton="id=featurevertical-button";
	}

	private void getTestDataContext() {
		testDataReader=new TestDataReader(suiteProperties,testcaseId);
		shippingAddressFName = testDataReader.getData("shippingAddressFName");
		shippingAddressLName = testDataReader.getData("shippingAddressLName");
		shippingAddressLine1 = testDataReader.getData("shippingAddressLine1");
		shippingAddressLine2 = testDataReader.getData("shippingAddressLine2");
		shippingAddressCity = testDataReader.getData("shippingAddressCity");
		shippingAddressState = testDataReader.getData("shippingAddressState");
		shippingAddressZip = testDataReader.getData("shippingAddressZip");
		shippingAddressPhone = testDataReader.getData("shippingAddressPhone");
		paymentInfoSameAsShipping = testDataReader.getData("paymentInfoSameAsShipping");
		paymentInfoName = testDataReader.getData("paymentInfoName");
		paymentInfoCardNumber = testDataReader.getData("paymentInfoCardNumber");
		paymentInfoMonthExpiry = testDataReader.getData("paymentInfoMonthExpiry");
		paymentInfoYearExpiry = testDataReader.getData("paymentInfoYearExpiry");
		paymentInfoCVV = testDataReader.getData("paymentInfoCVV");
	}
	public void placeOrder() throws Exception
	{
		navigateTo(CART);
		if (UI.isElementVisible(".tutors-attach"))
		{
			containsTutorsGift = true;
		}
		UI.click(GlobalElementRepository.CART.buttonCheckout);
		handelGuidedSolutions(false);
		addShippingInfo();
		addPaymentInfo();
		UI.click(GlobalElementRepository.CHECKOUT.buttonContinue);
		UI.click(GlobalElementRepository.CHECKOUT.placeOrder);
		if (containsTutorsGift)
		{
			UI.click(tutorsPromoContinueButton);
			UI.waitForElementPresent(orderKeyLocator);
		}
		orderKey = UI.getText(orderKeyLocator);
		generatedTestData.writeData(testcaseId + "_orderKey",orderKey);
	}

	private void handelGuidedSolutions(boolean addSolutions) throws Exception {
		if (UI.getCurrentUrl().contains("guidedsolutions"))
		{
			if (addSolutions == false)
			UI.click(noThanks);
		}
	}

	private void addShippingInfo() throws Exception {
		UI.type(shippingAddressFNameLocator, shippingAddressFName);
		UI.type(shippingAddressLNameLocator, shippingAddressLName);
		UI.type(shippingAddressLine1Locator, shippingAddressLine1);
		UI.type(shippingAddressLine2Locator, shippingAddressLine2);
		UI.type(shippingAddressCityLocator, shippingAddressCity);
		UI.select(shippingAddressStateLocator, shippingAddressState);
		UI.type(shippingAddressZipLocator, shippingAddressZip);
		UI.type(shippingAddressPhoneLocator, shippingAddressPhone);
	}

	private void addPaymentInfo() throws Exception {
		UI.waitForTextPresent("Checkout");
		if (paymentInfoSameAsShipping.equalsIgnoreCase("true")) {
			UI.click(paymentInfoSameAsShippingLocator);
		}
		UI.type(paymentInfoNameLocator, paymentInfoName);
		UI.type(paymentInfoCardNumberLocator, paymentInfoCardNumber);
		UI.select(paymentInfoExpiryMonthLocator, paymentInfoMonthExpiry);
		UI.select(paymentInfoExpiryYearLocator, paymentInfoYearExpiry);
		UI.type(paymentInfoCVVLocator, paymentInfoCVV);

	}
}
