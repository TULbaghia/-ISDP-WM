package pl.lodz.p.it.isdp.wm.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class AddProductTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "/home/student/JavaTools/geckodriver");
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");

        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("intl.accept_languages", "pl");

        firefoxOptions.setProfile(firefoxProfile);
        firefoxOptions.setBinary(firefoxBinary);
        driver = new FirefoxDriver(firefoxOptions);
    }

    @Test
    public void addProductTest() {
        try {
            driver.get("https://localhost:8181/");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

            driver.findElement(By.cssSelector("#myNavbar li:nth-child(2) a")).click();

            handleInput(By.cssSelector("form [name='j_username']"), "LRey");
            handleInput(By.cssSelector("form [name='j_password']"), "P@ssw0rd");

            driver.findElement(By.cssSelector("form [type='submit']")).click();

            assertTrue(driver.findElement(By.cssSelector(".footer")).getText().contains("LRey"));

            driver.findElement(By.cssSelector("#myNavbar li:nth-child(3) a")).click();
            driver.findElement(By.cssSelector("#myNavbar li:nth-child(3) ul li:nth-child(1) a")).click();

            handleInput(By.name("CreateProductForm:productSymbol"), "1234567890");
            handleInput(By.name("CreateProductForm:description"), "AppTest");
            handleInput(By.name("CreateProductForm:price"), "42.74");
            handleInput(By.name("CreateProductForm:weight"), "4274");

            driver.findElement(By.cssSelector("#CreateProductForm [type='submit']")).click();

            driver.findElement(By.cssSelector("#myNavbar li:nth-child(3) a")).click();
            driver.findElement(By.cssSelector("#myNavbar li:nth-child(3) ul li:nth-child(2) a")).click();

            assertEquals("1234567890", driver.findElement(By.cssSelector("table tbody tr td:nth-child(1)")).getText());
            assertEquals("AppTest", driver.findElement(By.cssSelector("table tbody tr td:nth-child(2)")).getText());
            assertEquals("42.74", driver.findElement(By.cssSelector("table tbody tr td:nth-child(3)")).getText());
            assertEquals("4274", driver.findElement(By.cssSelector("table tbody tr td:nth-child(4)")).getText());
            assertEquals("true", driver.findElement(By.cssSelector("table tbody tr td:nth-child(5) input")).getAttribute("disabled"));

            driver.findElement(By.cssSelector("table tbody tr td:nth-child(6) [type='submit']:nth-child(2)")).click();

            driver.findElement(By.cssSelector("#DeleteProductForm [type='submit']")).click();

            assertNotEquals("1234567890", driver.findElement(By.cssSelector("table tbody tr td:nth-child(1)")).getText());

            driver.findElement(By.cssSelector("#myNavbar > ul:last-child li a")).click();
            driver.findElement(By.cssSelector(".content [type='submit']")).click();

        } finally {
            driver.quit();
        }
    }

    private void handleInput(By element, String charSequence) {
        driver.findElement(element).click();
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(charSequence);
    }
}
