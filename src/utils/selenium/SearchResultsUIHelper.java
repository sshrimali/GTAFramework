package utils.selenium;

import org.apache.log4j.Priority;
import org.sikuli.script.Pattern;
import org.testng.Reporter;
import utils.framework.BaseDriver;
import utils.framework.GlobalElementRepository;
import utils.framework.Logger;
import utils.framework.TestDataReader;
import utils.sikuli.SikuliHelper;

import java.util.Properties;

import static utils.framework.Logger.logger;

/**
 * Created by sourabh on 2/25/16.
 */
public class SearchResultsUIHelper extends BaseDriver {
	private final Properties suiteProperties;

	Pattern signedOutHeaderWithoutCart = new Pattern();
	private String gapBetweenAdsBooksIcon;
	private String gapBetweenAdsResultSet;
	private String secondResultSet;
	private SikuliHelper screen;
	private String firstResultSet;
	private String searchString;
	private String verifySearchField;
	private String searchFieldExpandImage;
	private String searchFieldClosed;
	private String searchFieldClosedImage;
	private String searchFieldCloseIcon;
	private Boolean verifyOnPDP;
	private String imageFile;
	private String booksFilter;
	private String activePage2;
	private String currentPage2;
	private String paginationLocator;
	private String searchStringLocator;
	private String searchFilterLocator;
	private String searchVertical;
	private String verifyNavigation;
	private String firstTutorsResult;
	private String landingPage;
	private String gurillaGlassSearchTextbox;
	private String viewAllResultsLink;
	private String mobileSearchTextboxLocator;
	private String mobileSearchTextboxInput;

	public SearchResultsUIHelper(Properties suiteDataProp, String testcaseId) throws Exception {
		this.suiteProperties = suiteDataProp;
		BaseDriver.testcaseId = testcaseId;
		// read all test data
		getTestDataContext();
		//configure all element locators
		setElementId();
		setImageLib();
		screen=new SikuliHelper();
	}

	public void setImageLib() {
		imageLibPath=basePath + "/testdata/" + BaseDriver.ASSET + "/images/";
		gapBetweenAdsBooksIcon=imageLibPath + "gapBetweenAdsBooksIcon" + ".png";
		gapBetweenAdsResultSet=imageLibPath + "gapBetweenAdsResultSet" + ".png";
	}

	void setElementId() {
		// Configure all locators
		secondResultSet = "//li[@class='item-result'][2]";
		firstResultSet = "//li[@class='item-result'][1]";
		firstTutorsResult = "xpath=(//div[contains(@class,'tutor-card')]/div/a)[1]";
		booksFilter = ".filter-books";
		searchFilterLocator = ".filter-VERTICAL";
		activePage2 = "//a[@href=2]";
		currentPage2 = "//span[@class='current'][text()=2]";
		paginationLocator = ".simple-pagination";
		searchStringLocator = "//span[@class='query'][contains(text(),'searchString')]";
		gurillaGlassSearchTextbox = "//*[contains(@placeholder,'ISBN, title')]";
		viewAllResultsLink = ".view-all-link";
		mobileSearchTextboxLocator = "//*[contains(@placeholder,'title, author or ISBN')]";
		mobileSearchTextboxInput = "//input[@placeholder='Search by title, author or ISBN']";

	}

	private void getTestDataContext() {
		testDataReader=new TestDataReader(suiteProperties,testcaseId);
		searchString = testDataReader.getData("searchString");
		searchFieldExpandImage = testDataReader.getData("searchFieldExpandImage");
		searchFieldClosed = testDataReader.getData("searchFieldClosed");
		searchFieldClosedImage = testDataReader.getData("searchFieldClosedImage");
		verifyOnPDP = Boolean.valueOf(testDataReader.getData("verifyOnPdp"));
		imageFile = testDataReader.getData("imageFile");
		searchVertical = testDataReader.getData("searchVertical");
		verifyNavigation = testDataReader.getData("verifyNavigation");
		landingPage = testDataReader.getData("landingPage");
	}
	public void searchGurillaGlass() throws Exception{
		if (landingPage.equalsIgnoreCase(null)) {
			landingPage = "HOMEPAGE";
		}
		navigateTo(NavigationHelper.navigater.valueOf(landingPage));
		if (isMobile)
		{
			UI.click(mobileSearchTextboxLocator);
			UI.type(mobileSearchTextboxInput,searchString);
			UI.waitForElementPresent(firstResultSet);
		}
		else {

			UI.type(gurillaGlassSearchTextbox, searchString);
			UI.waitForElementPresent(viewAllResultsLink);
			if (imageFile != null) {
				screen.verifyImagePresent(imageFile);
			}
		}
	}
	public void searchCurrentPage() throws Exception
	{
			UI.scrollDownBy(scrollDownForHeader);
			UI.click(".hidden-overlay");
			if (searchFieldExpandImage != null) {
				screen.verifyImagePresent(searchFieldExpandImage);
				return;
			}
			if (searchFieldClosed != null) {
				UI.click(GlobalElementRepository.NAVIGATION.closeIcon);
				UI.waitForElementPresent(GlobalElementRepository.NAVIGATION.moreLinkSignedOut);
				screen.verifyImagePresent(searchFieldClosedImage);
				return;
			}
			UI.type(headerSearchTextBox, searchString);
	}
	public void searchCurrentPage(String searchString) throws Exception
	{
		this.searchString=searchString;
		searchCurrentPage();
	}
	public void clickFirstResult() throws Exception
	{
		UI.click(firstResultSet);
	}
	public void verifySearchResults() throws Exception
	{
		UI.waitForElementPresent(searchStringLocator.replace("searchString",searchString));
		if (verifyNavigation != null)
		{
			if (verifyNavigation.contains("tutor"))
			{
				firstResultSet = firstTutorsResult;
			}
			UI.click(firstResultSet);
			UI.waitForTextPresent(verifyNavigation);
		}
	}
	public void serpSearch() throws Exception
	{
		searchCurrentPage();
		UI.pressEnterKey(headerSearchTextBox);
		if (verifyOnPDP)
		{
			UI.waitForElementPresent(GlobalElementRepository.PDP.priceBoxContainer);
			UI.waitForTextPresent(searchString);
		}
		else {
			UI.waitForElementPresent(".filter-all-matches");
		}
	}
	public void verifySERPAdsPlacement() throws Exception {
		// TODO Verify ads positioning and search results visiblity in maximized window
		setElementId();
		UI.resizeWindow(largeWindowSize);
		screen.verifyImagePresent(gapBetweenAdsBooksIcon);
		UI.mouseHover(secondResultSet);
		screen.verifyImagePresent(gapBetweenAdsResultSet);
		try {
			UI.resizeWindow(smallWindowSize);
			screen.verifyImagePresent(gapBetweenAdsBooksIcon,"0.85");
			UI.mouseHover(secondResultSet);
			screen.verifyImagePresent(gapBetweenAdsResultSet,"0.85");
		}
		finally {
			UI.maximizeWindow();
		}
	}

	public void verifyPagination() throws Exception {
		UI.click(booksFilter);
		UI.click(activePage2);
		UI.waitForElementPresent(currentPage2);
		UI.waitForElementPresent(paginationLocator);
		UI.moveToElement(paginationLocator);
		if (imageFile != null) {
			screen.verifyImagePresent(imageFile,".50");
		}
	}

	public void switchSerpVertical() throws Exception{
		UI.click(searchFilterLocator.replace("VERTICAL",searchVertical));
		UI.waitForElementPresent(searchFilterLocator.replace("VERTICAL",searchVertical + ".active"));
	}
}
