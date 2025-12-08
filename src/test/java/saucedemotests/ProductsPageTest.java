package saucedemotests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

public class ProductsPageTest {

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
        yourCartPage = new YourCartPage(driver);
        header = new Header(driver);


        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
    }

    @Test
    public void isProductsPageDisplayedTest() {
        assertTrue(productsPage.isProductsPageDisplayed());
    }

    @Test
    public void testGetAllProductsNames() {
        List<String> names = productsPage.getAllProductsNames();
        assertFalse(names.isEmpty());
    }

    @Test
    public void orderingDropDownValuesTest() {
        assertEquals("Name (A to Z)", productsPage.getAllOptionsFromOrderingDropdown().get(0).getText());
        assertEquals("Name (Z to A)", productsPage.getAllOptionsFromOrderingDropdown().get(1).getText());
        assertEquals("Price (low to high)", productsPage.getAllOptionsFromOrderingDropdown().get(2).getText());
        assertEquals("Price (high to low)", productsPage.getAllOptionsFromOrderingDropdown().get(3).getText());
    }


    @Test
    public void orderingProductsFromHighToLowPriceTest() {
        productsPage.selectOrderingDropdownOption(3);

        assertEquals("Price (high to low)", productsPage.getTextFromOrderingDropdown());
        assertTrue(productsPage.areAllProductsPricesDescending());
    }

    @Test
    public void orderingProductsZtoAAlphabeticallyTest() {
        List<String> initialsNameList = productsPage.getAllProductsNames();

        for (int i = 0; i < initialsNameList.size(); i++) {
            System.out.println(initialsNameList.get(i));
        }

        productsPage.selectOrderingDropdownOption(1);

        List<String> namesListAfterSelectionZtoA = productsPage.getAllProductsNames();

        for (int i = 0; i < namesListAfterSelectionZtoA.size(); i++) {
            System.out.println(namesListAfterSelectionZtoA.get(i));
        }

        assertEquals(initialsNameList.reversed(), namesListAfterSelectionZtoA);
    }

    @Test
    public void areAllProductsPricesAscendingTest() {
        productsPage.selectOrderingDropdownOption(2); // Пример: опцијата “Price (low to high)”

        assertTrue(productsPage.areAllProductsPricesAscending());
    }

    @Test
    public void validateColorChangeOnTitleHoverTest() throws InterruptedException {
//        Thread.sleep(5000);

        assertEquals("#18583a", productsPage.getColorFromBackPackTitle());

        productsPage.hoverBackPackTitle();
//        Thread.sleep(5000);
        assertEquals("#3ddc91", productsPage.getColorFromBackPackTitle());
    }

    @Test
    public void backPackProductTitleClickTest() {
        productsPage.backPackProductTitleClick();

        String newUrl = driver.getCurrentUrl();

        assertTrue(newUrl.contains("inventory-item.html?id=4"));
    }


    @Test
    public void backpackButtonDisplayedTest() {
        WebElement addButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(productsPage.getBackpackAddToCartButton()));
        assertEquals("Add to cart", addButton.getText());
    }

    @Test
    public void removeButtonBackpackDisplayedTest() {
        productsPage.clickAddToCartButtonBackpack();

        assertTrue(productsPage.removeButtonBackpackDisplayed());

        productsPage.clickRemoveButtonBackpack();
    }

    @Test
    public void clickAddToCartButtonBikeLightTest() {
        productsPage.clickAddToCartButtonBikeLight();
        assertTrue(header.isYourCartIconDisplayed());
    }

    @Test
    public void clickProductImageTest() {
        productsPage.clickProductImage("4"); // пример: Backpack
        assertTrue(driver.getCurrentUrl().contains("inventory-item.html"));
    }
    
    @After
    public void tearDown() {
        driver.quit();
    }
}
