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
public class GurillaGlassSearch extends DefaultTest{
    private static Properties serpHPTestData;

    static
    {
        try
        {
            serpHPTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/search/gurillaglasssearch/gurillaglasssearch.txt");
            serpHPTestData.load(userDataFis);
            userDataFis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //Initialize Logger
        logger.initialize("search");
    }

    public GurillaGlassSearch() throws Exception {
    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.ASSET="search/gurillaglasssearch/";
    }
    @Test
    public void searchUsedBooks_62628() throws Exception {
        SearchResultsUIHelper helper = new SearchResultsUIHelper(serpHPTestData,"62628");
        helper.searchGurillaGlass();
    }
    @Test
    public void searchCheapBooks_62629() throws Exception {
        SearchResultsUIHelper helper = new SearchResultsUIHelper(serpHPTestData,"62629");
        helper.searchGurillaGlass();
    }
    @Test
    public void searchCollegeBooks_62630() throws Exception {
        SearchResultsUIHelper helper = new SearchResultsUIHelper(serpHPTestData,"62630");
        helper.searchGurillaGlass();
    }
    @Test
    public void searchBooks_62631() throws Exception {
        SearchResultsUIHelper helper = new SearchResultsUIHelper(serpHPTestData,"62631");
        helper.searchGurillaGlass();
    }
    @Test
    public void searchStudy_62632() throws Exception {
        SearchResultsUIHelper helper = new SearchResultsUIHelper(serpHPTestData,"62632");
        helper.searchGurillaGlass();
    }
}
