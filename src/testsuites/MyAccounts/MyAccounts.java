package testsuites.MyAccounts;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testsuites.DefaultTest;
import utils.framework.BaseDriver;
import utils.selenium.AuthenticationHelper;
import utils.selenium.BuyBackHelper;
import utils.selenium.MyAccountsHelper;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *  Created by sshrimali on 8/24/16.
 */
public class MyAccounts extends DefaultTest{

    private static Properties myAccountsTestData;

    static
    {
        try
        {
            myAccountsTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/myaccounts/myaccounts.txt");
            myAccountsTestData.load(userDataFis);
            userDataFis.close();
            logger.initialize("MYACCOUNTS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public MyAccounts() throws Exception {
    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.ASSET="myaccounts";
        //Create a generic user and sign up
        AuthenticationHelper helper = new AuthenticationHelper(myAccountsTestData,"myaccount_user_001");
        helper.signUpUI();
    }
    @Test
    public void changePassInvalidNewPass_5715() throws Exception {

        MyAccountsHelper myAccountsHelper = new MyAccountsHelper(myAccountsTestData,"5715");
        myAccountsHelper.changePassword();
    }
    @Test
    public void changePassInvalidOldPass_5716() throws Exception {

        MyAccountsHelper myAccountsHelper = new MyAccountsHelper(myAccountsTestData,"5716");
        myAccountsHelper.changePassword();
    }
    @Test
    public void verifyCSinMyAccounts_5739() throws Exception {

        MyAccountsHelper myAccountsHelper = new MyAccountsHelper(myAccountsTestData,"5739");
        myAccountsHelper.subscribeToStudy();
    }
    @Test
    public void verifyTutorsinMyAccounts_5740() throws Exception {

        MyAccountsHelper myAccountsHelper = new MyAccountsHelper(myAccountsTestData,"5740");
        myAccountsHelper.subscribeToTutors();
    }
    @Test
    public void verifyBooksIveSold_5750() throws Exception {
        BuyBackHelper helper = new BuyBackHelper(myAccountsTestData,"5750");
        helper.sellBooks();
        MyAccountsHelper myAccountsHelper = new MyAccountsHelper(myAccountsTestData,"5750");
        myAccountsHelper.verifyBooksIveSold();
    }
    @Test
    public void updNotificationPref_5736() throws Exception {
        MyAccountsHelper myAccountsHelper = new MyAccountsHelper(myAccountsTestData,"5736");
        myAccountsHelper.updateNotificationPreferance();
    }
}
