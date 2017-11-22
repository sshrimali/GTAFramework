package utils.sikuli;

import com.google.common.collect.Iterators;
import org.apache.commons.io.FileUtils;
import org.sikuli.script.*;
import org.testng.Assert;
import utils.framework.BaseDriver;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import static utils.framework.Logger.logger;



/**
 * Created by sshrimali on 9/8/16.
 */
public class SikuliHelper extends BaseDriver {


    private ScreenImage actual;
    private static String imageFile;
    public SikuliHelper() throws Exception {
    }

    public void testClick(String location) throws Exception {
        Thread.sleep(3000);
        Screen screen = new Screen();
        Pattern pattern = new Pattern(location);

        try {
            screen.click(pattern.similar((float) 0.95));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        captureScreenshot();


    }
    private void verifyImagePresentBase(String imageFileRaw,String match) throws Exception {
        this.imageFile = imageFileRaw;
        setImageLib(imageFile);
        Thread.sleep(2000);
        Screen screen = new Screen();
        Pattern pattern = new Pattern(imageFile);
        FileUtils.copyFile(new File(imageFile), new File(screenshotLocation + "//" + testcaseId + "_expected.png"));
        try {
            logger.info("Attempting to verify Image in screen: " + imageFile + " with match : " + match);
            captureActual(screenshotLocation + "//" + testcaseId + "_actual.png",screen);
            screen.find(pattern.similar(Float.parseFloat(match)));
        }
        catch (Exception e)
        {
            logger.error("Image not found on screen : " + imageFile + " with match : " + match + "\nError : " + e.getMessage());
            Assert.fail("Image not found on screen : " + imageFile + " with match : " + match + "\n <br/>Exception Trace : " + e.getMessage());
        }
    }
    public void verifyImagePresent(String imageFileRaw,String match) throws Exception {
        String[] imageFileArray=imageFileRaw.split("::");
        for (String file : imageFileArray)
        {
            verifyImagePresentBase(file,match);
        }
    }
    public void verifyImagePresent(String imageFileRaw) throws Exception {
        String imageComparisionFactor = defaultImageComparisionFactor;
        verifyImagePresent(imageFileRaw,imageComparisionFactor);

    }
    public int getCountOfImage(String imageFileRaw,String match) throws Exception {
        int count=0;
        this.imageFile=imageFileRaw;
        setImageLib(imageFile);
        FileUtils.copyFile(new File(imageFile), new File(screenshotLocation + "//" + testcaseId + "_expected.png"));
        Thread.sleep(3000);
        Screen screen = new Screen();
        Pattern pattern = new Pattern(imageFile);
        UI.triggerAlert("Verifying : " + imageFile);
        Thread.sleep(2);
        UI.acceptAlert();
        try {
            captureActual(screenshotLocation + "//" + testcaseId + "_actual.png",screen);
            Iterator<Match> it = screen.findAll(pattern.similar(Float.parseFloat(match)));
            count= Iterators.size(it);
            logger.info("Found : " + count + " instances of image " + imageFile + " : with match : " + match);
        }
        catch (Exception e)
        {
            logger.error("Image(count) verification failed : " + imageFile + " with match : " + match + "\nError : " + e.getMessage());
            Assert.fail("Image(count) verification failed : " + imageFile + " with match : " + match + "\nError : " + e.getMessage());
        }
        return count;
    }
    public int getCountOfImage(String image) throws Exception {
        String imageComparisionFactor = defaultImageComparisionFactor;
        return getCountOfImage(image,imageComparisionFactor);
    }
    public void setImageLib()
    {

        File file = new File(imageFile);
        if(file.exists() && !file.isDirectory()) {
            ImagePath.add(imageLibPath);
            logger.debug("Adding asset image in imagelib  : " + imageLibPath);
        }
        else
        {
            logger.warn("Failed adding asset image in imagelib  : " + imageLibPath  + " Image file does not exist or is a directory");
        }
        ImagePath.add(globalImageLib);
        logger.debug("Adding global image location in imagelib : " + globalImageLib);
        logger.info("Image path : " + ImagePath.getPaths().toString());
    }
    public void setImageLib(String imageFileRaw)
    {
        String smartFileLocation = imageLibPath + "//" + imageFileRaw;
        String globalFileLocation =  globalImageLib + "//" + imageFileRaw;
        File file = new File(smartFileLocation);
        File gFile = new File(globalFileLocation);
        if(file.exists() && !file.isDirectory()) {
            imageFile=smartFileLocation;
        }
        else if(gFile.exists() && !gFile.isDirectory())
        {
            imageFile=globalFileLocation;
        }
        else
        {
            imageFile=imageFileRaw;
        }
        setImageLib();
    }
    public void saveScreenToFile(String fileName)
    {
        Screen screen = new Screen();
        screen.saveCapture(fileName);
    }
    public void captureActual(String fileName,Screen screen) throws IOException {
        actual = screen.capture();
        String newFile = actual.getFile();
        FileUtils.copyFile(new File(newFile), new File(fileName));
        logger.info("Sikuli captured screen and saved to : " + fileName );
    }

    public boolean compareImages(String subImage,String superImage) throws IOException {
        FileUtils.copyFile(new File(subImage), new File(screenshotLocation + "//" + testcaseId + "_actual.png"));
        FileUtils.copyFile(new File(superImage), new File(screenshotLocation + "//" + testcaseId + "_expected.png"));
        Finder supImg = new Finder(superImage);

        Pattern subImg = new Pattern(subImage);
        supImg.find(subImg);
        Boolean result = false;
        if (supImg.hasNext())
        {
            result = true;
        }
        else {
            result = false;
        }
        logger.info("Sikuli image comparision for sub-image : " + subImage + " and super-image " + superImage + " : found to be : " + result);
        return result;
    }
}
