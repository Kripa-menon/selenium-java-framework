package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Listeners(listeners.TestListener.class)
public class BaseTest {
    protected WebDriver driver;
    protected Properties props = new Properties();
    @BeforeMethod
    public void setUp() throws IOException {
        props.load(new FileInputStream("src/test/resources/config.properties"));
        driver = DriverFactory.createDriver(props.getProperty("browser"));
        driver.manage().window().maximize();
        driver.get(props.getProperty("baseUrl"));

    }

    @AfterMethod
    public  void tearDown(){
        if(driver!=null){
            driver.quit();
        }

    }

    public WebDriver getDriver(){
        return driver;
    }

}
