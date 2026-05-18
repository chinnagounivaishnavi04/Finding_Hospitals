Feature: Corporate Wellness Form Validation

  Scenario: Capture warning message for invalid form submission
    Given the user navigates to the Corporate Wellness page
    When the user enters name "12345", email "ram", organization "testing" and phone "parshuu" and orgvalue "<=500"
    And the user clicks the Schedule button
    Then a warning alert should be displayed
    And the user captures the alert message and prints it to the console