package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class CheckoutOverviewPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By pageTitle = By.className("title");
    private By cartItems = By.className("cart_item");
    private By itemName = By.className("inventory_item_name");
    private By itemDescription = By.className("inventory_item_desc");
    private By itemPrice = By.className("inventory_item_price");
    private By itemQuantity = By.className("cart_quantity");
    private By finishButton = By.id("finish");
    private By cancelButton = By.id("cancel");
    private By summaryTax = By.className("summary_tax_label");
    private By summaryTotal = By.className("summary_total_label");

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }

    public List<WebElement> getCartItems() {
        return driver.findElements(cartItems);
    }

    public int getCartItemCount() {
        return getCartItems().size();
    }

    public String getItemName(int index) {
        return getCartItems().get(index).findElement(itemName).getText();
    }

    public String getItemDescription(int index) {
        return getCartItems().get(index).findElement(itemDescription).getText();
    }

    public String getItemPrice(int index) {
        return getCartItems().get(index).findElement(itemPrice).getText();
    }

    public String getItemQuantity(int index) {
        return getCartItems().get(index).findElement(itemQuantity).getText();
    }

    public String getTax() {
        return driver.findElement(summaryTax).getText();
    }

    public String getTotal() {
        return driver.findElement(summaryTotal).getText();
    }

    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
    }

    public boolean isPageDisplayed() {
        return driver.findElement(pageTitle).isDisplayed();
    }
}