package utilities;

import org.openqa.selenium.*;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String name) {

        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String path = System.getProperty("user.dir") +
                "/screenshots/" + name + "_" + time + ".png";

        try {
            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(src, new File(path));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return path;
    }
}