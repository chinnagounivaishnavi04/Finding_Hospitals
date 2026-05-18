package stepDefinitions;

import factory.BaseClass;
import io.cucumber.java.en.*;
import pageObjects.HospitalsPage;
import java.util.List;
import utilities.LoggerUtil;
import utilities.ScreenshotUtil;
import factory.BaseClass;
// REMOVE the wrong one
// import io.cucumber.messages.types.Exception;

// Java default exception is automatically available, so no import needed

public class HospitalsSteps {

    private HospitalsPage dis;

    @Given("the user should open the application and search page for {string}")
    public void the_user_should_open_the_application_and_search_page_for(String name) {

        LoggerUtil.info("Launching application");

        if (BaseClass.getDriver() == null) {
            try {
                BaseClass.initializeBrowser();
                BaseClass.getDriver().get(BaseClass.getProperties().getProperty("appURL"));
            } catch (Exception e) {
                LoggerUtil.error("Driver initialization failed");
            }
        }

        dis = new HospitalsPage(BaseClass.getDriver());

        LoggerUtil.info("Setting location: " + name);
        dis.setLocation(name);

        // ✅ Capture screenshot
        ScreenshotUtil.captureScreenshot(
                BaseClass.getDriver(),
                "Location_Entered"
        );
    }


    @Given("the user should choose the {string}")
    public void the_user_should_choose_the(String Hname) throws InterruptedException {

        LoggerUtil.info("Searching hospital: " + Hname);

        dis.setSearchhospital(Hname);
        Thread.sleep(4000);
        dis.selectHospitalFromList();

        ScreenshotUtil.captureScreenshot(
                BaseClass.getDriver(),
                "Hospital_Search_Result"
        );
    }

    @When("the user enter a minimum rating of open  and display results")
    public void the_user_enter_a_minimum_rating_of_open_and_display_results() {
        System.out.println("Extracting matching establishment data matrices...");
        List<String> finalHospitalList = dis.setHospitalcards();

        if (finalHospitalList.isEmpty()) {
            System.out.println("RESULT: No hospitals matched the 24/7 and >3.5 rating criteria.");
        } else {
            System.out.println("---MATCHING HOSPITALS FOUND---");
            for (int i = 0; i < finalHospitalList.size(); i++) {
                System.out.println((i + 1) + ". " + finalHospitalList.get(i));
            }
            System.out.println("-----------------------------------");
            System.out.println("Total count: " + finalHospitalList.size());
        }
        dis.setNavigate_back();
    }
}