package tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

import java.time.Duration;

public class LoginTest extends BaseTest {

       @Test
     public void validLoginShowsDashboard(){

           LoginPage loginPage = new LoginPage(getDriver());
           ProductsPage productsPage = loginPage.login(getEmail(), getPassword() );

           WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(10));
           //wait.until(ExpectedConditions.urlContains("dashboard/dash"));
           Assert.assertTrue(productsPage.isLoaded(),"Products page did not load after login");
          //Assert.fail("retry test");
       }

}
