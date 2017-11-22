package utils.selenium;

import org.openqa.selenium.WebDriver;
import utils.framework.BaseDriver;
import utils.framework.TestDataReader;
import utils.sikuli.SikuliHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static utils.selenium.NavigationHelper.navigater.HOMEPAGE;
import static utils.selenium.NavigationHelper.navigater.SIGNUP;

/**
 * Created by sourabh on 2/25/16.
 */
public class CartHelper extends BaseDriver {
	private final Properties suiteProperties;
	private final SikuliHelper screen;

	private String searchString;
	private String addToCartButton;
	private String cartPageHeader;
	private String item;
	private String cartImageIcon;
	private String cartIcon;
	private String productType;
	private String cartItemEBook;
	private String cartItemTBS;


	public CartHelper(Properties suiteDataProp, String testcaseId) throws Exception {
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
		cartPageHeader=globalElementRepository.GetSpanByMatchingText("Cart");
		cartIcon=".chg-cart";
		cartItemEBook=".label-ebook";
		cartItemTBS=".hwh-item";
	}

	private void getTestDataContext() throws Exception{
		testDataReader=new TestDataReader(suiteProperties,testcaseId);
		cartImageIcon = testDataReader.getData("cartImageIcon");
		productType = testDataReader.getData("productType");
	}

	public void verifyCart() throws Exception {
		if (cartImageIcon != null) {
			verifyCartIcon();
		}
		UI.click(cartIcon);
		UI.waitForElementPresent(cartPageHeader);
		if (productType != null)
		{
			List<String> productList = new ArrayList<String>(Arrays.asList(productType.split("::")));
			for (String product : productList)
			{
                if (product.equalsIgnoreCase("eTextbook"))
				{
					UI.waitForElementPresent(cartItemEBook);
				}
				else if (product.equalsIgnoreCase("TBS"))
				{
					UI.waitForElementPresent(cartItemTBS);
				}
			}
		}
	}

	private void verifyCartIcon() throws Exception {
		screen.verifyImagePresent(cartImageIcon);
	}

}
