package testsuites.HeaderFooter;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testsuites.DefaultTest;
import utils.framework.BaseDriver;
import utils.selenium.AuthenticationHelper;
import utils.selenium.FooterHelper;
import utils.selenium.MyAccountsHelper;
import utils.selenium.NavigationHelper;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *  Created by sshrimali on 8/24/16.
 */
public class FooterTest extends DefaultTest{

    private static Properties myAccountsTestData;


    static
    {
        try
        {

            myAccountsTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/headerfooter/footer.txt");
            myAccountsTestData.load(userDataFis);
            userDataFis.close();
            logger.initialize("HeaderFooter");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public FooterTest() throws Exception {
    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.ASSET="headerfooter";
    }

    @Test
    public void verifyCopyRight_5892() throws Exception {
        NavigationHelper navigator = new NavigationHelper();
        navigator.navigateTo(NavigationHelper.navigater.HOMEPAGE);
        FooterHelper footerHelper = new FooterHelper(myAccountsTestData,"5892");
        footerHelper.verifyFooter();
    }
}
