package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HospitalsPage extends BasePage {

    private WebDriverWait wait;

    // Locators
    @FindBy(className = "c-omni-searchbox--small")
    public WebElement location;

    @FindBy(xpath = "//*[contains(text(),'Bangalore')]")
    public WebElement select;

    @FindBy(xpath = "//*[@placeholder='Search doctors, clinics, hospitals, etc.']")
    public WebElement searchhospital;

    @FindBy(xpath = "//*[@class=\"c-omni-suggestion-item__content\"]")
    public List<WebElement> hospitalSuggestions;

    @FindBy(className = "c-estb-card")
    private List<WebElement> hospitalCards;

    @FindBy(xpath = "//*[@id=\"container\"]/div/div[2]/div[1]/div[1]/div[2]/div/div[1]/span/a/i")
    public WebElement navigate_back;

    // Constructor
    public HospitalsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private void jsClick(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            System.err.println("JS Click failed: " + e.getMessage());
        }
    }

    public void setLocation(String name) {
        wait.until(ExpectedConditions.visibilityOf(location));
        location.click();
        location.clear();
        location.sendKeys(name);
    }

    public void setSelect() {
        wait.until(ExpectedConditions.visibilityOf(select));
        jsClick(select);
    }

    public void setSearchhospital(String Hname) {
        wait.until(ExpectedConditions.visibilityOf(searchhospital));
        searchhospital.sendKeys(Hname);
    }

    public void selectHospitalFromList() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(hospitalSuggestions));

            boolean targetClicked = false;
            for (WebElement item : hospitalSuggestions) {
                String text = item.getText().trim();
                if (text.equalsIgnoreCase("Hospital")) {
                    // Wait for element to be genuinely clickable
                    wait.until(ExpectedConditions.elementToBeClickable(item));
                    jsClick(item);
                    targetClicked = true;
                    break;
                }
            }
            if (!targetClicked) {
                System.out.println("Target option 'Hospital' not explicitly clicked. Sending ENTER key...");
                searchhospital.sendKeys(org.openqa.selenium.Keys.ENTER);
            }
        } catch (Exception e) {
            System.out.println("Suggestions overlay issue encountered. Forcing search submission via ENTER key.");
            try {
                searchhospital.sendKeys(org.openqa.selenium.Keys.ENTER);
            } catch (Exception ex) {
                System.err.println("Could not submit search field: " + ex.getMessage());
            }
        }
    }

    public List<String> setHospitalcards() {
        List<String> matchingNames = new java.util.ArrayList<>();

        try {
            wait.until(ExpectedConditions.elementToBeClickable(navigate_back));
            Thread.sleep(2000);
            System.out.println("Found " + hospitalCards.size() + " total hospital cards on page to analyze.");

            if (hospitalCards.isEmpty()) {
                return matchingNames;
            }

            for (WebElement card : hospitalCards) {
                String value = card.getText();
                String[] lines = value.split("\n");
                String hospitalName = lines[0];

                if (value.toLowerCase().contains("24x7") || value.contains("24/7")) {
                    for (String line : lines) {
                        if (line.matches("^[0-9]\\.[0-9].*")) {
                            try {
                                String ratingRaw = line.split(" ")[0].trim();
                                double ratingValue = Double.parseDouble(ratingRaw);

                                if (ratingValue > 3.5) {
                                    matchingNames.add(hospitalName);
                                    break;
                                }
                            } catch (Exception e) {
                                // Skip card error metrics safely
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Gracefully handled results page parsing lag: " + e.getMessage());
        }
        return matchingNames;
    }

    public void setNavigate_back(){
        wait.until(ExpectedConditions.elementToBeClickable(navigate_back)).click();
    }
}