package pages;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WebDriver driver;

    private final By emailField = By.id("userEmail");
    private final By passwordField = By.id("userPassword");
    private final By loginButton = By.id("login");

    public LoginPage(WebDriver driver){
        this.driver=driver;
    }
    public ProductsPage login(String email, String password){
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
        TestListener.step("Logging in as: " + email);
        return new ProductsPage(driver);


    }
}
