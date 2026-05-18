package stepDefinitions;

import factory.BaseClass;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebElement;
import pageObjects.WellnessPage;
import pageObjects.WellnessPage;
//
//import static factory.BaseClass.getDriver();

public class WellnessSteps {
    WellnessPage cwv ;
    boolean isButtonEnabled;

    @Given("the user navigates to the Corporate Wellness page")
    public void the_user_navigates_to_the_corporate_wellness_page() {
        cwv = new WellnessPage(BaseClass.getDriver());
        cwv.setCorporates();
        cwv.setClickWellness();
        cwv.verifynavigation();
        System.out.println("Navigated to Corporate Wellness Page");
    }

    @When("the user enters name {string}, email {string}, organization {string} and phone {string} and orgvalue {string}")
    public void the_user_enters_name_email_organization_and_phone_and_orgvalue_and_intrest(String namee, String orgname1, String contnum1, String email1, String orvalue) {
        cwv.setName(namee);
        cwv.setOrgname(orgname1);
        cwv.setContnum(contnum1);
        cwv.setEmail(email1);
        cwv.setScroll();
        cwv.setOrgele(orvalue);
        cwv.setIntrestele();
    }

    @When("the user clicks the Schedule button")
    public void the_user_clicks_the_schedule_button() {
        isButtonEnabled = cwv.setSchedulebu();
        if (isButtonEnabled) {
            System.out.println("Schedule button is enabled");
            cwv.clickScheduleButton();
        } else {
            System.out.println("Schedule button is not enabled");
        }


    }

    @Then("a warning alert should be displayed")
    public void a_warning_alert_should_be_displayed() {
        if (!isButtonEnabled) {

            System.out.println("\n Validation Errors:");
            // Process and print validation messages
            for (WebElement error : cwv.getErrorMessages()) {
                String msg = error.getText().trim();
                if (!msg.isEmpty()) {
                    System.out.println("➡ " + msg);
                }
            }
        }
    }
    @Then("the user captures the alert message and prints it to the console")
    public void the_user_captures_the_alert_message_and_prints_it_to_the_console () {
        int invalidCount = cwv.getInvalidFieldsCount();
        System.out.println("Invalid Fields Count: " + invalidCount);

    }
}


