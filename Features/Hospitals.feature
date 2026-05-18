Feature: Hospital Search Functionality
  As a user in Bangalore
  I want to find hospitals that meet specific criteria
  So that I can access emergency care with convenient facilities

  Scenario: Search for high-rated 24/7 hospitals with parking
    Given the user should open the application and search page for "Bangalore"
    And the user should choose the "Hospital"
    When the user enter a minimum rating of open  and display results


