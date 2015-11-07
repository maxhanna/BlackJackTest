Feature: To test login form works when there are no errors

Scenario: Check login form is validated when there are no errors
Given I am on my Blackjack website
When I click on the username form
And I populate the username form
Then I should be on join game room page

Scenario: Check if user joins room when there are no errors
Given I have already logged in
When I click on the join table button
Then I should be on game room page

