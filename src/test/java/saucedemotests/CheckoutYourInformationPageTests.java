package saucedemotests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.time.Duration;

import static org.junit.Assert.*;

public class CheckoutYourInformationPageTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private ProductsPage productsPage;
    private Header header;
    private YourCartPage yourCartPage;
    private CheckoutYourInformationPage checkoutYourInformationPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        productsPage = new ProductsPage(driver);
        header = new Header(driver);
        yourCartPage = new YourCartPage(driver);
        checkoutYourInformationPage = new CheckoutYourInformationPage(driver, header);

        productsPage.clickAddToCartButtonBackpack();
        header.clickYourCartIcon();
        yourCartPage.checkoutButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
    }

    @Test
    public void verifyPageTitleIsCorrect() {
        String actualTitle = checkoutYourInformationPage.getPageTitle();

        assertEquals("Checkout: Your Information", actualTitle);
    }


    @Test
    public void cancelButtonTest() {
        checkoutYourInformationPage.clickCancel();
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/cart.html"));
        assertEquals("Your Cart", driver.findElement(By.className("title")).getText());
    }

    @Test
    public void continueSuccessfulTest() {
        checkoutYourInformationPage.fillAllFields("UserFirstName", "UserLastName", "1000");
        checkoutYourInformationPage.clickContinue();

        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/checkout-step-two.html"));
        assertEquals("Checkout: Overview", driver.findElement(By.className("title")).getText());
    }

    @Test
    public void firstNameErrorTest() {
        checkoutYourInformationPage.enterLastName("UserLastName");
        checkoutYourInformationPage.enterPostalCode("1000");
        checkoutYourInformationPage.clickContinue();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        assertEquals("Error: First Name is required", checkoutYourInformationPage.getErrorMessage());
    }

    @Test
    public void lastNameErrorTest() {
        checkoutYourInformationPage.enterFirstName("UserFirstName");
        checkoutYourInformationPage.enterPostalCode("1000");
        checkoutYourInformationPage.clickContinue();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        assertEquals("Error: Last Name is required", checkoutYourInformationPage.getErrorMessage());
    }

    @Test
    public void postalCodeErrorTest() {
        checkoutYourInformationPage.enterFirstName("UserFirstName");
        checkoutYourInformationPage.enterLastName("UserLastName");
        checkoutYourInformationPage.clickContinue();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        assertEquals("Error: Postal Code is required", checkoutYourInformationPage.getErrorMessage());
    }

    @Test
    public void closeErrorMessageTest() {
        checkoutYourInformationPage.clickContinue();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        assertTrue(checkoutYourInformationPage.isErrorMessageDisplayed());

        checkoutYourInformationPage.clickXErrorMessageButton();
        assertFalse(checkoutYourInformationPage.isErrorMessageDisplayed());
    }

    @Test
    public void refreshBrowserResetsFieldsAndErrorsTest() {
        checkoutYourInformationPage.clickContinue();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        assertTrue(checkoutYourInformationPage.isErrorMessageDisplayed());

        driver.navigate().refresh();

        assertEquals("", checkoutYourInformationPage.getFirstName());
        assertEquals("", checkoutYourInformationPage.getLastName());
        assertEquals("", checkoutYourInformationPage.getPostalCode());
        assertFalse(checkoutYourInformationPage.isErrorMessageDisplayed());
    }

    @Test
    public void cancelAndBackClearsFieldsAndErrorsTest() {
        checkoutYourInformationPage.clickContinue();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        assertTrue(checkoutYourInformationPage.isErrorMessageDisplayed());

        checkoutYourInformationPage.clickCancel();
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/cart.html"));

        yourCartPage.checkoutButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));

        assertEquals("", checkoutYourInformationPage.getFirstName());
        assertEquals("", checkoutYourInformationPage.getLastName());
        assertEquals("", checkoutYourInformationPage.getPostalCode());
        assertFalse(checkoutYourInformationPage.isErrorMessageDisplayed());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}