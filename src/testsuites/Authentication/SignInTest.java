package testsuites.Authentication;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testsuites.DefaultTest;
import utils.framework.BaseDriver;
import utils.selenium.AuthenticationHelper;
import utils.selenium.HomePageUIHelper;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *  Created by sshrimali on 8/24/16.
 */
public class SignInTest extends DefaultTest{

    private static Properties signinTestData;

    static
    {
        try
        {
            signinTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/authentication/signin/signin.txt");
            signinTestData.load(userDataFis);
            userDataFis.close();
            logger.initialize("signin");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public SignInTest() throws Exception {
    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.ASSET="authentication/signin";
    }
    @Test
    public void verifySOHPSignInResolutionChange_67906() throws Exception
    {
        HomePageUIHelper helper = new HomePageUIHelper(signinTestData,"67906");
        helper.verifyResolution();
    }
    @Test
    public void verifySignOut_5833() throws Exception
    {
        AuthenticationHelper helper = new AuthenticationHelper(signinTestData,"5833");
        helper.signUpUI();
        helper.signOut();
    }
}
