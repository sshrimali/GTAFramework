package testsuites.HomePage;


import org.testng.annotations.*;
import testsuites.DefaultTest;
import utils.framework.BaseDriver;
import utils.framework.Logger;
import utils.framework.Recorder;
import utils.selenium.CartHelper;
import utils.selenium.PDPUIHelper;
import utils.selenium.HomePageUIHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by sshrimali on 8/24/16.
 */
public class SOHP extends DefaultTest {

    private static Properties sohpTestData;
    Recorder rec = new Recorder();
    PDPUIHelper pdpHelper;
    CartHelper cartHelper;
    static
    {
        try {
            //Initialize Logger
            logger.initialize("sohp");
            sohpTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/homepage/sohp.txt");
            sohpTestData.load(userDataFis);

            userDataFis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public SOHP() throws Exception {

    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.ASSET="homepage";
    }
    @Test
    public void verifyHomePageLookFeel_25861() throws Exception {
        HomePageUIHelper helper = new HomePageUIHelper(sohpTestData,"25861");
        helper.openHomePage();
        helper.verifySignedOutHomePageLookAndFeel();
    }
    @Test
    public void verifySingleHeaderOnHomePage_25924() throws Exception {
        HomePageUIHelper helper = new HomePageUIHelper(sohpTestData,"25924");
        helper.openHomePage();
        helper.verifyImageCount();
    }
    @Test
    public void verifyCartIconHP_5869() throws Exception
    {
        pdpHelper = new PDPUIHelper(sohpTestData,"5869_01");
        pdpHelper.addToCart();
        pdpHelper = new PDPUIHelper(sohpTestData,"5869_02");
        pdpHelper.addToCart();
        cartHelper = new CartHelper(sohpTestData,"5869_03");
        cartHelper.verifyCart();
    }
    @Test
    public void verifyHomePageMenu_5872() throws Exception
    {
        HomePageUIHelper helper = new HomePageUIHelper(sohpTestData,"5872");
        helper.verifyHomePageDropDownMenu();
    }
    @Test
    public void verifyHamBurgerMenu_113427() throws Exception
    {
        HomePageUIHelper helper = new HomePageUIHelper(sohpTestData,"113427");
        helper.verifyHamburgerMenu();
    }

}
