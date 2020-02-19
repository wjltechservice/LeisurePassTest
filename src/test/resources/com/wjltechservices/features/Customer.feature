Feature: Customers
  Customers represent pass holders who need to purchase new, or renew old passes

  Scenario Outline: Adding a new customer
  I should be able to add multiple customers to the system and store the required data

    When I add a new customer <Full Name> from <Home City>
    Then I am returned a numeric Customer ID

    Examples:
      | Full Name  | Home City |
      | Joe Bloggs | London    |
      | Jim Anyone | Cardiff   |

  Scenario Outline: Adding a customer which already exists
  If I add a customer which already exists in the system I should be returned their customer ID

    Given I have a customer in the system <Full Name> from <Home City>
    When I add a new customer <Full Name> from <Home City>
    Then I am returned the Customer ID for the existing customer

    Examples:
      | Full Name  | Home City |
      | Joe Bloggs | London    |
      | Jim Anyone | Cardiff   |

  Scenario Outline: Querying an existing customer
  If a customer already exists in the system I should be able to return its information using their Customer ID

    Given I have a customer in the system <Full Name> from <Home City>
    When I query for the customer using their Customer ID
    Then I am provided with the Customer Details <Customer Details>

    Examples:
      | Full Name  | Home City | Customer Details                                                         |
      | Joe Bloggs | London    | { 'customerId': <GENERATED>, 'customerName': 'Joe Bloggs', 'homeCity': 'London' }  |
      | Jim Anyone | Cardiff   | { 'customerId': <GENERATED>, 'customerName': 'Jim Anyone', 'homeCity': 'Cardiff' } |