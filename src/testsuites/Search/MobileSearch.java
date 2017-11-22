package testsuites.Search;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testsuites.DefaultTest;
import utils.framework.BaseDriver;
import utils.selenium.SearchResultsUIHelper;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *  Created by sshrimali on 8/24/16.
 */
public class MobileSearch extends DefaultTest{
    private static Properties mobileSearchTestData;

    static
    {
        try
        {
            mobileSearchTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/search/mobilesearch/mobilesearch.txt");
            mobileSearchTestData.load(userDataFis);
            userDataFis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //Initialize Logger
        logger.initialize("search");
    }

    public MobileSearch() throws Exception {
    }
    @BeforeClass
    public void initialize() throws Exception
    {
            BaseDriver.browserAgent="Apple iPhone 6";
            BaseDriver.ASSET="search/mobilesearch";
    }
    @Test
    public void searchSOHP_106875() throws Exception {
        SearchResultsUIHelper helper = new SearchResultsUIHelper(mobileSearchTestData,"106875");
        helper.searchGurillaGlass();
    }
    @Test
    public void searchSudyPage_106876() throws Exception {
        SearchResultsUIHelper helper = new SearchResultsUIHelper(mobileSearchTestData,"106876");
        helper.searchGurillaGlass();
    }

    @Test
    public void searchBooksPages_106877() throws Exception {
        SearchResultsUIHelper helper = new SearchResultsUIHelper(mobileSearchTestData,"106877_1");
        helper.searchGurillaGlass();
        helper = new SearchResultsUIHelper(mobileSearchTestData,"106877_2");
        helper.searchGurillaGlass();
        helper = new SearchResultsUIHelper(mobileSearchTestData,"106877_3");
        helper.searchGurillaGlass();
        helper = new SearchResultsUIHelper(mobileSearchTestData,"106877_4");
        helper.searchGurillaGlass();
        helper = new SearchResultsUIHelper(mobileSearchTestData,"106877_5");
        helper.searchGurillaGlass();
    }

}
