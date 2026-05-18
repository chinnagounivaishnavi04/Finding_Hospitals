package hooks;

import com.aventstack.extentreports.*;
import factory.BaseClass;
import io.cucumber.java.*;
import org.openqa.selenium.*;

import utilities.ExtentReportManager;

import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

public class Hooks {

    WebDriver driver = BaseClass.getDriver();
    private static Properties p;

    private static ExtentReports extent = ExtentReportManager.getReportInstance();
    private static ExtentTest test;

    @Before
    public void setup(Scenario scenario) throws IOException {

        test = extent.createTest(scenario.getName());

        if (BaseClass.getDriver() == null) {
            driver = BaseClass.initializeBrowser();
            p = BaseClass.getProperties();
        } else {
            driver = BaseClass.getDriver();
            if (p == null) {
                p = BaseClass.getProperties();
            }
        }

        driver.get(p.getProperty("appURL"));
    }

    @AfterStep
    public void addScreenshot(Scenario scenario) {

        WebDriver activeDriver = BaseClass.getDriver();

        try {
            byte[] screenshot = ((TakesScreenshot) activeDriver)
                    .getScreenshotAs(OutputType.BYTES);

            if (scenario.isFailed()) {
                scenario.attach(screenshot, "image/png", scenario.getName());

                test.fail("Step Failed",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(
                                Base64.getEncoder().encodeToString(screenshot)
                        ).build());
            } else {
                test.pass("Step Passed");
            }

        } catch (Exception e) {
            System.out.println("Screenshot error: " + e.getMessage());
        }
    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            test.fail("Scenario Failed");
        } else {
            test.pass("Scenario Passed");
        }

        driver.manage().deleteAllCookies();
    }

    public static void quitDriver() {

        WebDriver driver = BaseClass.getDriver();

        if (driver != null) {
            driver.quit();
        }

        ExtentReportManager.getReportInstance().flush();
    }
}