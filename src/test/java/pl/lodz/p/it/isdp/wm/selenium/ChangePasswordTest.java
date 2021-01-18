package pl.lodz.p.it.isdp.wm.selenium;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.DrbgParameters;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChangePasswordTest {
    
    WebDriver driver;
    
    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "/home/student/-ISDP-WM/geckodriver");
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
    public void passwordChangeTest() throws Exception {
        String URL = "https://localhost:8181/";
        String userName = "JDoe";
        String userPassword = "P@ssw0rd";
        String newPassword = "P@ssw0rd1";
        
        driver.navigate().to(URL);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        // Log in to JDoe user account with entry credentials
        logIn(userName, userPassword);
        assertEquals(driver.findElement(By.xpath("//h4")).getText(), "Uwierzytelniony użytkownik: JDoe");
        
         // Change JDoe user password to passed newPassword
        changePassword(userName, userPassword, newPassword);
        
        // Log out
        logOut();
        assertEquals(driver.findElement(By.xpath("//h4")).getText(), "Uwierzytelniony użytkownik: brak autoryzacji");
        
        // Log in to JDoe user account with incorrect password
        logIn(userName, userPassword);
        assertTrue(driver.getPageSource().contains("Niepoprawny login lub hasło"));
        assertEquals(driver.findElement(By.xpath("//h4")).getText(), "Uwierzytelniony użytkownik: brak autoryzacji");
        
        // Log in to JDoe user account with new password
        logIn(userName, newPassword);
        assertEquals(driver.getPageSource().contains("Niepoprawny login lub hasło"), false);
        assertEquals(driver.findElement(By.xpath("//h4")).getText(), "Uwierzytelniony użytkownik: JDoe");
        
        changePassword(userName, newPassword, userPassword);

        driver.quit();
    }
    
    public void logIn(String userName, String userPassword) {
        String elementUserName = "j_username";
        String elementUserPassword = "j_password";
        String elementLoginPage = "//a[@href='../common/signIn.xhtml']";
        String elementLoginSubmit = "//input[@type='submit' and @value='Zaloguj']";
        
        driver.findElement(By.xpath(elementLoginPage)).click();
        driver.findElement(By.name(elementUserName)).sendKeys(userName);
        driver.findElement(By.name(elementUserPassword)).sendKeys(userPassword);
        driver.findElement(By.xpath(elementLoginSubmit)).click();
    }
    
    public void changePassword(String userName, String userPassword, String newPassword) {
        driver.findElement(By.xpath("//a[contains(text(), 'Ustawienia')]")).click();
        driver.findElement(By.xpath("//a[@href='../common/changeMyPassword.xhtml']")).click();
        
        // Assert user name equals JDoe
        assertTrue(driver.getPageSource().contains("JDoe"));
        
        driver.findElement(By.id("ChangeMyPasswordForm:oldPassword")).sendKeys(userPassword);
        driver.findElement(By.id("ChangeMyPasswordForm:newPassword")).sendKeys(newPassword);
        driver.findElement(By.id("ChangeMyPasswordForm:newPasswordRepeat")).sendKeys(newPassword);
        driver.findElement(By.xpath("//input[@type='submit' and @value='Zmień hasło']")).click();
    }
    
    public void logOut() { 
        driver.findElement(By.xpath("//a[@href='../common/logout.xhtml']")).click();
        driver.findElement(By.xpath("//input[@type='submit' and @value='Wyloguj']")).click();
    }
}
