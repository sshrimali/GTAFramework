package utils.selenium;

import utils.framework.BaseDriver;
import utils.framework.TestDataReader;
import utils.sikuli.SikuliHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by sourabh on 2/25/16.
 */
public class AdobeHelper extends BaseDriver {
	private final Properties suiteProperties;
	private final SikuliHelper screen;

	private String searchString;
	private String addToCartButton;
	private String cartPageHeader;
	private String item;
	private String cartImageIcon;
	private String cartIcon;
	private String productType;


	public AdobeHelper(Properties suiteDataProp, String testcaseId) throws Exception {
		this.suiteProperties = suiteDataProp;
		BaseDriver.testcaseId = testcaseId;
		// read all test data
		getTestDataContext();
		//configure all element locators
		setElementId();
		setImageLib();
		screen=new SikuliHelper();
	}
	public AdobeHelper() throws Exception {
		// read all test data
		getTestDataContext();
		//configure all element locators
		setElementId();
		suiteProperties = null;
		screen = null;
	}

	void setElementId() {
		// Configure all locators
	}

	private void getTestDataContext() throws Exception{
			//testDataReader=new TestDataReader(suiteProperties,testcaseId);
	}

	public void getDigitalDataObject() throws Exception {
		//digitalData.cart.items[1].productInfo.productFormat
		String digitalData = UI.executeScript("JSON.parse(digitalData);");
		System.out.println("response is : " + digitalData);
	}

}
