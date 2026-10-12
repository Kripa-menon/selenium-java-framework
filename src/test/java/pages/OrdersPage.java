package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrdersPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By orderIds = By.cssSelector("tbody tr th");

    public OrdersPage(WebDriver driver){
        this.driver=driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isOrderPresent(String orderId){
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(orderIds));
        List<WebElement> ids = driver.findElements(orderIds);
        for(WebElement id: ids){
            if(id.getText().trim().equals(orderId)){
                return  true;
            }
        }
        return false;
    }
}
