package testsuites.Authentication;


import org.seleniumhq.jetty9.server.Authentication;
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
public class SignUpTest extends DefaultTest{

    private static Properties signUpTestData;

    static
    {
        try
        {

            signUpTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/authentication/signup/signup.txt");
            signUpTestData.load(userDataFis);
            userDataFis.close();
            logger.initialize("signup");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public SignUpTest() throws Exception {
    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.ASSET="authentication/signup";
    }
    @Test
    public void signUpLessComplex_5846() throws Exception
    {
        AuthenticationHelper helper = new AuthenticationHelper(signUpTestData,"5846_1");
        helper.signUpUI();
        helper = new AuthenticationHelper(signUpTestData,"5846_2");
        helper.signUpUI();
        helper = new AuthenticationHelper(signUpTestData,"5846_3");
        helper.signUpUI();
    }

    @Test
    public void verifySignUpStudentParentType_25531() throws Exception
    {
        AuthenticationHelper helper = new AuthenticationHelper(signUpTestData,"25531");
        helper.signUpUI();
    }
    @Test
    public void verifySignUpNotRegistered_25532() throws Exception
    {
        AuthenticationHelper helper = new AuthenticationHelper(signUpTestData,"25532");
        helper.signUpUI();
    }
}
