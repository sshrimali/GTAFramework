package testsuites.Orders;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testsuites.DefaultTest;
import testsuites.PDP.PDPTest;
import utils.framework.BaseDriver;
import utils.selenium.AdobeHelper;
import utils.selenium.CartHelper;
import utils.selenium.PDPUIHelper;


import java.io.FileInputStream;
import java.util.Properties;

/**
 *  Created by sshrimali on 8/24/16.
 */
public class OrdersTest extends DefaultTest{

    private static Properties ordersTestData;

    static
    {
        try
        {
            ordersTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/orders/orders.txt");
            ordersTestData.load(userDataFis);
            userDataFis.close();
            logger.initialize("orders");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public OrdersTest() throws Exception {
    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.ASSET="orders";
        //Create a generic user and sign up
        //AuthenticationHelper helper = new AuthenticationHelper(ordersTestData,"order_user_001");
        //helper.signUpUI();
    }
    @Test
    public void verifyTBS_5759() throws Exception
    {
        //AdobeHelper helper = new AdobeHelper(ordersTestData,"5759");
        //helper.getDigitalDataObject();
        PDPUIHelper helper = new PDPUIHelper(ordersTestData,"5759");
        helper.addToCart();
        CartHelper cart = new CartHelper(ordersTestData,"5759");
        cart.verifyCart();
    }
}
