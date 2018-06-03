Feature: Verfiy Login for amazon website

@Postitive
Scenario: Verify successfull Login for amazon website
Given Open the url "https://www.amazon.com"
And Enter the uid "<valid email id of amazon account>"
And Enter the pwd "<password>"
Then Click on Login
Then verify sucessfull Login


@Negative_Email
Scenario: Verify unsuccessfull Login with incorrect username
Given Open the url "https://www.amazon.com"
And Enter the uid "<invalid email id of amazon account>"
Then verify incorrect email id error message

@Negative_Pwd
Scenario: Verify unsuccessfull Login with incorrect password
Given Open the url "https://www.amazon.com"
And Enter the uid "<valid email id of amazon account>"
And Enter the pwd "<invalid password>"
Then Click on Login
Then verify incorrect pwd error message