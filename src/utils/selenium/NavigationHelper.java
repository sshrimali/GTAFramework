package utils.selenium;

import org.sikuli.script.Pattern;
import utils.framework.BaseDriver;
import utils.framework.TestDataReader;
import utils.sikuli.SikuliHelper;

import java.util.Properties;

import static utils.framework.GlobalElementRepository.*;
import static utils.selenium.NavigationHelper.navigater.HOMEPAGE;
import static utils.selenium.NavigationHelper.navigater.MYACCOUNTS;

/**
 * Created by sourabh on 2/25/16.
 */
public class NavigationHelper extends BaseDriver {
	private final Properties suiteProperties;

	Pattern signedOutHeaderWithoutCart = new Pattern();
	private String userMenu;
	private String myAccountsProfileLink;
	private String signUpLink;
	private String moreLink;
	private SikuliHelper screen = new SikuliHelper();
	private String menuItem;
	private String menuDropDownImage;
	private String stepsToNavigate;

	public NavigationHelper(Properties suiteDataProp, String testcaseId) throws Exception
	{
		this.suiteProperties = suiteDataProp;
		BaseDriver.testcaseId = testcaseId;
		// read all test data
		getTestDataContext();
		//configure all element locators
		setElementId();
		setImageLib();
	}
	public NavigationHelper() throws Exception
	{
		// read all test data
		//getTestDataContext();
		//configure all element locators
		setElementId();
		suiteProperties = null;
		setImageLib();
	}
	void setElementId() {
		// Configure all locators
		userMenu=".user-item";
		myAccountsProfileLink=".track-my-account";
		signUpLink=".track-signup";
		moreLink="div.more-item > div.header";
	}
	public enum navigater
	{
		HOMEPAGE,
		BOOKS,
		MYACCOUNTS,
		MYORDERS,
		SIGNIN,
		SIGNUP,
		FORGETPASSWORD,
		RENTBUYBOOKS,
		SELLBOOKS,
		STUDY,
		TBS,
		TESTPREP, TUTORS, INTERNSHIPS, SCHOLARSHIPS, QNA, BOOKSIHAVESOLD, CART,NOTIFICATION_PREF,
		COLLEGEBOOKS,CHEAPBOOKS,USEDBOOKS

	}
	private void getTestDataContext() {
		testDataReader = new TestDataReader(suiteProperties,testcaseId);
		menuItem = testDataReader.getData("menuItem");
		menuDropDownImage = testDataReader.getData("menuDropDownImage");
		stepsToNavigate=testDataReader.getData("stepsToNavigate");
	}
	public void navigateTo(navigater target) throws Exception {
		//TODO Implement smart logic, if already on required page, do not navigate again
		switch (target) {
			case MYACCOUNTS:
				navigateToMyAccounts();
				break;
			case MYORDERS:
				navigateToMyOrders();
				break;
			case SIGNUP:
				navigateToSignUp();
				break;
			case HOMEPAGE:
				navigateToHomePage();
				break;
			case BOOKS:
				navigateToBooks();
				break;
			case RENTBUYBOOKS:
				navigateToRentBuyBooks();
				break;
			case SELLBOOKS:
				navigateToSellBooks();
				break;
			case STUDY:
				navigateToStudy();
				break;
			case TBS:
				navigateToTBS();
				break;
			case QNA:
				navigateToQNA();
				break;
			case TESTPREP:
				navigateToTestPrep();
				break;
			case TUTORS:
				navigateToTutors();
				break;
			case INTERNSHIPS:
				navigateToInternships();
				break;
			case SCHOLARSHIPS:
				navigateToScholarships();
				break;
			case CART:
				navigateToCart();
				break;
			case BOOKSIHAVESOLD:
				navigateToBooksIveSold();
				break;
			case NOTIFICATION_PREF:
				navigateToNotificationPrefences();
				break;
			case COLLEGEBOOKS:
				navigateToCollegeBooks();
				break;
			case CHEAPBOOKS:
				navigateToCheapbooks();
				break;
			case USEDBOOKS:
				navigateToUsedBooks();
				break;
		}
	}

	private void navigateToNotificationPrefences() throws Exception {
		if (!UI.getCurrentUrl().contains("orders-books_i've_sold"))
		{
			navigateTo(MYACCOUNTS);
			UI.click("//li[@data-hash='my_info-notification_preferences']");
			verifyURL("my_info-notification_preferences");
		}
	}

	private void navigateToBooksIveSold() throws Exception {
		if (!UI.getCurrentUrl().contains("orders-books_i've_sold"))
		{
			navigateTo(MYACCOUNTS);
			UI.click("//li[@data-hash=\"orders-books_i've_sold\"]");
			verifyURL("orders-books_i've_sold");
		}
	}

	private void navigateToCart() throws Exception {
		if (!UI.getCurrentUrl().contains("shoppingcart"))
		{
			UI.click(CART.shoppingCart);
			verifyURL("/shoppingcart");

		}
	}

	private void navigateToScholarships() throws Exception{
		if (!UI.getCurrentUrl().contains("/scholarships")) {
			UI.click(NAVIGATION.scholarshipsLink);
			verifyURL("/scholarships");		}
	}

	private void navigateToInternships()  throws Exception{
		if (!UI.getCurrentUrl().contains("/internships")) {
			UI.click(NAVIGATION.internshipsLink);
			verifyURL("/internships");		}
	}

	private void navigateToTutors()  throws Exception{
		if (!UI.getCurrentUrl().contains("/tutors")) {
			UI.click(NAVIGATION.tutorsLink);
			verifyURL("/tutors");		}
	}

	private void navigateToTestPrep()  throws Exception{
		if (!UI.getCurrentUrl().contains("/test-prep")) {
			if (isMobile)
			{
				UI.get("/test-prep");
			}
			else {
				UI.click(NAVIGATION.testprepLink);
			}
			verifyURL("/test-prep");		}
	}

	private void navigateToQNA() throws Exception {
		if (!UI.getCurrentUrl().contains("/qa")) {
			UI.mouseHover(NAVIGATION.studyLink);
			UI.click(NAVIGATION.qnaLink);
			verifyURL("/qa");		}
	}

	private void navigateToTBS() throws Exception {
		if (!UI.getCurrentUrl().contains("/tbs")) {
			UI.mouseHover(NAVIGATION.studyLink);
			UI.click(NAVIGATION.tbsLink);
			verifyURL("/tbs");
		}
	}

	private void navigateToStudy() throws Exception {
		if (!UI.getCurrentUrl().contains("/study")) {
			if (isMobile)
			{
				UI.get("/study");
			}
			else {
				UI.click(NAVIGATION.studyLink);
			}
			verifyURL("/study");
		}
	}
	private void verifyURL(String content) throws Exception
	{
		String currentUrl = UI.getCurrentUrl();
		assertTrue(currentUrl.contains(content),"Current URL : " + currentUrl + " does not contain " + content);
	}

	private void navigateToHomePage() {
		UI.get(baseURL);
	}
	private void navigateToBooks() throws Exception
	{
		if (!UI.getCurrentUrl().contains("/books")) {
			if (isMobile)
			{
				UI.get("/books");
			}
			else {
				UI.click(NAVIGATION.booksLink);
			}
			verifyURL("/books");
		}
	}
	private void navigateToRentBuyBooks() throws Exception
	{
		if (!UI.getCurrentUrl().contains("/books")) {
			if (isMobile)
			{
				UI.get("/books");
			}
			else {
				UI.mouseHover(NAVIGATION.booksLink);
				UI.click(NAVIGATION.rentBuyBooksLink);
			}
			verifyURL("/books");		}
	}
	private void navigateToMyOrders() throws Exception
	{
		if (!UI.getCurrentUrl().contains("/my/orders")) {
			UI.get("/my/orders");
		}
	}
	private void navigateToCheapbooks() throws Exception
	{
		if (!UI.getCurrentUrl().contains("/cheap-textbooks")) {
			UI.get("/cheap-textbooks");
		}
	}
	private void navigateToUsedBooks() throws Exception
	{
		if (!UI.getCurrentUrl().contains("/used-textbooks")) {
			UI.get("/used-textbooks");
		}
	}
	private void navigateToCollegeBooks() throws Exception
	{
		if (!UI.getCurrentUrl().contains("/college-textbooks")) {
			UI.get("/college-textbooks");
		}
	}
	private void navigateToSellBooks() throws Exception
	{
		if (!UI.getCurrentUrl().contains("/sell-textbooks")) {
			if (isMobile)
			{
				UI.get("/sell-textbooks");
			}
			else {
				UI.mouseHover(NAVIGATION.booksLink);
				UI.click(NAVIGATION.sellBooksLink);
			}
			verifyURL("/sell-textbooks");		}
	}

	private void navigateToSignUp() throws Exception {
		if (UI.getCurrentUrl().contains("/auth?"))
		{
			return; // Already on Sign UP/In Page
		}
		if (isMobile)
		{
			UI.get("/auth?action=signup");
		}
		else {
			if (!UI.isElementVisible(moreLink) || UI.getCurrentUrl().contains("/signout?")) {
				navigateTo(HOMEPAGE);
			}
			UI.click(moreLink);
			UI.click(signUpLink);
		}
		UI.waitForTextPresent("Create account");
	}

	private void navigateToMyAccounts() throws Exception {
		if (!UI.isElementVisible(userMenu))
		{
			navigateTo(HOMEPAGE);
		}
		UI.click(userMenu);
		UI.click(myAccountsProfileLink);
		UI.waitForTextPresent("Account Overview");
	}
	public void verifyMenuDropDown() throws Exception
	{
		UI.mouseOver(globalElementRepository.GetLinkByText(menuItem));
		screen.verifyImagePresent(menuDropDownImage);
	}
	public void verifyNavigationFlow() throws Exception
	{
		navigater target;
		String[] page = stepsToNavigate.split("->");
		for (int iterator=0;iterator < page.length ; iterator++)
		{
			target = navigater.valueOf(page[iterator]);
			navigateTo(target);
		}
	}
}
