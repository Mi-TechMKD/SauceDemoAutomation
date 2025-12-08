package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Header {

    private WebDriver driver;
    private WebDriverWait wait;

    private By menuButton = By.id("react-burger-menu-btn");
    private By sidebarContainer = By.cssSelector(".bm-menu-wrap");
    private By allItemsLink = By.id("inventory_sidebar_link");
    private By aboutLink = By.id("about_sidebar_link");
    private By logoutLink = By.id("logout_sidebar_link");
    private By resetAppStateLink = By.id("reset_sidebar_link");
    private By closeMenuButton = By.id("react-burger-cross-btn");
    private By pageTitle = By.className("login_logo");
    private By yourCartIcon = By.id("shopping_cart_container");
    private By yourCartBadge = By.className("shopping_cart_badge");

    public Header(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isSidebarVisible() {
        try {
            return driver.findElement(sidebarContainer).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void openMenuIfClosed() {
        if (!isSidebarVisible()) {
            clickMenuButton();
        }
    }

    public void clickMenuButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                driver.findElement(menuButton));
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(d -> driver.findElement(sidebarContainer).isDisplayed());
    }

    public void clickCloseMenuButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                driver.findElement(closeMenuButton));
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.invisibilityOfElementLocated(sidebarContainer));
    }

    public void clickAllItemsLink() {
        openMenuIfClosed();

        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(allItemsLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }

    public void clickAboutLink() {
        openMenuIfClosed();
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(aboutLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }

    public void clickLogoutLink() {
        openMenuIfClosed();
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(logoutLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
    }

    public void clickResetAppStateLink() {
        openMenuIfClosed();
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(resetAppStateLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }

    public void clickYourCartIcon() {
        driver.findElement(yourCartIcon).click();
    }
    public boolean isYourCartIconDisplayed() {
        return driver.findElement(yourCartIcon).isDisplayed();
    }

    public String getCartItemsCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(d -> driver.findElements(yourCartBadge).size() > 0 || driver.findElements(yourCartBadge).isEmpty());
            if (driver.findElements(yourCartBadge).size() > 0) {
                return driver.findElement(yourCartBadge).getText();
            } else {
                return "0";
            }
        } catch (Exception e) {
            return "0";
        }
    }

    public void waitForCartItemsCount(String expectedCount) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> {
            if (driver.findElements(yourCartBadge).size() > 0) {
                return driver.findElement(yourCartBadge).getText().equals(expectedCount);
            } else {
                return expectedCount.equals("0");
            }
        });
    }


}
