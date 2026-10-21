package base;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Locale;

public class DriverFactory {

    public static WebDriver createDriver(String browser){
        return  switch (browser.toLowerCase()){
            case "chrome" -> new ChromeDriver();
            case "firefox" -> new FirefoxDriver();
            default -> throw new IllegalArgumentException("Unsupported Browser: " + browser);
        };
    }

    public static WebDriver createDriver(String browser, boolean headless){
        return switch (browser.toLowerCase()){
            case "chrome" ->{
                ChromeOptions options = new ChromeOptions();
                if(headless){
                    options.addArguments("--headless=new","--window-size=1920,1080");
                }
                yield new ChromeDriver(options);
            }
            case "firefox"->{
                FirefoxOptions options=new FirefoxOptions();
                if(headless){
                    options.addArguments("-headless","--width=1920","--height=1080");
                }
                yield new FirefoxDriver(options);
            }
            default -> throw new IllegalArgumentException("unsupported browser"+browser);
        };
    }

    public static WebDriver createDriver(String browser,boolean headless, String gridUrl){
        if(gridUrl == null || gridUrl.isBlank()){
            return createDriver(browser,headless);
        }
        MutableCapabilities options = switch (browser.toLowerCase()){
            case "chrome"->{
                ChromeOptions chrome = new ChromeOptions();
                if(headless){
                    chrome.addArguments("--headless=new","--window-size=1920,1080");
                }
                yield chrome;
            }
            case "firefox"->{
                FirefoxOptions firefox = new FirefoxOptions();
                if(headless){
                    firefox.addArguments("-headless","--width=1920","--height=1080");
                }
                yield firefox;
            }
            default -> throw new IllegalArgumentException("unsupported browser"+browser);
        };
        try{
            return new RemoteWebDriver(URI.create(gridUrl).toURL(),options);
        }catch (MalformedURLException e){
            throw  new IllegalArgumentException("invalid grid URL:"+gridUrl,e);
        }
    }
}
