package pl.lodz.p.it.isdp.wm.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.DrbgParameters;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewSeleneseIT {
    
    @Test
    public void testSimple() throws Exception {
        System.setProperty("webdriver.gecko.drive", "../geckodriver");
        
        WebDriver driver = new FirefoxDriver();

        // And now use this to visit NetBeans
        driver.get("http://www.netbeans.org");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.netbeans.org");

        // Check the title of the page
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until((ExpectedCondition<Boolean>) (WebDriver d) -> d.getTitle().contains("NetBeans"));
        
        driver.quit();
    }
    
//    @Test
//    public void testSimple() throws MalformedURLException {
//        DesiredCapabilities dc = new DesiredCapabilities();
//        dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        WebDriver driver = new RemoteWebDriver(new URL("https://localhost:8181/"), dc );
//        driver.getTitle();
//    }
    
}
