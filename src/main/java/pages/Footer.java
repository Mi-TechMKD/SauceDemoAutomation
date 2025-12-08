package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Footer {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By twitterLink = By.xpath("//*[@id=\"page_wrapper\"]/footer/ul/li[1]/a");
    private final By facebookLink = By.xpath("//*[@id=\"page_wrapper\"]/footer/ul/li[2]/a");
    private final By linkedInLink = By.xpath("//*[@id=\"page_wrapper\"]/footer/ul/li[3]/a");
    private By copyrightText = By.className("footer_copy");

    public Footer(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private void clickLink(By link) {
        driver.findElement(link).click();
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public void twitterLink() {
        clickLink(twitterLink);
    }

    public void facebookLink() {
        clickLink(facebookLink);
    }

    public void setLinkedinLink() {
        clickLink(linkedInLink);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void closeCurrentTabAndReturn() {
        driver.close();
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
            break;
        }
    }

    public String getCopyrightText() {
        return driver.findElement(copyrightText).getText();
    }

    public boolean socialLinksDisplayed() {
        try {
            return driver.findElement(twitterLink).isDisplayed() &&
                    driver.findElement(facebookLink).isDisplayed() &&
                    driver.findElement(linkedInLink).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getFooter() {
        return driver.findElement(twitterLink).getText() + "\n" +
                driver.findElement(facebookLink).getText() + "\n" +
                driver.findElement(linkedInLink).getText();
    }
}
