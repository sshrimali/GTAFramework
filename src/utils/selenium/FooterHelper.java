package utils.selenium;

import utils.framework.BaseDriver;
import utils.framework.TestDataReader;
import utils.sikuli.SikuliHelper;

import java.util.Properties;

/**
 * Created by sourabh on 2/25/16.
 */
public class FooterHelper extends BaseDriver {
	private final Properties suiteProperties;
	private final SikuliHelper screen;

	private String searchString;
	private String copyRightText;


	public FooterHelper(Properties suiteDataProp, String testcaseId) throws Exception {
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
		//firstResultSet = "//li[@class='item-result'][1]";
	}

	private void getTestDataContext() {
		testDataReader=new TestDataReader(suiteProperties,testcaseId);
		copyRightText = testDataReader.getData("copyRightText");

	}
	public void verifyFooter() throws Exception
	{
		if (copyRightText != null)
		{
			UI.waitForTextPresent(copyRightText);
		}
	}
}
