Feature: Check the Onboard CustomerApis
  
  Check the Responce and status code

  Scenario: Create the ValidCustomer
    When Create the PostRequest with Valid Customer
    Then Check the Customer Responce
    And Check Status code

  Scenario: Create the InvalidCustomer
    When Create the PostRequest with InvalidCustomer
   
   

  Scenario: Get data about one Customer
    Given Enter the CustomerId
      | id  |
      | 102 |
      | 105 |
      | 180 |
      | 121 |
