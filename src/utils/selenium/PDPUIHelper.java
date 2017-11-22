package utils.selenium;

import org.sikuli.script.Pattern;
import org.testng.Assert;
import utils.framework.BaseDriver;
import utils.framework.GlobalElementRepository;
import utils.framework.TestDataReader;
import utils.sikuli.SikuliHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by sourabh on 2/25/16.
 */
public class PDPUIHelper extends BaseDriver {
	private final Properties suiteProperties;

	Pattern signedOutHeaderWithoutCart = new Pattern();
	private String LogoHomePage;
	private String MenuHomePage;
	private String MenuHomePageScrolledUp;
	private String HPGurillaGlass;
	private String MenuHomePageScrolledDown;
	private SikuliHelper screen = new SikuliHelper();
	private String pdpIPAD;
	private String pdpDesktop;
	private Boolean compatible;
	private String coverImage;
	private String pricingState;
	private String pricingStateDropDown;
	private String item;
	private String cartPageHeader;
	private String bookFamilyURL;
	private String searchString;
	private String productType;
	private String tbsCheckBox;
	private String eTextbookSelector;


	public PDPUIHelper(Properties suiteDataProp, String testcaseId) throws Exception
	{
		this.suiteProperties = suiteDataProp;
		BaseDriver.testcaseId = testcaseId;
		// read all test data
		getTestDataContext();
		//configure all element locators
		setElementId();
		setImageLib();
	}

	public void setImageLib() {
		imageLibPath=basePath + "/testdata/pdp/images/";
		pdpIPAD=imageLibPath + "pdpIPAD.png";
		pdpDesktop=imageLibPath + "pdpDesktop.png";
	}
	void setElementId() {
		// Configure all locators
		pricingStateDropDown=".state-dropdown";
		cartPageHeader=globalElementRepository.GetSpanByMatchingText("Cart");
		bookFamilyURL="xpath=//div[contains(@class,'global-breadcrumb')]/a[last()]";
		tbsCheckBox="//label[@for='tbs-id']";
		eTextbookSelector=".test-ebooks-label";
	}

	private void getTestDataContext() {
		testDataReader=new TestDataReader(suiteProperties,testcaseId);
		searchString = testDataReader.getData(("searchString"));
		item = testDataReader.getData("item",searchString);
		coverImage=testDataReader.getData("coverImage");
		pricingState=testDataReader.getData("pricingState","California");
		productType=testDataReader.getData("productType");

	}

	public void navitageToPDP() throws Exception {
		SearchResultsUIHelper helper = new SearchResultsUIHelper(suiteProperties,testcaseId);
		helper.searchCurrentPage();
		helper.clickFirstResult();
	}
	public void verifyPDP() throws Exception
	{
		if (!coverImage.isEmpty())
		{
			screen.verifyImagePresent(coverImage);
		}
	}

	@Deprecated
	public void comparePDP() throws Exception {
		Thread.sleep(5000);
		String imageToVerify=UI.captureScreenshot("//div[@class='pricebox-col']",pdpDesktop);
		Thread.sleep(5000);
		this.UIArchive = UI;
		System.out.println("initiating Switching to IPAD");
		switchToCustomBrowser("Chrome","Apple iPad");
		this.UI=UICustom;
		try {
			UI.get(baseURL);
			UI.triggerAlert("This is IPAD");
			Thread.sleep(2000);
			UI.acceptAlert();
			Thread.sleep(5000);
			SearchResultsUIHelper helper = new SearchResultsUIHelper(suiteProperties, testcaseId);
			UI.scrollToTop();
			//UI.resizeWindow("750,1024");
			//UI.resizeWindow("1024,768");
			UI.type("css=#autosuggest-input","Math");
			UI.click("xpath=(//a[contains(@class,'autosuggest-search-btn')])[2]");
			helper.clickFirstResult();
			Thread.sleep(5000);
			//captureScreenshot(pdpIPAD);
			screen.verifyImagePresent(imageToVerify);
		}
		finally {
			System.out.println("Switching to Desktop");
			UI.close();
			this.UI=UIArchive;

		}
		compatible = screen.compareImages(pdpIPAD,pdpDesktop);
		System.out.println("Screens we found compatible : " + compatible);
		Assert.assertTrue(compatible,"we found images compatiblity to be : " + compatible);
	}
	public void selectShippingState() throws Exception
	{
			UI.select(pricingStateDropDown,pricingState);
			UI.waitForTextPresent("shipping to");
	}
	public void addToCart() throws Exception
	{
		SearchResultsUIHelper searchH = new SearchResultsUIHelper(suiteProperties,testcaseId);
		searchH.searchCurrentPage(item);
		searchH.clickFirstResult();
		UI.waitForElementPresent(GlobalElementRepository.PDP.addToCartButton);
		if (UI.isElementVisible(pricingStateDropDown))
		{
			selectShippingState();
		}
		if (productType != null)
		{
			selectProductType();
		}
		UI.click(GlobalElementRepository.PDP.addToCartButton);
		UI.waitForElementPresent(cartPageHeader);
	}

	private void selectProductType() throws Exception {
		// TODO
		List<String> productList = new ArrayList<String>(Arrays.asList(productType.split("::")));
		if (productList.contains("eTextbook"))
		{
			UI.click(eTextbookSelector);
		}
		if (productList.contains("TBS"))
		{
			UI.click(tbsCheckBox);
		}
	}

	public void verifyFamilyURL() throws Exception {
		UI.click(bookFamilyURL);
		UI.waitForTextPresent("Book Editions for");
		UI.waitForTextPresent(searchString);
	}
}
