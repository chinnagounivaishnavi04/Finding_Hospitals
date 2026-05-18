Feature: Diagnostics Page City Extraction

  Scenario: Capture and display top cities from the Diagnostics dashboard
    Given the user is on the Diagnostics page
    When the user identifies all names in the Top Cities section
    And stores these city names in a List
    Then the system should display the captured List of cities in the console output