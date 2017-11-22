package testsuites.HeaderFooter;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testsuites.DefaultTest;
import utils.framework.BaseDriver;
import utils.selenium.MobileHomePageUIHelper;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *  Created by sshrimali on 8/24/16.
 */
public class MobileHeader extends DefaultTest{

    private static Properties mobileHeaderTestData;

    static
    {
        try
        {
            logger.initialize("mobileheader");
            mobileHeaderTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/headerfooter/mobileheader/mobileheader.txt");
            mobileHeaderTestData.load(userDataFis);
            userDataFis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public MobileHeader() throws Exception {

    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.browserAgent="Apple iPhone 6";
        BaseDriver.ASSET="headerfooter/mobileheader";
    }

    @Test
    public void verifyVerticalHighlighting_99825() throws Exception {
        MobileHomePageUIHelper helper = new MobileHomePageUIHelper(mobileHeaderTestData,"99825_1");
        helper.verifyImage();
        helper = new MobileHomePageUIHelper(mobileHeaderTestData,"99825_2");
        helper.verifyImage();
        helper = new MobileHomePageUIHelper(mobileHeaderTestData,"99825_3");
        helper.verifyImage();
        helper = new MobileHomePageUIHelper(mobileHeaderTestData,"99825_4");
        helper.verifyImage();
    }
    @Test
    public void verifyHP_99826() throws Exception {
        MobileHomePageUIHelper helper = new MobileHomePageUIHelper(mobileHeaderTestData, "99826");
        helper.verifyImage();
    }
    @Test
    public void verifyHP_99833() throws Exception {
        MobileHomePageUIHelper helper = new MobileHomePageUIHelper(mobileHeaderTestData, "99833");
        helper.verifyImage();
    }
    @Test
    public void verifyHP_99834() throws Exception {
        MobileHomePageUIHelper helper = new MobileHomePageUIHelper(mobileHeaderTestData, "99834");
        helper.verifyImage();
    }
    @Test
    public void verifyHP_99836() throws Exception {
        MobileHomePageUIHelper helper = new MobileHomePageUIHelper(mobileHeaderTestData, "99836");
        helper.verifyImage();
    }
}
