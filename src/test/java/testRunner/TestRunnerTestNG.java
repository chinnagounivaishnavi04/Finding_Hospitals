package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = ".//Features",
        glue = {"stepDefinitions", "hooks"},
        plugin = {
                "pretty",
                "html:reports/cucumber.html"
        },
        monochrome = true
)
public class TestRunnerTestNG extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}