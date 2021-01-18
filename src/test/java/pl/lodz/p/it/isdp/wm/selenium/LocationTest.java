package pl.lodz.p.it.isdp.wm.selenium;
 
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
 
public class LocationTest {
 
    private WebDriver driver;
    private String url;
    private String username;
    private String password;
 
    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "/home/student/JavaTools/geckodriver");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        FirefoxBinary firefoxBinary = new FirefoxBinary();
 
        firefoxProfile.setPreference("intl.accept_languages", "pl");
        firefoxOptions.setProfile(firefoxProfile);
        firefoxBinary.addCommandLineOptions("--headless");
        firefoxOptions.setBinary(firefoxBinary);
        
        driver = new FirefoxDriver(firefoxOptions);
        url = "http://localhost:8080/faces/main/index.xhtml";
        username = "JDoe";
        password = "P@ssw0rd";
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
 
    @Test
    public void AddLocationTest() throws Exception {
        driver.get(url);
        driver.findElement(By.className("glyphicon-log-in")).click();
 
        driver.findElement(By.name("j_username")).click();
        driver.findElement(By.name("j_username")).clear();
        driver.findElement(By.name("j_username")).sendKeys(username);
 
        driver.findElement(By.name("j_password")).click();
        driver.findElement(By.name("j_password")).clear();
        driver.findElement(By.name("j_password")).sendKeys(password);
 
        driver.findElement(By.xpath("//input[@type='submit']")).click();
 
        assertTrue(driver.findElement(By.xpath("//h4")).getText().contains("JDoe"));
 
        driver.findElement(By.partialLinkText("Lokalizacja")).click();
        driver.findElement(By.xpath("//a[@href='../location/listLocations.xhtml']")).click();
 
        assertEquals("AA-01-04-04", driver.findElement(By.xpath("//form[@id='j_idt26']/table/tbody/tr[last()]/td")).getText());
 
        driver.findElement(By.partialLinkText("Lokalizacja")).click();
        driver.findElement(By.xpath("//a[@href='../location/createNewLocation.xhtml']")).click();
 
        driver.findElement(By.id("CreateLocationForm:locationSymbol")).click();
        driver.findElement(By.id("CreateLocationForm:locationSymbol")).clear();
        driver.findElement(By.id("CreateLocationForm:locationSymbol")).sendKeys("ZZ-00-00-00");
        driver.findElement(By.id("CreateLocationForm:locationType")).click();
        driver.findElement(By.xpath("//option[@value='SHELF1']")).click();
        driver.findElement(By.name("CreateLocationForm:j_idt34")).click();
 
        driver.findElement(By.linkText("Lokalizacja")).click();
        driver.findElement(By.xpath("//a[@href='../location/listLocations.xhtml']")).click();
 
        assertEquals("ZZ-00-00-00", driver.findElement(By.xpath("//form[@id='j_idt26']/table/tbody/tr[last()]/td")).getText());
        assertEquals("CAŁA", driver.findElement(By.xpath("//form[@id='j_idt26']/table/tbody/tr[last()]/td[2]")).getText());
        driver.findElement(By.name("j_idt26:j_idt27:10:onlyWarehouse:j_idt38")).click();
        driver.findElement(By.name("DeleteLocationForm:j_idt30")).click();
 
        assertNotEquals(driver.findElement(By.xpath("//form[@id='j_idt26']/table/tbody/tr[last()]/td")).getText(), "ZZ-00-00-00");
 
        driver.findElement(By.linkText("Wylogowanie")).click();
        driver.findElement(By.name("j_idt26:j_idt30")).click();
 
        assertTrue(driver.findElement(By.xpath("//h4")).getText().contains("brak autoryzacji"));
    }
 
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}