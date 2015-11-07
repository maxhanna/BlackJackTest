Feature: To test finishing a game works when there are no errors

Scenario: Check game form is validated when there are no errors
Given I am already logged in
And I am already in a game
When I click on the hit button
And I click on the stay button
Then I should be on the end game page