package saucedemotests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.Header;
import pages.LoginPage;
import pages.ProductsPage;
import pages.YourCartPage;

import static org.junit.Assert.*;

public class HeaderTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private YourCartPage yourCartPage;
    private Header header;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        header = new Header(driver);
        yourCartPage = new YourCartPage(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
    }

    @Test
    public void getPageTitleTest() {
        String title = header.getPageTitle();
        assertEquals("Swag Labs", title);
    }

    @Test
    public void isSidebarVisibleTest() {
        header.clickMenuButton();
        assertTrue(header.isSidebarVisible());
    }

    @Test
    public void menuButtonOpensSidebarTest() {
        header.clickMenuButton();
        assertTrue(header.isSidebarVisible());
    }

    @Test
    public void clickAllItemsLinkTest() {
        header.clickAllItemsLink();
        assertEquals("Products", productsPage.productsTextDisplayed());
    }

    @Test
    public void clickAboutLinkTest() {
        header.clickAboutLink();
        assertTrue(driver.getCurrentUrl().contains("saucelabs.com"));
    }

    @Test
    public void clickLogoutLinkTest() {
        header.clickLogoutLink();
        assertTrue(driver.findElement(By.id("user-name")).isDisplayed());
        assertEquals("https://www.saucedemo.com/", driver.getCurrentUrl());
    }

    @Test
    public void clickResetAppStateLinkTest() {
        productsPage.clickAddToCartButtonBackpack();
        productsPage.clickAddToCartButtonBikeLight();
        assertEquals("2", header.getCartItemsCount());

        header.clickResetAppStateLink();
        assertEquals("0", header.getCartItemsCount());
    }

    @Test
    public void closeMenuButtonTest() {
        header.clickMenuButton();
        header.clickCloseMenuButton();
        assertFalse(header.isSidebarVisible());
    }

    @Test
    public void clickYourCartIconTest() {
        productsPage.clickAddToCartButtonBackpack(); // додај барем 1 производ
        header.clickYourCartIcon();
        assertTrue(driver.getCurrentUrl().contains("cart.html")); // проверка дека се отвори кошничка
    }

    @Test
    public void isYourCartIconDisplayedTest() {
        assertTrue(header.isYourCartIconDisplayed());
    }

    @Test
    public void getCartItemsCountTest() {
        assertEquals("0", header.getCartItemsCount());
        productsPage.clickAddToCartButtonBackpack();
        assertEquals("1", header.getCartItemsCount());
    }


    @After
    public void tearDown() {
        driver.quit();
    }
}