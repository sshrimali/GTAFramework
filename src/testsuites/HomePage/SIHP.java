package testsuites.HomePage;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testsuites.DefaultTest;
import utils.framework.BaseDriver;
import utils.framework.Logger;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *  Created by sshrimali on 8/24/16.
 */
public class SIHP extends DefaultTest{

    private static Properties sohpTestData;

    static
    {
        try
        {
            logger.initialize("sihp");
            sohpTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/homepage/sihp.txt");
            sohpTestData.load(userDataFis);
            userDataFis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public SIHP() throws Exception {
    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.ASSET="homepage";
    }
    //@Test
    public void sourabh_4() throws Exception {
        //BasicConfigurator.configure();
        Logger.logger.info("My test failures custom by sourabh from sihp");
        //repeat with all other desired appenders


    }
    //@Test
    public void sourabh_5() throws Exception {
        System.out.println("I always pass");
    }


}
