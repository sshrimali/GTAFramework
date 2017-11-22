package utils.selenium;

import utils.framework.BaseDriver;
import utils.framework.TestDataReader;
import utils.sikuli.SikuliHelper;

import java.util.Properties;

/**
 * Created by sourabh on 2/25/16.
 */
public class BuyBackHelper extends BaseDriver {
	private final Properties suiteProperties;
	private final SikuliHelper screen;




	private String orderKey;
	private String verifyPrintReciept;
	private String verifyOrderExtension;
	private String orderDetailDownArrow;
	private String extensionLink;
	private String extensionDropDown;
	private String shippingAddressFName;
	private String shippingAddressLName;
	private String shippingAddressLine1;
	private String shippingAddressLine2;
	private String shippingAddressCity;
	private String shippingAddressState;
	private String shippingAddressZip;
	private String shippingAddressPhone;
	private String shippingAddressLNameLocator;
	private String shippingAddressLine1Locator;
	private String shippingAddressLine2Locator;
	private String shippingAddressCityLocator;
	private String shippingAddressStateLocator;
	private String shippingAddressZipLocator;
	private String shippingAddressPhoneLocator;
	private String shippingAddressFNameLocator;
	private String sellBooksSearchLocator;
	private String item;
	private String searchButton;
	private String sellBookButton;
	private String shippingLabeLocator;

	public BuyBackHelper(Properties suiteDataProp, String testcaseId) throws Exception {
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
		shippingAddressFNameLocator = ".address-fname";
		shippingAddressLNameLocator = ".address-lname";
		shippingAddressLine1Locator = ".address-line1";
		shippingAddressLine2Locator = ".address-line2";
		shippingAddressCityLocator = ".address-city";
		shippingAddressStateLocator = ".address-state";
		shippingAddressZipLocator = ".address-zip";
		shippingAddressPhoneLocator = ".address-phone";
		sellBooksSearchLocator = ".rent-buy-input-field";
		searchButton = ".buyback-search-btn";
		sellBookButton = ".cta-button";
		shippingLabeLocator = ".shipping-label";
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
		item = testDataReader.getData("item");

	}
	public void sellBooks() throws Exception {
		navigateTo(NavigationHelper.navigater.SELLBOOKS);
		UI.type(sellBooksSearchLocator,item);
		UI.click(searchButton);
		UI.click(sellBookButton);
		addShippingInfo();
		UI.click(sellBookButton);
		UI.waitForElementPresent(shippingLabeLocator);
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
}
