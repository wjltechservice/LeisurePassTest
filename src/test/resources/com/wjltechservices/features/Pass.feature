Feature: Passes
  Passes are able to be bought, renwed and cancelled by customers and can be validated by vendors

  Background:
    Given I have a vendor in the system called Thorpe Park
    And I have a customer in the system Joe Bloggs from London

  Scenario Outline: Adding a new pass
  A customer should be able to purchase a new pass for an attraction

    When the customer purchases a new <Duration> day pass for Thorpe Park in <City>
    Then they are given their unique Pass ID

    Examples:
      | Duration | City       |
      | 1        | Manchester |
      | 7        | London     |
      | 30       | Cardiff    |

  Scenario Outline: Renewing a pass
  A customer should be able to renew a pass they have already purchased, making it valid again for the duration

    Given the customer has an expired <Duration> day pass for Thorpe Park
    When the customer renews their pass
    Then the pass is valid for another <Duration> days

    Examples:
      | Duration |
      | 1        |
      | 7        |
      | 30       |

  Scenario: Validating a pass
  A vendor should be able to check whether a pass is currently valid for their attraction

    Given the customer has a valid 1 day pass for Thorpe Park in London
    When Thorpe Park validate the pass for the London attraction
    Then they are told the pass is valid

  Scenario: Validating a pass
  A vendor should be able to check whether a pass is currently valid for their attraction

    Given the customer has an expired 1 day pass for Thorpe Park in London
    When Thorpe Park validate the pass for the London attraction
    Then they are told the pass is invalid

  Scenario: Cancelling a pass
  A customer should be able to cancel a pass that they have bought

    Given the customer has a valid 1 day pass for Thorpe Park in London
    Then they are able to cancel the pass