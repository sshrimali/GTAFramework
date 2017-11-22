package utils.framework;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by sshrimali on 9/2/16.
 */
public class Screenshot extends BaseDriver {
    public Screenshot() throws Exception {
    }


    public String capture() throws Exception {
        setGlobalProperties();
        File screenshotData = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String ScreenshotFile=screenshotLocation + "//" + testcaseId + ".png";
        FileUtils.copyFile(screenshotData, new File(ScreenshotFile));
        String screenShotLink="<b><a href=\"screenshots//" + testcaseId + ".png\" target=\"_blank\">" + "Screenshot : " + testcaseId + ".png" + "</a></b>";
        return screenShotLink;
    }
    public void capture(String path) throws Exception {
        setGlobalProperties();

        File screenshotData = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotData, new File(path));
    }
    public String capture(String element, String path) throws Exception {
        setGlobalProperties();
        WebElement ele = UI.findElement(element);

// Get entire page screenshot
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        BufferedImage  fullImg = ImageIO.read(screenshot);

        //Image before rescaling
        ImageIO.write(fullImg, "png", screenshot);
        FileUtils.copyFile(screenshot, new File(path + "_fullImage_before.png"));

        //fullImg=getScaledImage(fullImg,1024,980);
//Image after rescaling
        ImageIO.write(fullImg, "png", screenshot);

//        String left = UI.executeScript("return document.evaluate('//div[@class=\"pricebox-col\"]',document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.getBoundingClientRect(); ");
//        System.out.println("I am the response from script overall : " + left);
        String top = UI.executeScript("rect= document.evaluate('//div[@class=\"pricebox-col\"]',document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.getBoundingClientRect() ;" +
                "return (rect.left + window.scrollX).toString() + \",\" + (rect.top  + window.scrollY).toString() + \",\" + rect.width.toString() + \",\" + rect.height.toString();");
        System.out.println("I am the response from script : " + top);
        int l=Math.round(Float.valueOf(top.split(",")[0]));
        int t=Math.round(Float.valueOf(top.split(",")[1]));
        int w=Math.round(Float.valueOf(top.split(",")[2]));
        int h=Math.round(Float.valueOf(top.split(",")[3]));
        FileUtils.copyFile(screenshot, new File(path + "_fullImage_after.png"));

// Get the location of element on the page
        Point point = ele.getLocation();

// Get width and height of the element
        int eleWidth = ele.getSize().getWidth();
        int eleHeight = ele.getSize().getHeight();
        System.out.println("Dimentions : \n" + point.getX()+ "\n\t" + point.getY()+ "\n\t"+ eleWidth+ "\n\t" + eleHeight);
        System.out.println("Dimentions of actyal image: : H : \n" + fullImg.getHeight());
        System.out.println("Dimentions of actyal image: W :  \n" + fullImg.getWidth());
        //eleWidth=78;
// Crop the entire page screenshot to get only element screenshot
        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, "png", screenshot);
        FileUtils.copyFile(screenshot, new File(path));
        eleScreenshot= fullImg.getSubimage(l,t,w,h);
        ImageIO.write(eleScreenshot, "png", screenshot);
        FileUtils.copyFile(screenshot, new File(path + "_new.png"));

// Copy the element screenshot to disk
        FileUtils.copyFile(screenshot, new File(path));
//        AShot shot = new AShot();
//        BufferedImage eleScreenshot23=shot.takeScreenshot(driver,ele).getImage();
//        ImageIO.write(eleScreenshot23, "png", new File(path + "_AHSOT.png"));

        return path;
    }
    private BufferedImage getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, Transparency.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        //g2.drawImage(srcImg, 0, 0, 1008, 903, null);
        g2.dispose();
        return resizedImg;
    }
}
