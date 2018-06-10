package com.qa.automation;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GlueCode {

    private WebDriver driver;
    private WebDriverWait wait ;
    private String no_of_products ;
    private String product_name ;
    private String product_price ;
    Utility utl = new Utility();

    @Before
    public void beforemethod(Scenario scenario ){
        System.out.println("scenario name is :  "+ scenario.getName());
       // System.setProperty("webdriver.gecko.driver", "C:\\visa\\qa-automation\\geckodriver.exe");
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +"geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("marionette", false);
        driver = new FirefoxDriver(options);


    }
    @After
    public void aftermethod(Scenario scn){
        if(scn.isFailed()){
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scn.embed(screenshot, "image/png");
        }
        driver.quit();
    }

    @Given("^Open the web application url$")
    public void open_the_url() throws Throwable {

        String url = utl.getPropeties("URL");
        driver.get(url);
        System.out.println(driver.getTitle());
        Assert.assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more",driver.getTitle() );

    }

    @Given("^Enter the valid application username$")
    public void enter_valid_uid() throws Throwable {

        driver.findElement(By.id("nav-link-accountList")).click();
        wait = new WebDriverWait(driver,30);

        //WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("item")));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("ap_email"))));

        driver.findElement(By.id("ap_email")).sendKeys( utl.getPropeties("USERNAME"));
        driver.findElement(By.id("continue")).click();


    }

    @Given("^Enter the invalid application username$")
    public void enter_invalid_uid() throws Throwable {

        driver.findElement(By.id("nav-link-accountList")).click();
        wait = new WebDriverWait(driver,30);

        //WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("item")));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("ap_email"))));
        driver.findElement(By.id("ap_email")).sendKeys( utl.getPropeties("INVALIDUSRNAME"));
        driver.findElement(By.id("continue")).click();


    }


    @Given("^Enter the valid application password$")
    public void enter_valid_pwd() throws Throwable {

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("ap_password"))));
        driver.findElement(By.id("ap_password")).sendKeys(utl.getPropeties("PASSWORD"));
    }
    @Given("^Enter the invalid application password$")
    public void enter_invalid_pwd() throws Throwable {

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("ap_password"))));
        driver.findElement(By.id("ap_password")).sendKeys(utl.getPropeties("INVALIDPASSWORD"));
    }

    @Then("^Click on Login$")
    public void click_on_Login() throws Throwable {

        driver.findElement(By.id("signInSubmit")).click();
    }

    @Then("^verify sucessfull Login$")
    public void verify_sucessfull_Login() throws Throwable {

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("nav-link-accountList"))));
        System.out.println(driver.findElement(By.id("nav-link-accountList")).getText());
        //  Assert.assertEquals("Hello, veena\n" +
        //         "Account & Lists", driver.findElement(By.id("nav-link-accountList")).getText());

        String text = driver.findElement(By.id("nav-link-accountList")).getText();

        //Assert.assertTrue(text.contains("veena"));
        Assert.assertFalse(text.contains("Hello. Sign in"));

    }


    @Then("^verify incorrect email id error message$")
    public void verify_incorrect_email_id(){

        String text = driver.findElement(By.className("a-list-item")).getText();
        System.out.println(text);
        Assert.assertTrue(text.contains("We cannot find an account with that email address"));
    }

    @Then("^verify incorrect pwd error message$")
    public void verify_incorrect_pwd(){
        String text = driver.findElement(By.className("a-list-item")).getText();
        System.out.println(text);
        Assert.assertEquals("Your password is incorrect" , text);

    }

    @Then("^search for \"([^\"]*)\" and select the first product displayed$")
    public void search_for_and_select_the_first_product_displayed(String product) throws Throwable {

        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(product);
        driver.findElement(By.className("nav-input")).click();
        //Thread.sleep(5000);
        wait = new WebDriverWait(driver,120);
        wait.until(ExpectedConditions.visibilityOf( driver.findElement(By.xpath("//h2[@data-max-rows='0']"))));

        //h2[@data-max-rows='0']

       // System.out.println("waitign done");
        //   driver.findElement(By.id("result_0")).click();
          driver.findElement(By.xpath("//h2[@data-max-rows='0']")).click();
       // driver.findElement(By.cssSelector("[data-max-rows=\"2\"]")).click();
        System.out.println("click done");
        //Thread.sleep(5000);


    }

    @Then("^Capture product name and price$")
    public void capture_product_name_and_price() throws Throwable {

        product_name = driver.findElement(By.id("productTitle")).getText();
        product_price = driver.findElement(By.id("priceblock_ourprice")).getText();

    }

    @Then("^add \"([^\"]*)\" quantity to basket$")
    public void add_quantity_to_basket(String quantity) throws Throwable {

        //int i = Integer.parseInt(quantity);

        if( isElementPresent("quantity")) {
            Select sel = new Select(driver.findElement(By.id("quantity")));
            sel.selectByVisibleText(quantity);
            no_of_products = quantity ;
        }
        else {
            no_of_products = "1" ;
        }

        driver.findElement(By.id("add-to-cart-button")).click();

    }

    private boolean isElementPresent(String idname){

        try {
            return driver.findElement(By.id(idname)).isDisplayed() ;

        }

        catch (NoSuchElementException e){
            System.out.println(" No such element found" + e.toString() );
            return false ;
        }
        catch (Exception e){
            System.out.println("exception in finding element" + e.toString() );
            return false ;
        }


    }

    @Then("^Validate the product is added into basket on product page$")
    public void validate_the_product_is_added_into_basket_on_product_page() throws Throwable {

        wait = new WebDriverWait(driver,60);
        wait.until(ExpectedConditions.visibilityOf( driver.findElement(By.id("huc-v2-order-row-messages"))));
        String cartMessage = driver.findElement(By.id("huc-v2-order-row-messages")).getText();
        Assert.assertTrue(cartMessage.contains("Added to Cart") );

        String basket_product = driver.findElement(By.id("nav-cart-count")).getText();
        Assert.assertEquals(no_of_products , basket_product);

    }

    @Then("^Click on basket and validate product name and price in basket page$")
    public void click_on_basket_and_validate_product_name_and_price_in_basket_page() throws Throwable {

        driver.findElement(By.id("nav-cart-count")).click();

        wait = new WebDriverWait(driver,120);
        wait.until(ExpectedConditions.visibilityOf( driver.findElement(By.cssSelector(".sc-product-title"))));
        String pname = driver.findElement(By.cssSelector(".sc-product-title")).getText();
        String pprice = driver.findElement(By.cssSelector(".sc-product-price")).getText();

        Assert.assertEquals(product_name ,pname );
        Assert.assertEquals(product_price ,pprice );

    }

    @Then("^Logout and Login again to valdiate the product is still available in basket$")
    public void logout_and_Login_again_to_valdiate_the_product_is_still_available_in_basket() throws Throwable {


        Actions builder = new Actions(driver);
        WebElement element = driver.findElement(By.id("nav-link-accountList"));
        builder.moveToElement(element).build().perform();

        //Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf( driver.findElement(By.id("nav-item-signout-sa"))));

        driver.findElement(By.id("nav-item-signout-sa")).click();
        //Thread.sleep(5000);
        wait = new WebDriverWait(driver,120);
        wait.until(ExpectedConditions.visibilityOf(  driver.findElement(By.id("ap_email"))));

        driver.findElement(By.id("ap_email")).sendKeys(utl.getPropeties("USERNAME"));
        driver.findElement(By.id("continue")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("ap_password"))));
        driver.findElement(By.id("ap_password")).sendKeys(utl.getPropeties("PASSWORD"));

        driver.findElement(By.id("signInSubmit")).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("nav-link-accountList"))));
        System.out.println(driver.findElement(By.id("nav-link-accountList")).getText());

        String text = driver.findElement(By.id("nav-link-accountList")).getText();
        Assert.assertFalse(text.contains("Hello. Sign in"));

        driver.findElement(By.id("nav-cart-count")).click();
        // Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".a-size-medium.sc-product-title.a-text-bold"))));
        String pname = driver.findElement(By.cssSelector(".a-size-medium.sc-product-title.a-text-bold")).getText();
        String pprice = driver.findElement(By.cssSelector(".a-size-medium.sc-white-space-nowrap")).getText();
        String basket_product = driver.findElement(By.id("nav-cart-count")).getText();

        //deleting product from basket so test can execute next time
        driver.findElement(By.xpath("//input[@value='Delete']")).click();

        Assert.assertEquals(product_name ,pname );
        Assert.assertEquals(product_price ,pprice );
        Assert.assertEquals(no_of_products , basket_product);


    }

}
