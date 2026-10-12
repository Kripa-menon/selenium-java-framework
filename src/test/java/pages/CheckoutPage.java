package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class CheckoutPage {

    private  final WebDriver driver;
    private final WebDriverWait wait;

    private final By countryInput = By.cssSelector("input[placeholder='Select Country']");
    private  final By countryOption = By.cssSelector(".ta-item");
    private final By placeOrderButton = By.cssSelector(".action__submit");

    public CheckoutPage(WebDriver driver){
        this.driver=driver;
        this.wait= new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectCountry(String countryName){
        String partial = countryName.substring(0,3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(countryInput)).sendKeys(partial);

        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(countryOption));
        for(WebElement option :options){
            if(option.getText().trim().equalsIgnoreCase(countryName)){
                option.click();
                return;
            }
        }
        throw new NoSuchElementException("Country not found in the dropdown" + countryName);
    }

    public  ConfirmationPage placeOrder(){
        wait.ignoring(ElementClickInterceptedException.class).until(d->{
            d.findElement(placeOrderButton).click();
            return true;
        });
        return new ConfirmationPage(driver);
    }
}
