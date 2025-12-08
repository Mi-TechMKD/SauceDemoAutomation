package saucedemotests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import pages.ProductsPage;

import static org.junit.Assert.*;

public class LoginPageTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
    }

    @Test
    public void succesfulLoginTest() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");

        loginPage.clickLogin();
        // both methods validating the technically same thing
        assertEquals("Products", productsPage.productsTextDisplayed());
        assertTrue(productsPage.isProductsPageDisplayed());
    }

    @Test
    public void loginFromInitialStateUITest() {
        assertEquals("14px", loginPage.getUsernameFontSize());
        assertEquals("\"DM Sans\", Arial, Helvetica, sans-serif", loginPage.getUsernameFontType());

        assertEquals("14px", loginPage.getPasswordFontSize());
        assertEquals("\"DM Sans\", Arial, Helvetica, sans-serif", loginPage.getPasswordFontType());

        assertEquals("Login", loginPage.getLoginButtonText());
        assertEquals("\"DM Sans\", Arial, Helvetica, sans-serif", loginPage.getLoginButtonFontType());
        assertEquals("16px", loginPage.getLoginButtonFontSize());
        assertEquals("#3ddc91", loginPage.getLoginButtonColor());
    }

    @Test
    public void errorMessageEmptyUsernameAndPasswordTest() {
        loginPage.clickLogin();

        assertEquals("Epic sadface: Username is required", loginPage.getErrorMessage());
    }

    @Test
    public void errorMessageValidUsernameAndInvalidPasswordTest() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("123");
        loginPage.clickLogin();
        assertEquals("Epic sadface: Username and password do not match any user in this service",
                loginPage.getErrorMessage());
    }

    @Test
    public void errorMessageValidUsernameAndEmptyPasswordTest() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("");
        loginPage.clickLogin();
        assertEquals("Epic sadface: Password is required",
                loginPage.getErrorMessage());
    }

    @Test
    public void errorMessageInvalidUsernameAndEmptyPasswordTest() {
        loginPage.enterUsername("invalid_username");

        loginPage.clickLogin();

        assertEquals("Epic sadface: Password is required", loginPage.getErrorMessage());
    }

    @Test
    public void removingErrorMessageTest() {
        loginPage.clickLogin();

        loginPage.clickErrorMessageXButton();
        assertFalse(loginPage.isErrorMessageDisplayed());
    }

    @Test
    public void emptyUsernameTest() {
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        assertEquals("Epic sadface: Username is required", loginPage.getErrorMessage());
    }

    @Test
    public void emptyPasswordTest() {
        loginPage.enterUsername("standard_user");
        loginPage.clickLogin();
        assertEquals("Epic sadface: Password is required", loginPage.getErrorMessage());
    }


    @Test
    public void invalidPasswordTest() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("wrong_password");
        loginPage.clickLogin();
        assertEquals("Epic sadface: Username and password do not match any user in this service",
                loginPage.getErrorMessage());
    }


    @After
    public void tearDown() {
        driver.quit();
    }
}
