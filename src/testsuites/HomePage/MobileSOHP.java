package testsuites.HomePage;



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
public class MobileSOHP extends DefaultTest{

    private static Properties mobileSohpTestData;

    static
    {
        try
        {
            logger.initialize("mobilesohp");
            mobileSohpTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/mobilesohp/mobilesohp.txt");
            mobileSohpTestData.load(userDataFis);
            userDataFis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public MobileSOHP() throws Exception {

    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.browserAgent="Apple iPhone 6";
        BaseDriver.ASSET="mobilesohp";
    }

    @Test
    public void verifyMobileHPCoupon_66002() throws Exception {
        MobileHomePageUIHelper helper = new MobileHomePageUIHelper(mobileSohpTestData,"66002");
        helper.verifyImageCount();

    }

}
