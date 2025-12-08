package pagesTests;

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

public class YourCartPageTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private YourCartPage yourCartPage;
    private Header header;
    private CheckoutYourInformationPage checkoutYourInformationPage;



    @Before
    public void SetUp() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        loginPage = new LoginPage(driver);
        header = new Header(driver);
        productsPage = new ProductsPage(driver);
        yourCartPage = new YourCartPage(driver);
        checkoutYourInformationPage = new CheckoutYourInformationPage(driver, header);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");

        loginPage.clickLogin();

    }

    @Test
    public void yourCartPageTitleDisplayedTest() {
        header.clickYourCartIcon();
        assertEquals("Your Cart", yourCartPage.yourCartPageDisplayed());
    }

    @Test
    public void quantityLabelDisplayedTest() {
        header.clickYourCartIcon();
        yourCartPage.quantityLabelDisplayed();
        assertTrue(yourCartPage.quantityLabelDisplayed());
    }

    @Test
    public void descriptionLabelDisplayedTest() {
        header.clickYourCartIcon();
        yourCartPage.descriptionLabelDisplayed();
        assertTrue(yourCartPage.descriptionLabelDisplayed());
    }

    @Test
    public void validateBackpackRemoveButtonIsVisible() {
        productsPage.clickAddToCartButtonBackpack();
        header.clickYourCartIcon();

        assertTrue(yourCartPage.getRemoveBackpackButton().isDisplayed());
    }

    @Test
    public void backpackRemoveButtonTest() {
        productsPage.clickAddToCartButtonBackpack();
        header.clickYourCartIcon();

        int before = yourCartPage.getCartItems().size();

        yourCartPage.getRemoveBackpackButton().click();

        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(
                By.className("cart_item"), before));

        int after = yourCartPage.getCartItems().size();

        assertEquals(before - 1, after);
    }


    @Test
    public void removeMultipleProductsTest() {
        productsPage.isProductsPageDisplayed();
        productsPage.clickAddToCartButtonBackpack();
        productsPage.clickAddToCartButtonBikeLight();

        header.clickYourCartIcon();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("cart_item"), 2));

        int before = yourCartPage.getCartItems().size();
        assertEquals(2, before);

        yourCartPage.removeItemsByIds("remove-sauce-labs-backpack", "remove-sauce-labs-bike-light");

        int after = yourCartPage.getCartItems().size();
        assertEquals(0, after);
    }

    @Test
    public void clickContinueShoppingTest() {
        header.clickYourCartIcon();
        yourCartPage.continueShoppingButton();
        productsPage.isProductsPageDisplayed();
        assertEquals("Products", productsPage.productsTextDisplayed());
    }

    @Test
    public void checkoutButtonTest() {
        header.clickYourCartIcon();

        assertTrue(checkoutYourInformationPage.isPageDisplayed());
    }

    @Test
    public void colorOfTheCheckoutButtonTest() {
        header.clickYourCartIcon();
        assertEquals("#3ddc91", yourCartPage.colorOFTheCheckoutButton());
        assertEquals("#ffffff", yourCartPage.colorOFTheContinueShoppingButton());
    }

    @Test
    public void colorOFTheContinueShoppingButtonTest() {
        header.clickYourCartIcon();
        assertEquals("#3ddc91", yourCartPage.colorOFTheCheckoutButton());
        assertEquals("#ffffff", yourCartPage.colorOFTheContinueShoppingButton());
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }

}
