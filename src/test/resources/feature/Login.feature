Feature: Verfiy Login for amazon website

@Postitive
Scenario: Verify successfull Login for amazon website
Given Open the web application url
And Enter the valid application username
And Enter the valid application password
Then Click on Login
Then verify sucessfull Login


@Negative_Email
Scenario: Verify unsuccessfull Login with incorrect username
Given Open the web application url
And Enter the invalid application username
Then verify incorrect email id error message

@Negative_Pwd
Scenario: Verify unsuccessfull Login with incorrect password
Given Open the web application url
And Enter the valid application username
And Enter the invalid application password
Then Click on Login
Then verify incorrect pwd error message