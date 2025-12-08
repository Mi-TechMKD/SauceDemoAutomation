package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class YourCartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By yourCartPageDisplayed = By.className("title");
    private By descriptionLabel = By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[2]");
    private By quantityLabel = By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[1]");
    private By continueShoppingButton = By.id("continue-shopping");
    private By checkoutButton = By.id("checkout");
    private By removeBackpackButton = By.id("remove-sauce-labs-backpack");
    private By cartItems = By.className("cart_item");


    public YourCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String yourCartPageDisplayed() {
        return driver.findElement(yourCartPageDisplayed).getText();
    }

    public boolean descriptionLabelDisplayed() {
        return driver.findElement(descriptionLabel).isDisplayed();
    }

    public boolean quantityLabelDisplayed() {
        return driver.findElement(quantityLabel).isDisplayed();
    }

    public WebElement getRemoveBackpackButton() {
        return driver.findElement(removeBackpackButton);
    }

    public List<WebElement> getCartItems() {
        return driver.findElements(cartItems);
    }

    public void removeItemsByIds(String... removeButtonIds) {
        for (String id : removeButtonIds) {
            WebElement removeBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));

            int before = getCartItems().size();

            removeBtn.click();

            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.className("cart_item"), before));
        }
    }

    public void continueShoppingButton() {
        driver.findElement(continueShoppingButton).click();
    }

    public String colorOFTheContinueShoppingButton() {
        Color checkoutButtonColor = Color.fromString(driver.findElement(continueShoppingButton).getCssValue("background-color"));
        return checkoutButtonColor.asHex();
    }

    public void checkoutButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout")));
        btn = wait.until(ExpectedConditions.elementToBeClickable(btn));
        btn.click();
    }

    public String colorOFTheCheckoutButton() {
        Color checkoutButtonColor = Color.fromString(driver.findElement(checkoutButton).getCssValue("background-color"));
        return checkoutButtonColor.asHex();
    }


}