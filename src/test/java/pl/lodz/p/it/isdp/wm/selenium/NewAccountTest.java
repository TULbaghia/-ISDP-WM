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

public class NewAccountTest {

    private WebDriver driver;    

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
    public void AddAccountTest() throws Exception {
		url = "http://localhost:8080/faces/main/index.xhtml";
        username = "DMitchell";
        password = "P@ssw0rd";
		
        driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		logIn(userName, userPassword);
		Assert.assertTrue(driver.findElement(By.xpath("//h4")).getText().contains("DMitchell")) 		
        checkIfAccountExists();
		Assert.assertNotEquals("ZZbigniewski", driver.findElement(By.xpath("//form[@id='j_idt26']/table/tbody/tr[last()]/td")).getText());
		
		registerAccount();
		
		checkIfAccountExists();
		Assert.assertEquals("ZZbigniewski", driver.findElement(By.xpath("//form[@id='j_idt26']/table/tbody/tr[last()]/td")).getText());
		
		removeAccount();
		Assert.assertNotEquals("ZZbigniewski", driver.findElement(By.xpath("//form[@id='j_idt26']/table/tbody/tr[last()]/td")).getText());
		
		logOut();
		Assert.assertTrue(driver.findElement(By.xpath("//h4")).getText().contains("brak autoryzacji"));
		
		driver.quit();      
    }

    public void logIn(String userName, String userPassword) {          
        driver.findElement(By.className("glyphicon-log-in")).click();
        driver.findElement(By.name("j_username")).click();
        driver.findElement(By.name("j_username")).clear();
        driver.findElement(By.name("j_username")).sendKeys(username);

        driver.findElement(By.name("j_password")).click();
        driver.findElement(By.name("j_password")).clear();
        driver.findElement(By.name("j_password")).sendKeys(password);

        driver.findElement(By.xpath("//input[@type='submit']")).click();
		
    }
	
	public void checkIfAccountExists() {
		driver.findElement(By.linkText("Konto użytkownika")).click();
        driver.findElement(By.xpath("//a[@href='../account/listNewAccounts.xhtml']")).click();	  
	}
	
	public void registerAccount() {
		driver.findElement(By.xpath("//a[@href='../common/registerAccount.xhtml']")).click();
        
        driver.findElement(By.id("RegisterForm:name")).click();
        driver.findElement(By.id("RegisterForm:name")).clear();
        driver.findElement(By.id("RegisterForm:name")).sendKeys("Zbigniew");
        
        driver.findElement(By.id("RegisterForm:surname")).click();
        driver.findElement(By.id("RegisterForm:surname")).clear();
        driver.findElement(By.id("RegisterForm:surname")).sendKeys("Zbigniewski");
        
        driver.findElement(By.id("RegisterForm:email")).click();
        driver.findElement(By.id("RegisterForm:email")).clear();
        driver.findElement(By.id("RegisterForm:email")).sendKeys("zbigniewzbigniewski@wm.com");
        
        driver.findElement(By.id("RegisterForm:login")).click();
        driver.findElement(By.id("RegisterForm:login")).clear();
        driver.findElement(By.id("RegisterForm:login")).sendKeys("ZZbigniewski");
        
        driver.findElement(By.id("RegisterForm:password")).click();
        driver.findElement(By.id("RegisterForm:password")).clear();
        driver.findElement(By.id("RegisterForm:password")).sendKeys("P@ssw0rd");
        
        driver.findElement(By.id("RegisterForm:passwordRepeat")).click();
        driver.findElement(By.id("RegisterForm:passwordRepeat")).clear();
        driver.findElement(By.id("RegisterForm:passwordRepeat")).sendKeys("P@ssw0rd");
        
        driver.findElement(By.id("RegisterForm:question")).click();
        driver.findElement(By.id("RegisterForm:question")).clear();
        driver.findElement(By.id("RegisterForm:question")).sendKeys("What is your mother name?");
        
        driver.findElement(By.id("RegisterForm:answer")).click();
        driver.findElement(By.id("RegisterForm:answer")).clear();
        driver.findElement(By.id("RegisterForm:answer")).sendKeys("Eva");
        
        driver.findElement(By.name("RegisterForm:j_idt36")).click();
	}
	
	public void removeAccount() {
		driver.findElement(By.name("j_idt26:j_idt27:3:j_idt41")).click();
        driver.findElement(By.name("DeleteNewAccountForm:j_idt30")).click();				
	}
	
	public void logOut() {
		driver.findElement(By.linkText("Wylogowanie")).click();
        driver.findElement(By.name("j_idt26:j_idt30")).click();  	
	}
}
