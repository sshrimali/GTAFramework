package utils.selenium;

import utils.framework.BaseDriver;
import utils.framework.TestDataReader;
import utils.sikuli.SikuliHelper;

import java.util.Properties;

/**
 * Created by sourabh on 2/25/16.
 */
public class MyOrdersHelper extends BaseDriver {
	private final Properties suiteProperties;
	private final SikuliHelper screen;




	private String orderKey;
	private String verifyPrintReciept;
	private String verifyOrderExtension;
	private String orderDetailDownArrow;
	private String extensionLink;
	private String extensionDropDown;

	public MyOrdersHelper(Properties suiteDataProp, String testcaseId) throws Exception {
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
		orderDetailDownArrow=".ddb-arrow";
		extensionLink="//span[text()='Extend/Purchase']";
		extensionDropDown=".chzn-single";
	}

	private void getTestDataContext() {
		testDataReader=new TestDataReader(suiteProperties,testcaseId);
		orderKey=testDataReader.getData("orderKey");
		verifyPrintReciept = testDataReader.getData("verifyPrintReciept");
		verifyOrderExtension = testDataReader.getData("verifyOrderExtension");
	}
	public void verifyMyOrder() throws Exception {
		navigateTo(NavigationHelper.navigater.MYORDERS);
		if (verifyPrintReciept != null)
		{
			UI.waitForElementPresent("//div[@data-order-key='" + orderKey + "']//span[text()='Print order Receipt']");
		}
		if (verifyOrderExtension != null)
		{
			UI.click(orderDetailDownArrow);
			UI.click(extensionLink);
			UI.waitForElementPresent(extensionDropDown);
		}

	}
}
