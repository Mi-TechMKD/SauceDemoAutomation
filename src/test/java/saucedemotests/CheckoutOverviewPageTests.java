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


public class CheckoutOverviewPageTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private Header header;
    private ProductsPage productsPage;
    private YourCartPage yourCartPage;
    private CheckoutYourInformationPage checkoutYourInformationPage;
    private CheckoutOverviewPage checkoutOverviewPage;

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

        header = new Header(driver);
        productsPage = new ProductsPage(driver);
        yourCartPage = new YourCartPage(driver);
        checkoutYourInformationPage = new CheckoutYourInformationPage(driver, header);
        checkoutOverviewPage = new CheckoutOverviewPage(driver);

        productsPage.clickAddToCartButtonBackpack();
        productsPage.clickAddToCartButtonBikeLight();
        header.clickYourCartIcon();
        yourCartPage.checkoutButton();
        checkoutYourInformationPage.fillAllFields("UserFirstName", "UserLastName", "1000");
        checkoutYourInformationPage.clickContinue();
    }

    @Test
    public void verifyCheckoutOverviewPageTitle() {
        assertEquals("Checkout: Overview", checkoutOverviewPage.getPageTitle());
    }

    @Test
    public void verifyPageIsDisplayed() {
        assertTrue(checkoutOverviewPage.isPageDisplayed());
    }

    @Test
    public void verifyCartItemCount() {
        assertEquals(2, checkoutOverviewPage.getCartItemCount());
    }

    @Test
    public void verifyBackpackItemDetails() {
        assertEquals("Sauce Labs Backpack", checkoutOverviewPage.getItemName(0));
        assertEquals("$29.99", checkoutOverviewPage.getItemPrice(0));
        assertEquals("1", checkoutOverviewPage.getItemQuantity(0));
    }

    @Test
    public void verifyBikeLightItemDetails() {
        assertEquals("Sauce Labs Bike Light", checkoutOverviewPage.getItemName(1));
        assertEquals("$9.99", checkoutOverviewPage.getItemPrice(1));
        assertEquals("1", checkoutOverviewPage.getItemQuantity(1));
    }

    @Test
    public void verifyItemDescriptions() {
        String backpackDesc = checkoutOverviewPage.getItemDescription(0);
        String bikeLightDesc = checkoutOverviewPage.getItemDescription(1);
        assertTrue(backpackDesc.contains("Sly Pack"));
        assertTrue(bikeLightDesc.contains("bike at night"));
    }

    @Test
    public void verifyTaxAndTotal() {
        assertEquals("Tax: $3.20", checkoutOverviewPage.getTax());
        assertEquals("Total: $43.18", checkoutOverviewPage.getTotal());
    }

    @Test
    public void clickFinishButton() {
        checkoutOverviewPage.clickFinish();
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/checkout-complete.html"));
        assertEquals("Checkout: Complete!", driver.findElement(By.className("title")).getText());
    }

    @Test
    public void clickCancelButton() {
        checkoutOverviewPage.clickCancel();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
        assertEquals("Products", driver.findElement(By.className("title")).getText());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}