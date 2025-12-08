package saucedemotests;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import pages.ProductDescriptionPage;
import pages.ProductsPage;

import static org.junit.Assert.*;

public class ProductDescriptionPageTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private ProductDescriptionPage productPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        productsPage = new ProductsPage(driver);

        productsPage.backPackProductTitleClick();

        productPage = new ProductDescriptionPage(driver);
    }

    @Test
    public void addToCartTest() {
        int initialCount = productPage.getCartBadgeCount();

        productPage.clickAddToCart();

        assertEquals("Remove", productPage.getAddRemoveButtonText());
        assertEquals("rgb(226, 35, 26)", productPage.getAddRemoveButtonBorderColor()); // пример red
        assertEquals(initialCount + 1, productPage.getCartBadgeCount());
    }

    @Test
    public void removeFromCartTest() {
        productPage.clickAddToCart();
        int countAfterAdd = productPage.getCartBadgeCount();

        productPage.clickRemove();

        assertEquals("Add to cart", productPage.getAddRemoveButtonText());
        assertEquals("rgb(19, 35, 34)", productPage.getAddRemoveButtonBorderColor()); // пример grey
        assertEquals(countAfterAdd - 1, productPage.getCartBadgeCount());
    }

    @Test
    public void backToProductsTest() {
        productPage.clickBackToProducts();
        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://www.saucedemo.com/inventory.html", currentUrl);
    }

    @Test
    public void productInformationTest() {
        assertEquals("Sauce Labs Backpack", productPage.getProductName());
        assertTrue(productPage.isProductImageDisplayed());
        assertEquals("carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.", productPage.getProductDescription());
        assertEquals("$29.99", productPage.getProductPrice());
    }
    @After
    public void tearDown(){
        driver.quit();
    }
}