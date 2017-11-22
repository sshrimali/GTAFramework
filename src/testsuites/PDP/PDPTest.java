package testsuites.PDP;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testsuites.DefaultTest;
import utils.framework.BaseDriver;
import utils.framework.Logger;
import utils.framework.Recorder;
import utils.selenium.PDPUIHelper;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by sshrimali on 8/24/16.
 */
public class PDPTest extends DefaultTest {

    private static Properties pdpTestData;

    static
    {
        try
        {

            logger.initialize("PDP");
            pdpTestData = new Properties();
            // Read Test data as property and pass to utilities
            FileInputStream userDataFis = new FileInputStream("testdata/pdp/pdp.txt");
            pdpTestData.load(userDataFis);
            userDataFis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public PDPTest() throws Exception {

    }
    @BeforeClass
    public void initialize() throws Exception
    {
        BaseDriver.ASSET="pdp";
    }

    //@Test //Disabled for now
    public void verifyPDPCompatiblityForIPADDesktop_25862() throws Exception {
        PDPUIHelper helper = new PDPUIHelper(pdpTestData,"25862");
        helper.navitageToPDP();
        helper.comparePDP();
    }
    @Test
    public void verifyBookCover_5774() throws Exception {
        PDPUIHelper helper = new PDPUIHelper(pdpTestData,"5774");
        helper.navitageToPDP();
        helper.verifyPDP();
    }
    @Test
    public void verifyFamilyURL_86330() throws Exception{
        PDPUIHelper helper = new PDPUIHelper(pdpTestData,"86330");
        helper.navitageToPDP();
        helper.verifyFamilyURL();
    }
}
