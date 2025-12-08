package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProductsPage {

    private WebDriver driver;
    private Header header;
    private Actions actions;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.header = new Header(driver);
        this.actions = new Actions(driver);
    }


    private By productsTitle = By.className("title");
    private By dropdownSorting = By.className("product_sort_container");
    private By productsPriceList = By.className("inventory_item_price");
    private By productsNamesList = By.className("inventory_item_name");
    private By backPackProductTitle = By.xpath("//*[@id=\"item_4_title_link\"]/div");
    private By clickBackpackAddToCartButton = By.id("add-to-cart-sauce-labs-backpack");
    private By removeBackpackFromCartButton = By.id("remove-sauce-labs-backpack");
    private By addToCartButtonBikeLight = By.id("add-to-cart-sauce-labs-bike-light");


    public String productsTextDisplayed() {
        return driver.findElement(productsTitle).getText();
    }

    public boolean isProductsPageDisplayed() {
        return driver.findElement(productsTitle).getText().equals("Products");
    }

    public List<WebElement> getAllOptionsFromOrderingDropdown() {
        Select orderingDropDown = new Select(driver.findElement(dropdownSorting));
        return orderingDropDown.getOptions();
    }

    public void selectOrderingDropdownOption(int optionIndex) {
        Select orderingDropDown = new Select(driver.findElement(dropdownSorting));
        orderingDropDown.selectByIndex(optionIndex);
    }

    public String getTextFromOrderingDropdown() {
        Select orderingDropDown = new Select(driver.findElement(dropdownSorting));
        return orderingDropDown.getFirstSelectedOption().getText();
    }

    public boolean areAllProductsPricesDescending() {
        List<Double> productNames = new ArrayList<>();

        List<WebElement> priceElements = driver.findElements(productsPriceList);

        for (int i = 0; i < priceElements.size(); i++) {
            productNames.add(Double.parseDouble(driver.findElements(productsPriceList).get(i).getText().substring(1)));
        }

        for (int i = 0; i < productNames.size() - 1; i++) {
            if (productNames.get(i) < productNames.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean areAllProductsPricesAscending() {
        List<Double> productNames = new ArrayList<>();

        List<WebElement> priceElements = driver.findElements(productsPriceList);

        for (int i = 0; i < priceElements.size(); i++) {
            productNames.add(Double.parseDouble(driver.findElements(productsPriceList).get(i).getText().substring(1)));
        }

        for (int i = 0; i < productNames.size() - 1; i++) {
            if (productNames.get(i) > productNames.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public List<String> getAllProductsNames() {
        List<String> productNames = new ArrayList<>();

        List<WebElement> nameElements = driver.findElements(productsNamesList);

        for (int i = 0; i < nameElements.size(); i++) {
            productNames.add(nameElements.get(i).getText());
        }
        return productNames;
    }

    public void hoverBackPackTitle() {
        WebElement backPackTitle = driver.findElement(backPackProductTitle);

        actions.moveToElement(backPackTitle).perform();
    }

    public String getColorFromBackPackTitle() {
        Color loginButtonBackgroundColour = Color.fromString(driver.findElement(backPackProductTitle).getCssValue("color"));
        return loginButtonBackgroundColour.asHex();
    }

    public void backPackProductTitleClick() {
        driver.findElement(backPackProductTitle).click();
    }

    public WebElement getBackpackAddToCartButton() {
        return driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
    }

    public void clickAddToCartButtonBackpack() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(clickBackpackAddToCartButton)).click();
    }

    public boolean removeButtonBackpackDisplayed() {
        return driver.findElement(removeBackpackFromCartButton).isDisplayed();
    }

    public void clickRemoveButtonBackpack() {
        driver.findElement(removeBackpackFromCartButton).click();
    }

    public void clickAddToCartButtonBikeLight() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonBikeLight)).click();
    }

    public void clickProductImage(String productId) {
        driver.findElement(By.id("item_" + productId + "_img_link")).click();
    }

}
