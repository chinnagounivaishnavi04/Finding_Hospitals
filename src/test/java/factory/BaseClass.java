package factory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseClass {

    // ✅ Thread-safe driver
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static Properties p;
    private static Logger logger;

    // ✅ Get Driver
    public static WebDriver getDriver() {
        return driver.get();
    }

    // ✅ Set Driver
    public static void setDriver(WebDriver d) {
        driver.set(d);
    }

    // ✅ Initialize Browser
    public static WebDriver initializeBrowser() throws IOException {

        p = getProperties();

        String executionEnv = p.getProperty("execution_env");
        String browser = p.getProperty("browser").toLowerCase();
        String os = p.getProperty("os").toLowerCase();

        WebDriver d = null;

        if (executionEnv.equalsIgnoreCase("remote")) {

            DesiredCapabilities capabilities = new DesiredCapabilities();

            // ✅ OS handling
            switch (os) {
                case "windows":
                    capabilities.setPlatform(Platform.WINDOWS);
                    break;
                case "mac":
                    capabilities.setPlatform(Platform.MAC);
                    break;
                case "linux":
                    capabilities.setPlatform(Platform.LINUX);
                    break;
            }

            // ✅ Browser handling
            switch (browser) {

                case "chrome":
                    ChromeOptions chOptions = new ChromeOptions();
                    chOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                    chOptions.addArguments("--disable-notifications");
                    capabilities.merge(chOptions);
                    break;

                case "edge":
                    capabilities.setBrowserName("MicrosoftEdge");
                    break;

                case "firefox":
                    capabilities.setBrowserName("firefox");
                    break;
            }

            d = new RemoteWebDriver(
                    new URL("http://localhost:4444/wd/hub"),
                    capabilities
            );

        } else if (executionEnv.equalsIgnoreCase("local")) {

            switch (browser) {

                case "chrome":
                    ChromeOptions chOptions = new ChromeOptions();
                    chOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                    chOptions.addArguments("--disable-notifications");
                    d = new ChromeDriver(chOptions);
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    d = new EdgeDriver(edgeOptions);
                    break;

                case "firefox":
                    d = new FirefoxDriver();
                    break;
            }
        }

        // ✅ Set Driver safely
        setDriver(d);

        // ✅ Common browser setup
        if (getDriver() != null) {
            getDriver().manage().deleteAllCookies();
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            getDriver().manage().window().maximize();
        }

        return getDriver();
    }

    // ✅ Load Config Properties
    public static Properties getProperties() throws IOException {

        FileReader file = new FileReader(
                System.getProperty("user.dir")
                        + File.separator + "src"
                        + File.separator + "test"
                        + File.separator + "resources"
                        + File.separator + "config.properties"
        );

        p = new Properties();
        p.load(file);

        return p;
    }

    // ✅ Logger
    public static Logger getLogger() {
        logger = LogManager.getLogger(BaseClass.class);
        return logger;
    }
}