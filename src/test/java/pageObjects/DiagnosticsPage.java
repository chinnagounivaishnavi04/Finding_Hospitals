package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticsPage extends BasePage {

    private WebDriverWait wait;

    @FindBy(xpath="//*[@id=\"root\"]/div/div/footer/div/div[1]/div[2]/div[2]/a[4]/span")
    public WebElement scroll;

    @FindBy(xpath = "//span[text()='Book Diagnostic Tests']/parent::a")
    public WebElement clickdignostic;

    @FindBy(xpath = "//div[text()='TOP CITIES']/following-sibling::ul/li")
    public List<WebElement> cities;

    // Constructor
    public DiagnosticsPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void setScroll(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scroll);
    }

    public void setClickdignostic(){
        wait.until(ExpectedConditions.elementToBeClickable(clickdignostic)).click();
    }

    public void setCities(){
        wait.until(ExpectedConditions.visibilityOfAllElements(cities));
    }

    public List<String> getTopcitynames(){
        List<String> citylist = new ArrayList<>();
        for(WebElement city : cities){
            String name = city.getText().trim();
            if(!name.isEmpty()){
                citylist.add(name);
            }
        }
        return citylist;
    }
}