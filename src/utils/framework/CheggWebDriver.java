package utils.framework;

//import org.apache.log4j.Logger;
import com.google.common.base.Predicate;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static utils.framework.BaseDriver.*;
import static utils.framework.Logger.logger;


/**
 * Created by sshrimali on 9/2/16.
 */
public  class WebDriver implements WebDriver {

    private int retries;
    private int globalRetries=3;
    private int globalRetriesShort=1;
    private int sleep=100;
    private int quickWaitTime=5;
    private WebElement element;
    private int timeOutInSeconds;

    public WebDriver(String browser, int timeOutInSeconds)
    {
        isMobile=false;
        try
        {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            if (browser.equalsIgnoreCase("firefox"))
            {
                //System.setProperty("webdriver.gecko.driver","lib//firefoxdriver_mac");
                System.setProperty("webdriver.gecko.driver","lib//geckodriver");
                //System.setProperty("webdriver.firefox.marionette", "lib//firefoxdriver_mac");
                System.setProperty("webdriver.firefox.marionette", "lib//geckodriver");
                // Configure custom profile parameters
                capabilities.setBrowserName("firefox");
                FirefoxProfile profile = new FirefoxProfile();
                profile.setAcceptUntrustedCertificates(true);
                profile.setAssumeUntrustedCertificateIssuer(true);
                profile.setEnableNativeEvents(true);
                capabilities.setCapability(FirefoxDriver.PROFILE,profile);
                // Create driver session only if it does not exist
                if (driver == null)
                {
                    logger.debug("Instantiating firefox driver");
                    driver = new FirefoxDriver(capabilities);
                    logger.info("Instantiated firefox driver");
                }
            }
            else if(browser.equalsIgnoreCase("chrome"))
            {
                System.setProperty("webdriver.chrome.driver","lib//chromedriver_mac");
                Map<String, String> mobileEmulation = new HashMap<String, String>();
                Map<String, Object> chromeOptions = new HashMap<String, Object>();
                if (!browserAgent.equalsIgnoreCase("default"))
                {
                    if (browserAgent.toLowerCase().contains("phone")) {
                        isMobile = true;
                    }
                    mobileEmulation.put("deviceName", browserAgent);
                    chromeOptions.put("mobileEmulation", mobileEmulation);
                    capabilities.setCapability(ChromeOptions.CAPABILITY , chromeOptions);
                }
                // Create driver session only if it does not exist
                driver = new ChromeDriver(capabilities);
                logger.info("Instantiating chrome driver");
            }
            else if(browser.equalsIgnoreCase("safari"))
            {
                //System.setProperty("webdriver.chrome.driver","lib//chromedriver_mac");
                // Create driver session only if it does not exist
                driver = new SafariDriver(capabilities);
                logger.info("Instantiating safari driver");
            }
            // Configure generic parameters
            capabilities.setJavascriptEnabled(true);
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);


            //Configure implicit time out globally
            driver.manage().timeouts().implicitlyWait(timeOutInSeconds, TimeUnit.SECONDS);
            this.timeOutInSeconds=timeOutInSeconds;
            BaseDriver.currentAgent=browserAgent;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public WebDriver(String browser,String browserAgent, int timeOutInSeconds)
    {

        try
        {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            if (browser.equalsIgnoreCase("firefox"))
            {
                System.setProperty("webdriver.firefox.driver","lib//firefoxdriver_mac");
                System.setProperty("webdriver.gecko.driver","lib//firefoxdriver_mac");
                // Configure custom profile parameters
                capabilities.setBrowserName("firefox");
                FirefoxProfile profile = new FirefoxProfile();
                profile.setAcceptUntrustedCertificates(true);
                profile.setAssumeUntrustedCertificateIssuer(true);
                profile.setEnableNativeEvents(true);
                capabilities.setCapability(FirefoxDriver.PROFILE,profile);
                // Create driver session only if it does not exist
                    driver = new FirefoxDriver(capabilities);
                    logger.info("Instantiating firefox driver");
            }
            else if(browser.equalsIgnoreCase("chrome") && browserAgent.equalsIgnoreCase("ipad"))
            {
                System.setProperty("webdriver.chrome.driver","lib//chromedriver_mac");
                Map<String, String> mobileEmulation = new HashMap<String, String>();
                mobileEmulation.put("deviceName", browserAgent);

                Map<String, Object> chromeOptions = new HashMap<String, Object>();
                chromeOptions.put("mobileEmulation", mobileEmulation);

                DesiredCapabilities cap = DesiredCapabilities.chrome();
                cap.setCapability(ChromeOptions.CAPABILITY , chromeOptions);
                driver = new ChromeDriver(cap);
                logger.info("Instantiating chrome driver");
            }
            // Configure generic parameters
            capabilities.setJavascriptEnabled(true);
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);


            //Configure implicit time out globally
            driver.manage().timeouts().implicitlyWait(timeOutInSeconds, TimeUnit.SECONDS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void waitForTextPresent(String textToWaitFor) throws Exception {
        retries=0;
        while (true) {
            try {
                String bodyText = driver.findElement(By.tagName("body")).getText();
                if (bodyText.contains(textToWaitFor))
                {
                    logger.info("Found message in body : " + textToWaitFor);
                    break;
                }
            }
            catch (Exception e)
            {
                if (retries >= globalRetries)
                {
                    customFailure(e,"Failed waiting for text : " + textToWaitFor);
                }
                retries=retries+1;
            }
            if (retries >= globalRetries)
            {
                customFailure("Timed out waiting for text : " + textToWaitFor);
            }
            retries=retries+1;
        }
    }
    public String executeScript(String script)
    {
        String response="";
        logger.info("Executing JS " + script);
        response = (String)((JavascriptExecutor)driver).executeScript(script);
        logger.debug("Response from JS : " + script);
        return response;
    }
    public void acceptAlert()
    {
        logger.info("Accepting alert");
        driver.switchTo().alert().accept();
    }
    public void triggerAlert(String alertText)
    {
        logger.info("Intitiating Alert : " + alertText);
        executeScript("alert('" + alertText + "')");
    }
    public void maximizeWindow()
    {

        logger.info("Maximing window");
        manage().window().maximize();
    }

    @Override
    public void get(String url) {
        logger.info("Get  : " + url);
        if (url.startsWith("/"))
        {
            if (baseURL.contains("?"))
            {
                url=baseURL.replace("?",url + "/?");
                logger.warn("Found ? in URL, reorganizing subdomain as : " + url);
            }
            else {
                url = baseURL + url;
            }
        }
        driver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override @Deprecated
    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public Options manage() {
        return driver.manage();
    }

    public void customFailure(Exception e, String customErrorMessage) throws Exception {
        String message=customErrorMessage + "<br/><br/>\n" + "Exception details : " + String.valueOf(e);
        customFailure(message);
    }
    public void customFailure(String customErrorMessage) throws Exception {
        Screenshot screenshot = new Screenshot();
        String screenshotLink = screenshot.capture();
        String message="Failure Reason : " + customErrorMessage  + "<br/><br/>" + "\n <br/>" + screenshotLink;
        logger.error(message.replace("<br/>","\n"));
        Assert.fail(message);
    }


    public void scrollDownBy(int i) {
        executeScript("window.scrollBy(0," + i + ")");
    }
    public void scrollToTop() {
        executeScript("window.scrollBy(0,-100000)");
    }
    public void resizeWindow(String dimentions)
    {
        int width = Integer.parseInt(dimentions.split(",")[0]);
        int height = Integer.parseInt(dimentions.split(",")[1]);
        driver.manage().window().setSize(new Dimension(width, height));
        logger.info("Resizing window to : " + dimentions);
    }
    public WebElement findElement(String locator) throws Exception
    {
        element=null;
            List<WebElement> elements = findElements(locator);
            logger.debug("Found elements : " + elements.size());
            for (int iterator=0;iterator < elements.size();iterator++)
                {
                    if (elements.get(iterator).isDisplayed())
                    {
                        element = elements.get(iterator);
                        logger.debug("Found element displayed or enabled: " + iterator + " : " + elements.get(iterator));
                        break;
                }
                element = elements.get(0);
            }
        logger.debug("Finalizing on element : " + element.toString());

        return element;
    }
    public List<WebElement> findElements(String locator) throws Exception
    {
        List<WebElement> elements = driver.findElements(getLocatorMethod(locator));
        return elements;
    }

    private By getLocatorMethod(String locator) throws Exception {
        By locatorMethod = null;
        if (null == locator)
        {
            customFailure(new NullPointerException() ,"locator specified is Null");
        }
        if (locator.startsWith("//") || locator.startsWith("xpath="))
        {
            locator=locator.replace("xpath=","");
            logger.info("Looking by Xpath : " + locator);
            locatorMethod=By.xpath(locator);
        }

        else if(locator.startsWith("id="))
        {
            locator=locator.replace("id=","");
            logger.info("Looking by id : " + locator);
            locatorMethod=By.id(locator);
        }
        else if(locator.startsWith("name="))
        {
            locator=locator.replace("name=","");
            logger.info("Looking by name : " + locator);
            locatorMethod=By.name(locator);
        }
        else
        {
            locator=locator.replace("css=","");
            logger.info("Looking by css : " + locator);
            locatorMethod=By.cssSelector(locator);
        }
        return locatorMethod;
    }
    @Deprecated
    private boolean isElementPresent(String locator) throws Exception{
        try {
            driver.manage().timeouts().implicitlyWait(quickWaitTime,TimeUnit.SECONDS);
            if (findElements(locator).size() > 0) {
                return true;
            }
            else
            {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        finally {
            driver.manage().timeouts().implicitlyWait(timeOutInSeconds,TimeUnit.SECONDS);
        }
    }
    @Deprecated
    private WebElement findElementOriginal(String locator) throws Exception
    {
        element=null;
        retries=0;
        while (true)
        {
            try {
                if (locator.startsWith("//") || locator.startsWith("xpath="))
                {
                    locator=locator.replace("xpath=","");
                    element = driver.findElement(By.xpath(locator));

                }
                else if(locator.startsWith(".") || locator.startsWith("css="))
                {
                    locator=locator.replace("css=","");

                    element = driver.findElement(By.cssSelector(locator));
                }
                else if(locator.startsWith("id="))
                {
                    locator=locator.replace("id=","");
                    element = driver.findElement(By.id(locator));
                }
                break;
            } catch (Exception e) {

                if (retries <= globalRetries) {
                    Thread.sleep(sleep);
                    retries++;
                    continue;
                }
                else
                {
                    customFailure(e, "Failed trying to find");
                    break;
                }
            }
        }

        return element;
    }
    public void type(String locator,String text) throws Exception {

        retries=0;
        while (true)
        {
            try {
                element = findElement(locator);
                logger.info("Writing text : " + text + " to locator : " + locator);
                element.click();
                element.clear();
                element.sendKeys(text);
                break;
            } catch (Exception e) {
                if (retries <= globalRetries) {
                    Thread.sleep(sleep);
                    retries++;
                    logger.warn("SendKeys to Element " + locator + " : Iteration : " + retries + " : Max Limit : " + globalRetries + " : Exception : " + e.getMessage());
                    continue;
                }
                else
                {
                    customFailure(e,"Failed trying to type to locator " + locator);
                    break;
                }
            }
        }

    }
    @Deprecated
    private void clickNew(String locator) throws Exception{
        try {
            element = findElement(locator);
            clickFast(element);
        }
        catch (Exception e) {
            customFailure(e, "Failed trying to click : " + locator);
            }
    }
    private void clickFast(WebElement element) throws Exception{
        try {
            element.click();
        }
        catch (Exception e) {
            logger.info("Tried fast click and in exception now");
            element = waitForCondition(ExpectedConditions.elementToBeClickable(element));
            element.click();
        }
    }

    private WebElement waitForCondition(ExpectedCondition<WebElement> condition) {
        WebDriverWait wait = new WebDriverWait(driver,timeOutInSeconds);
        logger.debug("Waiting for " + condition.toString());
        WebElement element = wait.until(condition);
        logger.debug("Wait completed for :" + condition.toString());
        return element;
    }

    public void click(String locator) throws Exception {
        logger.info("Getting element");
        retries=0;
        while (true)
        {
            try {
                element = findElement(locator);
                logger.debug("Got element");
                element.click();
                logger.info("Clicking : " + locator );
                break;
            } catch (Exception e) {
                if (retries <= globalRetries) {
                    Thread.sleep(sleep);
                    retries++;
                    logger.warn("Clicking on Element " + locator + " : Iteration : " + retries + " : Max Limit : " + globalRetries + " : Exception : " + e.getMessage());
                    continue;
                }
                else
                {
                    customFailure(e, "Failed trying to click : " + locator);
                    break;
                }
            }
        }
    }
    public void clickIfPresent(String locator) throws Exception {
        logger.info("Getting element");
        retries=0;
        while (true)
        {
            try {
                element = findElement(locator);
                logger.debug("Got element");
                element.click();
                logger.info("Clicking : " + locator );
                break;
            } catch (Exception e) {
                if (retries <= globalRetriesShort) {
                    Thread.sleep(sleep);
                    retries++;
                    logger.warn("Clicking on Element " + locator + " : Iteration : " + retries + " : Max Limit : " + globalRetries + " : Exception : " + e.getMessage());
                    continue;
                }
                else
                {
                    break;
                }
            }
        }
    }
    @Deprecated
    public void debugWait()
    {
        try {
            int debugWait = 2000;
            Thread.sleep(debugWait);
            logger.info("Waiting : " + debugWait);

        } catch (InterruptedException e) {
        }
    }

    public void mouseHover(String locator) throws Exception {
        try {
            Actions actions = new Actions(driver);
            element = findElement(locator);
            actions.moveToElement(element).build().perform();
            logger.info("Mouse over Element " + locator);
        }
        catch (Exception e)
        {
            customFailure(e, "Failed moving mouse to : " + locator);
        }
    }

    public String captureScreenshot(String element,String path) throws Exception {
        Screenshot screenshot = new Screenshot();
        String imageFile="";
        {
            imageFile=screenshot.capture(element,path);
            logger.info("Saved screenshot for element " + element + " at " + path);
        }
        return imageFile;
    }
    public boolean isElementVisible(String locator) throws Exception
    {
        try {
            driver.manage().timeouts().implicitlyWait(quickWaitTime,TimeUnit.SECONDS);
            List<WebElement> listOfElements = findElements(locator);
            if (listOfElements.size() > 0 && listOfElements.get(0).isDisplayed())
            {
                logger.info("IsVisible : True : " + locator);
                return true;
            }
            else
            {
                logger.warn("IsVisible : False : " + locator);
                return false;
            }
        }
        catch (Exception e)
        {
            logger.warn("IsVisible : False : " + locator + " : Recieved Exception : " + e.getMessage());
            return false;
        }
        finally {
            driver.manage().timeouts().implicitlyWait(timeOutInSeconds,TimeUnit.SECONDS);
        }
    }
    public void moveToElement(String locator) throws Exception
    {
        element = findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }
    public void waitForElementPresent(String locator) throws Exception {
        retries=0;
        while (true)
        {
            try {
                element = findElement(locator);
                if (element.isDisplayed())
                {
                    logger.debug("Wait for element present :Found element :  True : " + locator );
                    break;
                }
                logger.warn("Element not yet displayed : " + locator );
                break;
            } catch (Exception e) {
                if (retries <= globalRetries) {
                    Thread.sleep(sleep);
                    retries++;
                    logger.warn("Element not yet displayed : " + locator + " : Iteration : " + retries + " : Max Limit : " + globalRetries + " : Exception : " + e.getMessage());
                    continue;
                }
                else
                {
                    customFailure(e, "Timed out waiting for element : " + locator);
                    break;
                }
            }
        }
    }
    public void waitForActionableElement(String locator) throws Exception {
        retries=0;
        while (true)
        {
            try {
                element = findElement(locator);
                if (element.isDisplayed() && element.isEnabled())
                {
                    logger.debug("Wait for element actionable : True : " + locator );
                    break;
                }
                logger.warn("Element not yet actionable : " + locator );
                break;
            } catch (Exception e) {
                if (retries <= globalRetries) {
                    Thread.sleep(sleep);
                    retries++;
                    logger.warn("Element not yet actionable : " + locator + " : Iteration : " + retries + " : Max Limit : " + globalRetries + " : Exception : " + e.getMessage());
                    continue;
                }
                else
                {
                    customFailure(e, "Timed out waiting for element : " + locator);
                    break;
                }
            }
        }
    }

    public void pressEnterKey(String locator) throws Exception {
        try {
            WebElement element = findElement(locator);
            element.sendKeys(Keys.ENTER);
            logger.info("Pressed enter key");
        }
        catch (Exception e)
        {
            customFailure(e, "Failed trying to press enter key : " + locator);
        }
    }


    public void select(String locator, String value) throws Exception {
        try {

            WebElement element = findElement(locator);
            logger.info("From dropdown : " + locator + " : select item with text : " + value);
            Select selectElement = new Select(element);
            selectElement.selectByVisibleText(value);
        }
        catch (Exception e)
        {
            logger.error("Unable to select from drop-down " + locator + " : Value : " + value + " : Exception : " + e.getMessage());
        }
    }
    public void mouseOver(String locator) throws Exception {
        try {


            WebElement element = findElement(locator);
            logger.info("Mouse over to: " + locator);
            Actions builder = new Actions(driver);
            builder.moveToElement(element).build().perform();
        }
        catch (Exception e)
        {
            logger.error("Unable to move to element : " + locator + " : Exception : " + e.getMessage());
        }
    }

    public String getText(String locator) throws Exception {
        retries=0;
        String text = "";
        while (true)
        {
            try {
                element = findElement(locator);
                logger.info("Getting text from " + locator);
                text = element.getText();
                break;
            } catch (Exception e) {
                if (retries <= globalRetries) {
                    Thread.sleep(sleep);
                    retries++;
                    logger.warn("Getting text from " + locator + " : Iteration : " + retries + " : Max Limit : " + globalRetries + " : Exception : " + e.getMessage());
                    continue;
                }
                else
                {
                    customFailure(e,"Failed trying to get string from locator " + locator);
                    break;
                }
            }
        }
        return text;
    }
}
