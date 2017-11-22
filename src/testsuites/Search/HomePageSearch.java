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
public class HomePageSearch extends DefaultTest{
    private static Properties serpHPTestData;

    static
    {
        try
        {
            serpHPTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/search/homepagesearch/homepagesearch.txt");
            serpHPTestData.load(userDataFis);
            userDataFis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //Initialize Logger
        logger.initialize("serp");
    }

    public HomePageSearch() throws Exception {
    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.ASSET="serp";
    }
    @Test
    public void searchBookWithoutVersion_112998() throws Exception {
        SearchResultsUIHelper helper = new SearchResultsUIHelper(serpHPTestData,"112998");
        helper.serpSearch();
    }
}
