package saucedemotests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.Footer;
import pages.LoginPage;
import pages.ProductsPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FooterTests {

    private WebDriver driver;
    private ProductsPage productsPage;
    private LoginPage loginPage;
    private Footer footer;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        productsPage = new ProductsPage(driver);
        footer = new Footer(driver);
    }
    @Test
    public void footerTest() {
        System.out.println(footer.getFooter());
        assertEquals("Twitter\nFacebook\nLinkedIn", footer.getFooter());
    }

    @Test
    public void socialLinksAreDisplayedTest() {
        assertTrue(footer.socialLinksDisplayed());
    }

    @Test
    public void twitterLinkTest() {
        footer.twitterLink();
        assertEquals("https://x.com/saucelabs", footer.getCurrentUrl());
        footer.closeCurrentTabAndReturn();
    }

    @Test
    public void facebookLinkTest() {
        footer.facebookLink();
        assertEquals("https://www.facebook.com/saucelabs", footer.getCurrentUrl());
        footer.closeCurrentTabAndReturn();
    }

    @Test
    public void linkedinLinkTest() {
        footer.setLinkedinLink();
        assertEquals("https://www.linkedin.com/company/sauce-labs/", footer.getCurrentUrl());
        footer.closeCurrentTabAndReturn();
    }

    @Test
    public void footerCopyrightTextTest() {
        String expectedText = "© 2025 Sauce Labs. All Rights Reserved. Terms of Service | Privacy Policy";
        assertEquals(expectedText, footer.getCopyrightText());
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }
}
