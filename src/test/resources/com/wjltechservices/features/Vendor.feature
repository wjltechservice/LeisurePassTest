Feature: Vendors
  Vendors represent tourism businesses/attractions that offer services for which passes can be purchased

  Scenario Outline: Adding a new vendor
  I can add a vendor which does not already exist to the system

    When I add a new vendor called <Vendor>
    Then <Vendor ID> is returned as the unique vendor ID

    Examples:
      | Vendor         | Vendor ID     |
      | Thorpe Park    | thorpepark    |
      | Alton Towers   | altontowers   |
      | London Dungeon | londondungeon |

  Scenario Outline: Adding a vendor which already exists
  If a vendor already exists in the system, subsequent calls to add that vendor should not fail, but instead
  return the data for the vendor

    Given I have a vendor in the system called <Vendor>
    When I add a new vendor called <Vendor>
    Then <Vendor ID> is returned as the unique vendor ID

    Examples:
      | Vendor         | Vendor ID     |
      | Thorpe Park    | thorpepark    |
      | Alton Towers   | altontowers   |
      | London Dungeon | londondungeon |

  Scenario Outline: Querying an existing vendor
  If a vendor already exists in the system, I should be able to return it using the unique vendor ID

    Given I have a vendor in the system called <Vendor>
    When I query for the vendor using the ID <Vendor ID>
    Then I am provided with the details <Vendor Details>

    Examples:
      | Vendor         | Vendor ID     | Vendor Details                                                  |
      | Thorpe Park    | thorpepark    | { 'vendorId': 'thorpepark', 'vendorName': 'Thorpe Park' }       |
      | Alton Towers   | altontowers   | { 'vendorId': 'altontowers', 'vendorName': 'Alton Towers' }     |
      | London Dungeon | londondungeon | { 'vendorId': 'londondungeon', 'vendorName': 'London Dungeon' } |