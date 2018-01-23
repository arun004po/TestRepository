import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;
import org.junit.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TrackingNetworkCalls {
    BrowserMobProxy proxy;
    String CurrentDirectory = System.getProperty("user.dir");
    String sFileName = CurrentDirectory+"/target/SeleniumEasy.har";

@Test
    public void setup() throws IOException {

    proxy =new BrowserMobProxyServer();
    proxy.start(0);
    Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);


    // configure it as a desired capability
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(CapabilityType.PROXY,seleniumProxy);

    // start the browser up
    System.setProperty("webdriver.chrome.driver", CurrentDirectory + "\\drivers\\chromedriver.exe");
    WebDriver driver = new ChromeDriver(capabilities);

    // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
    proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT,CaptureType.RESPONSE_CONTENT);

    proxy.newHar("vitality.com");


    // open yahoo.com
    driver.get("http://aws-jt-tt-17.ec2.gbst.net:1518/adviser");
    driver.manage().deleteAllCookies();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().window().maximize();

    // get the HAR data
    Har har = proxy.getHar();
    File harFile = new File(sFileName);
    har.writeTo(harFile);

   List<HarEntry> s =  har.getLog().getEntries();

    driver.quit();

}}