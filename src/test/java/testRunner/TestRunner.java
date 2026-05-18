package testRunner;

import hooks.Hooks;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = ".//Features",
        glue = {"stepDefinitions", "hooks"},
        plugin = {
                "pretty",
                "html:reports/cucumber.html"
        },
        monochrome = true
)
public class TestRunner {

    @AfterClass
    public static void closeBrowserProcess() {
        Hooks.quitDriver();
    }
}