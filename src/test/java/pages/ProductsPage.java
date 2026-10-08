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

public class ProductsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By productCard = By.cssSelector(".card-body");
    private final By productName = By.cssSelector("b");
    private final  By addToCartButton = By.cssSelector("button:last-of-type");
    private final By cartButton = By.cssSelector("button[routerLink*='cart']");
    private final By toast =By.cssSelector(("#toast-container"));
    private final By spinner = By.cssSelector(".ngx-spinner-overlay");

    public ProductsPage(WebDriver driver){
        this.driver=driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isLoaded(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(productCard));
        return  driver.findElements(productCard).size()>0;
    }

    public void addToCart(String name){
        List<WebElement> cards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCard));
        for(WebElement card:cards){
            String title = card.findElement(productName).getText().trim();
            if(title.equalsIgnoreCase(name)){
                card.findElement(addToCartButton).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(toast));
                return;
            }
        }
        throw new NoSuchElementException("Product not found:" +name);
    }

    public CartPage goToCart(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(spinner));
        wait.ignoring(ElementClickInterceptedException.class).until(d ->{
            d.findElement(cartButton).click();
            return  true;
        });
        return  new CartPage(driver);
    }
}
