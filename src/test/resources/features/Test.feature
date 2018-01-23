
@UI
Feature: Open HealthDirect Website and Browse
  Use selenium java with cucumber-jvm and navigate to website

@Screenshot
  Scenario: 
    When I open HealthDirect Website
    And Then I browse "Asthma" in search field
    Then I verify search related to "ASTHMA" is successful
    
    
    
    
    

   
   

    