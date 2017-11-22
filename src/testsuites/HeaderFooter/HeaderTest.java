package testsuites.HeaderFooter;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testsuites.DefaultTest;
import utils.framework.BaseDriver;
import utils.selenium.*;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *  Created by sshrimali on 8/24/16.
 */
public class HeaderTest extends DefaultTest{

    private static Properties headerTestData;

    static
    {
        try
        {

            headerTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/headerfooter/header/header.txt");
            headerTestData.load(userDataFis);
            userDataFis.close();
            logger.initialize("header");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public HeaderTest() throws Exception {
    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.ASSET="headerfooter/header";
    }
    @Test
    public void verifyHeaderSearchOpens_5873() throws Exception
    {
        SearchResultsUIHelper search = new SearchResultsUIHelper(headerTestData,"5873");
        search.searchCurrentPage();
    }
    @Test
    public void verifyHeaderSearchCloses_5874() throws Exception
    {
        SearchResultsUIHelper search = new SearchResultsUIHelper(headerTestData,"5874");
        search.searchCurrentPage();
    }
    @Test
    public void verifyHeader_5875() throws Exception
    {

        HomePageUIHelper helper = new HomePageUIHelper(headerTestData,"5875");
        helper.navigateTo(NavigationHelper.navigater.HOMEPAGE);
        helper.verifyHomePageLogo();
    }
    @Test
    public void verifyCartIconHP_5876() throws Exception
    {
        PDPUIHelper pdpHelper = new PDPUIHelper(headerTestData,"5876_01");
        pdpHelper.addToCart();
        CartHelper cartHelper = new CartHelper(headerTestData,"5876_02");
        cartHelper.verifyCart();
    }
    @Test
    public void verifyBooksMenu_5880() throws Exception
    {
        NavigationHelper helper = new NavigationHelper(headerTestData,"5880");
        helper.verifyMenuDropDown();
        helper.verifyNavigationFlow();
    }
    @Test
    public void verifyStudyMenu_5881() throws Exception
    {
        NavigationHelper helper = new NavigationHelper(headerTestData,"5881");
        helper.verifyMenuDropDown();
        helper.verifyNavigationFlow();
    }
    @Test
    public void verifyTutorsMenu_5882() throws Exception
    {
        NavigationHelper helper = new NavigationHelper(headerTestData,"5882");
        helper.verifyNavigationFlow();
    }
    @Test
    public void verifyTestPrepMenu_5883() throws Exception
    {
        NavigationHelper helper = new NavigationHelper(headerTestData,"5883");
        helper.verifyNavigationFlow();
    }
    @Test
    public void verifyInternshipsMenu_5884() throws Exception
    {
        NavigationHelper helper = new NavigationHelper(headerTestData,"5884");
        helper.verifyNavigationFlow();
    }
    @Test
    public void verifyScholarshipsMenu_5885() throws Exception
    {
        NavigationHelper helper = new NavigationHelper(headerTestData,"5885");
        helper.verifyNavigationFlow();
    }
}
