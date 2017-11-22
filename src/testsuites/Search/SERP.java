package testsuites.Search;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testsuites.DefaultTest;
import utils.framework.BaseDriver;
import utils.framework.Logger;
import utils.selenium.HomePageUIHelper;
import utils.selenium.SearchResultsUIHelper;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *  Created by sshrimali on 8/24/16.
 */
public class SERP extends DefaultTest{
    private static Properties serpTestData;

    static
    {
        try
        {
            serpTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/serp/serp.txt");
            serpTestData.load(userDataFis);
            userDataFis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //Initialize Logger
        logger.initialize("serp");
    }

    public SERP() throws Exception {
    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.ASSET="serp";
    }
    //@Test
    public void verifySearchAdsPositioning_21755() throws Exception {
        SearchResultsUIHelper helper = new SearchResultsUIHelper(serpTestData,"21755");
        helper.serpSearch();
        helper.verifySERPAdsPlacement();
    }
    //@Test
    public void verifyPagination_5927() throws Exception
    {
        SearchResultsUIHelper helper = new SearchResultsUIHelper(serpTestData,"5927");
        helper.serpSearch();
        helper.verifyPagination();
    }
   // @Test
    public void newSearchSerp_5940() throws Exception {
        SearchResultsUIHelper helper = new SearchResultsUIHelper(serpTestData,"5940_1");
        helper.serpSearch();
        helper = new SearchResultsUIHelper(serpTestData,"5940_2");
        helper.serpSearch();
        helper.verifySearchResults();
    }
    @Test
    public void verifyNavigatingToTutors_5951() throws Exception {
        SearchResultsUIHelper helper = new SearchResultsUIHelper(serpTestData,"5951");
        helper.serpSearch();
        helper.switchSerpVertical();
        helper.verifySearchResults();
    }


}
