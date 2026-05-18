package stepDefinitions;

import factory.BaseClass;
import io.cucumber.java.en.*;
import pageObjects.DiagnosticsPage;
import pageObjects.HospitalsPage;

import java.util.List;

//import static factory.BaseClass.driver;


public class DiagnosticsSteps {
    DiagnosticsPage ddn;
    List<String> capturedcities;
    @Given("the user is on the Diagnostics page")
    public void the_user_is_on_the_diagnostics_page() throws InterruptedException {
        ddn = new DiagnosticsPage(BaseClass.getDriver());
        ddn.setScroll();
        ddn.setClickdignostic();
        Thread.sleep(3000);
    }
    @When("the user identifies all names in the Top Cities section")
    public void the_user_identifies_all_names_in_the_top_cities_section() {
        ddn.setCities();
    }
    @When("stores these city names in a List")
    public void stores_these_city_names_in_a_list() {
        capturedcities=ddn.getTopcitynames();

    }
    @Then("the system should display the captured List of cities in the console output")
    public void the_system_should_display_the_captured_list_of_cities_in_the_console_output() {
        System.out.println("------Top Cities------");
        for(String cityName:capturedcities){
            System.out.println(cityName);
        }
        System.out.println("Total Cities: " +capturedcities.size());
    }
}
