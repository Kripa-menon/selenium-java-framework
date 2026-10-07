package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected Properties props = new Properties();
    @BeforeMethod
    public void setUp() throws IOException {
        props.load(new FileInputStream("src/test/resources/config.properties"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(props.getProperty("baseUrl"));

    }

    @AfterMethod
    public  void tearDown(){
        if(driver!=null){
            driver.quit();
        }

    }

}
