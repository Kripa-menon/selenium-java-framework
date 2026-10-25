package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class ConfirmationPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By orderIDLabel = By.cssSelector("td.em-spacer-1 label");

    private final By ordersButton = By.cssSelector("button[routerlink*='myorders']");

    public ConfirmationPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getOrderId()
    {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(orderIDLabel));
        List<WebElement> labels = driver.findElements(orderIDLabel);
        for(WebElement label :labels){
            String text = label.getText();
            if(text.contains("|")){
                return text.replace("|","").trim();
            }
        }
        throw new NoSuchElementException("order Id label not found on confirmation page");

    }

    public OrdersPage gotoOrders(){
        wait.until(ExpectedConditions.elementToBeClickable(ordersButton)).click();
        return new OrdersPage(driver);
    }
}
