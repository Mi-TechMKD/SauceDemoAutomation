package saucedemotests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.time.Duration;

import static org.junit.Assert.*;


public class CheckoutCompletePageTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private Header header;
    private ProductsPage productsPage;
    private YourCartPage yourCartPage;
    private CheckoutYourInformationPage checkoutYourInformationPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;

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
        checkoutOverviewPage.clickFinish();

        checkoutCompletePage = new CheckoutCompletePage(driver, header);
    }

    @Test
    public void verifyPageLoadsCorrectly() {
        assertEquals("Checkout: Complete!", driver.findElement(By.cssSelector("span.title")).getText());
    }

    @Test
    public void verifyPonyImageVisible() {
        assertTrue(checkoutCompletePage.isPonyVisible());
    }

    @Test
    public void verifyFinalMessageDisplayed() {
        assertTrue(checkoutCompletePage.isFinalMessageDisplayed());
    }

    @Test
    public void verifyConfirmationMessageText() {
        String headerMessage = checkoutCompletePage.getHeaderMessage();
        assertEquals("Thank you for your order!", headerMessage);

        String completeTextMessage = checkoutCompletePage.getCompleteTextMessage();
        assertEquals(
                "Your order has been dispatched, and will arrive just as fast as the pony can get there!",
                completeTextMessage
        );
    }



    @Test
    public void verifyBackHomeButtonColor() {
        String buttonColor = checkoutCompletePage.getBackHomeButtonColor();
        assertNotNull(buttonColor);
        assertTrue(buttonColor.contains("rgba") || buttonColor.contains("rgb"));
    }

    @Test
    public void clickBackHomeButton() {
        checkoutCompletePage.clickBackHome();
        assertEquals("Products", driver.findElement(By.cssSelector("span.title")).getText());
    }

    @Test
    public void verifyEmptyCartAfterCheckout() {
        checkoutCompletePage.clickBackHome();
        assertEquals("0", header.getCartItemsCount());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}