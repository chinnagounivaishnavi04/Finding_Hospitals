package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class WellnessPage extends BasePage {

    private Actions actions;
    private WebDriverWait wait;

    @FindBy(xpath = "/html/body/div/div/div/div[1]/div[1]/div[2]/div/div[3]/div[1]/span/span[2]")
    public WebElement corporates;

    @FindBy(xpath = "//a[contains(text(),'Wellness Plans') or contains(text(),'Health & Wellness')]")
    public WebElement clickWellness;

    @FindBy(xpath = "//input[@placeholder='Name']")
    public WebElement name;

    @FindBy(xpath = "//input[@placeholder='Organization Name']")
    public WebElement orgname;

    @FindBy(xpath = "//input[@placeholder='Contact Number']")
    public WebElement contnum;

    @FindBy(xpath = "//input[@placeholder='Official Email ID']")
    public WebElement email;

    @FindBy(id = "organizationSize")
    public WebElement scrollto;

    @FindBy(id = "organizationSize")
    public WebElement orgele;

    @FindBy(xpath = "//*[@id=\"interestedIn\"]")
    public WebElement intrestele;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/header[2]/div[2]/div/form/button")
    public WebElement schedulebu;

    @FindBy(xpath = "//*[contains(text(),'valid') or contains(text(),'Invalid')]")
    private List<WebElement> errorMessages;

    @FindBy(xpath = "//input[contains(@class,'error') or contains(@style,'border')]")
    private List<WebElement> invalidFields;

    // Constructor
    public WellnessPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void setCorporates(){
        wait.until(ExpectedConditions.visibilityOf(corporates)).click();
        actions.moveToElement(corporates).perform();
    }

    public void setClickWellness(){
        clickWellness.click();
    }

    public void verifynavigation(){
        wait.until(ExpectedConditions.urlContains("corporate"));
    }

    public void setName(String namee){
        name.click();
        name.sendKeys(namee);
    }

    public void setOrgname(String orgname1){
        orgname.click();
        orgname.sendKeys(orgname1);
    }

    public void setContnum(String contnum1){
        contnum.click();
        contnum.sendKeys(contnum1);
    }

    public void setEmail(String email1){
        email.click();
        email.sendKeys(email1);
    }

    public void setScroll(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scrollto);
    }

    public void setOrgele(String orvalue){
        wait.until(ExpectedConditions.elementToBeClickable(orgele));
        Select select = new Select(orgele);
        select.selectByValue(orvalue);
    }

    public void setIntrestele(){
        wait.until(ExpectedConditions.elementToBeClickable(intrestele));
        Select select = new Select(intrestele);
        select.selectByIndex(1);
    }

    public boolean setSchedulebu(){
        return schedulebu.isEnabled();
    }

    public void clickScheduleButton() {
        schedulebu.click();
    }

    public List<WebElement> getErrorMessages() {
        return errorMessages;
    }

    public int getInvalidFieldsCount() {
        return invalidFields.size();
    }
}