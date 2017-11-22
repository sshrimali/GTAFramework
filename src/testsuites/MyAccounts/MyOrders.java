package testsuites.MyAccounts;


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
public class MyOrders extends DefaultTest{

    private static Properties myOrdersTestData;

    static
    {
        try
        {
            myOrdersTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/myaccounts/myorders/myorders.txt");
            myOrdersTestData.load(userDataFis);
            userDataFis.close();
            logger.initialize("MYORDERS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public MyOrders() throws Exception {
    }

    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.ASSET="myaccounts/myorders";
        //Create a generic user and sign up
        AuthenticationHelper helper = new AuthenticationHelper(myOrdersTestData,"myorders_001");
        helper.signUpUI();
        PDPUIHelper pdp = new PDPUIHelper(myOrdersTestData,"myorders_002");
        pdp.addToCart();
        PDPUIHelper pdp2 = new PDPUIHelper(myOrdersTestData,"myorders_003");
        pdp2.addToCart();
        OrdersHelper ordersHelper = new OrdersHelper(myOrdersTestData,"myorders_002");
        ordersHelper.placeOrder();
    }
    @Test
    public void verifyPrintReciept_5743() throws Exception
    {
        MyOrdersHelper helper = new MyOrdersHelper(myOrdersTestData,"5742");
        helper.verifyMyOrder();
    }
    @Test
    public void verifyTutorsLinkMyOrdersPage_5749() throws Exception
    {
        MyOrdersHelper helper = new MyOrdersHelper(myOrdersTestData,"5749");
        helper.verifyMyOrder();

    }
}
