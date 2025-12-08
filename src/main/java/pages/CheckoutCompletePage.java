package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutCompletePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Header header;

    private By pageTitle = By.cssSelector("span.title");
    private By ponyImage = By.className("pony_express");
    private By finalMessage = By.className("complete-text");
    private By backHomeButton = By.id("back-to-products");

    public CheckoutCompletePage(WebDriver driver, Header header) {
        this.driver = driver;
        this.header = header;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitUntilPageLoaded();
    }

    private void waitUntilPageLoaded() {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(pageTitle, "Checkout: Complete!"));
    }

    public boolean isPonyVisible() {
        return wait.until(d -> d.findElement(ponyImage).isDisplayed());
    }

    public boolean isFinalMessageDisplayed() {
        return wait.until(d -> d.findElement(finalMessage).isDisplayed());
    }

    public String getHeaderMessage() {
        return driver.findElement(By.cssSelector("[data-test='complete-header']")).getText();
    }

    public String getCompleteTextMessage() {
        return driver.findElement(By.cssSelector("[data-test='complete-text']")).getText();
    }


    public void clickBackHome() {
        wait.until(ExpectedConditions.elementToBeClickable(backHomeButton)).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(pageTitle, "Products"));
    }

    public String getBackHomeButtonColor() {
        return Color.fromString(driver.findElement(backHomeButton).getCssValue("background-color")).asRgba();
    }
}