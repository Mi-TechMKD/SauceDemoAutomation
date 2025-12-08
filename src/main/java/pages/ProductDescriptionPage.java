package pages;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

public class ProductDescriptionPage {

    private WebDriver driver;

    private By addToCartButton = By.xpath("//button[contains(text(),'Add to cart') or contains(text(),'Remove')]");
    private By backToProductsButton = By.id("back-to-products");
    private By productName = By.className("inventory_details_name");
    private By productImage = By.className("inventory_details_img");
    private By productDescription = By.className("inventory_details_desc");
    private By productPrice = By.className("inventory_details_price");
    private By cartBadge = By.className("shopping_cart_badge");

    public ProductDescriptionPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickAddToCart() {
        driver.findElement(addToCartButton).click();
    }

    public void clickRemove() {
        driver.findElement(addToCartButton).click(); // истиот button ја менува функцијата
    }

    public String getAddRemoveButtonText() {
        return driver.findElement(addToCartButton).getText();
    }

    public String getAddRemoveButtonBorderColor() {
        return driver.findElement(addToCartButton).getCssValue("border-color");
    }

    public int getCartBadgeCount() {
        try {
            String count = driver.findElement(cartBadge).getText();
            return Integer.parseInt(count);
        } catch (Exception e) {
            return 0;
        }
    }

    public void clickBackToProducts() {
        driver.findElement(backToProductsButton).click();
    }

    public String getProductName() {
        return driver.findElement(productName).getText();
    }

    public boolean isProductImageDisplayed() {
        return driver.findElement(productImage).isDisplayed();
    }

    public String getProductDescription() {
        return driver.findElement(productDescription).getText();
    }

    public String getProductPrice() {
        return driver.findElement(productPrice).getText();
    }
}
