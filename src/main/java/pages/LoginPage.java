package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;



public class LoginPage {

    private WebDriver driver;
    private Header header;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        header = new Header(driver);
    }

    //Components -> Locators
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.xpath("//div[@class='error-message-container error']");
    private By errorMessageXButton = By.className("error-button");

    //Actions
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public String getUsernameFontSize(){
        return driver.findElement(usernameField).getCssValue("font-size");
    }
    public String getUsernameFontType(){
        return driver.findElement(usernameField).getCssValue("font-family");
    }

    public String getPasswordFontSize(){
        return driver.findElement(passwordField).getCssValue("font-size");
    }
    public String getPasswordFontType(){
        return driver.findElement(passwordField).getCssValue("font-family");
    }
    public String getLoginButtonText() {
        return driver.findElement(loginButton).getAttribute("value");
    }
    public String getLoginButtonFontType() {
        return driver.findElement(loginButton).getCssValue("font-family");
    }

    public String getLoginButtonFontSize() {
        return driver.findElement(loginButton).getCssValue("font-size");
    }
    public String getLoginButtonColor(){
        Color loginButtonBackgroundColour = Color.fromString(driver.findElement(loginButton).getCssValue("background-color"));
        return loginButtonBackgroundColour.asHex();
    }
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public void clickErrorMessageXButton() {
        driver.findElement(errorMessageXButton).click();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            driver.findElement(errorMessage).getText();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}