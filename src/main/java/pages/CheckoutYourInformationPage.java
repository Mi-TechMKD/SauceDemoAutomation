package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class CheckoutYourInformationPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Header header;

    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By postalCodeField = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By cancelButton = By.id("cancel");
    private By errorMessage = By.cssSelector("h3[data-test='error']");
    private By errorMessageCloseButton = By.cssSelector("button.error-button");
    private By pageTitle = By.xpath("//*[@id='header_container']/div[2]/span");

    public CheckoutYourInformationPage(WebDriver driver, Header header) {
        this.driver = driver;
        this.header = header;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterFirstName(String firstName) {
        WebElement field = driver.findElement(firstNameField);
        field.clear();
        field.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        WebElement field = driver.findElement(lastNameField);
        field.clear();
        field.sendKeys(lastName);
    }

    public void enterPostalCode(String postalCode) {
        WebElement field = driver.findElement(postalCodeField);
        field.clear();
        field.sendKeys(postalCode);
    }

    public void fillAllFields(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public String getFirstName() {
        return driver.findElement(firstNameField).getAttribute("value");
    }

    public String getLastName() {
        return driver.findElement(lastNameField).getAttribute("value");
    }

    public String getPostalCode() {
        return driver.findElement(postalCodeField).getAttribute("value");
    }

    public void clickContinue() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        btn.click();
    }

    public void clickCancel() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        btn.click();
    }

    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }

    public String getErrorMessage() {
        try {
            return driver.findElement(errorMessage).getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return driver.findElement(errorMessage).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickXErrorMessageButton() {
        try {
            driver.findElement(errorMessageCloseButton).click();
        } catch (NoSuchElementException e) {
            // ништо ако нема
        }
    }

    public boolean isPageDisplayed() {
        try {
            return driver.findElement(pageTitle).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}