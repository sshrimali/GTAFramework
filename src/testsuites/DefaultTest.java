package testsuites;

import org.testng.annotations.*;
import utils.framework.ActionDriver;
import utils.framework.Logger;
import utils.framework.Recorder;
import utils.framework.BaseDriver;
import utils.selenium.NavigationHelper;

/**
 * Created by sshrimali on 8/28/16.
 */
public class DefaultTest {
    Recorder rec = new Recorder();
    public static Logger logger = new Logger();

    public DefaultTest() throws Exception {
    }

    @BeforeMethod
    public void initializeMethod() throws Exception {
        rec.startRecording();
        BaseDriver driver = new BaseDriver();
        driver.navigateTo(NavigationHelper.navigater.HOMEPAGE);
    }
    @AfterMethod
    public void cleanupMethod() throws Exception {
        //Close browser
        rec.stopRecording();
    }

    @AfterClass
    public void cleanupClass() throws Exception {
        BaseDriver.browserAgent="default";
        //Close browser
        try {
            BaseDriver.close();
        }
        catch (Exception e)
        {
            Logger.logger.error(e);
        }
        Logger.logger.info("Closing browser");
    }
    @BeforeSuite
    public void initializeFramework() throws Exception
    {
        //Initialize Mouse
        ActionDriver action = new ActionDriver();
        action.moveMouse(0,0);
    }
}
