import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {
    WebDriver driver;
       @BeforeMethod
        public void setUp(){
         driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/client/#/auth/login");

       }
       @Test
     public void login(){
         WebElement usernameField =  driver.findElement(By.id("userEmail"));
         WebElement passwordField = driver.findElement(By.id("userPassword"));
         WebElement loginButton = driver.findElement(By.id("login"));
         usernameField.sendKeys("email");
         passwordField.sendKeys("pass");
         loginButton.click();
         WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
         //wait.until(ExpectedConditions.urlContains("dashboard/dash"));
           Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
       }
       @AfterMethod
       public  void tearDown(){
           driver.quit();
       }

}
