Feature: Verfiy product search in the website

@ProductSearch
Scenario: Verify successfull Login for amazon website
Given Open the web application url
And Enter the valid application username
And Enter the valid application password
Then Click on Login
Then verify sucessfull Login
Then search for "iphone * 64 gb" and select the first product displayed
Then Capture product name and price
Then add "1" quantity to basket
Then Validate the product is added into basket on product page
Then Click on basket and validate product name and price in basket page
Then Logout and Login again to valdiate the product is still available in basket



