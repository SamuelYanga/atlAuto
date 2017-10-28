@all
Feature: login

Background:
	Given Open home page.

@loginSuccess
Scenario Outline: ABO user login success
    And Login with ABO user. username="<userName>" password="<password>"
    Examples:
    |userName		|password	|
    |2234518		|123456		|
