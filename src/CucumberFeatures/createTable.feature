Feature: To test creating a table works when there are no errors

Scenario: Check if user joins new room when there are no errors
Given I am already logged in
And I populate the create table form
And I click on the create table forms submit button
When I click on the new join table button
Then I should be on new game room page

