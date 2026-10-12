package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartItemNames = By.cssSelector("li.items h3");

    private final By checkoutButton = By.xpath("//li[@class='totalRow']/button");

    public CartPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isProductInCart(String name){
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartItemNames));
        List<WebElement> items = driver.findElements(cartItemNames);
        for(WebElement item: items){
            if(item.getText().trim().equalsIgnoreCase(name)){
                return  true;
            }
        }
        return false;
    }
    public CheckoutPage gotoCheckout(){
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        return  new CheckoutPage(driver);
    }
}
