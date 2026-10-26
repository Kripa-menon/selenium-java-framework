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

public class DriverFactory {

    public static WebDriver createDriver(String browser) {
        return createDriver(browser, false);
    }

    public static WebDriver createDriver(String browser, boolean headless) {
        return switch (browser.toLowerCase()) {
            case "chrome" -> new ChromeDriver(chromeOptions(headless));
            case "firefox" -> new FirefoxDriver(firefoxOptions(headless));
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

    public static WebDriver createDriver(String browser, boolean headless, String gridUrl) {
        if (gridUrl == null || gridUrl.isBlank()) {
            return createDriver(browser, headless);
        }

        MutableCapabilities options = switch (browser.toLowerCase()) {
            case "chrome" -> chromeOptions(headless);
            case "firefox" -> firefoxOptions(headless);
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };

        try {
            return new RemoteWebDriver(URI.create(gridUrl).toURL(), options);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid grid URL: " + gridUrl, e);
        }
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        if ("true".equalsIgnoreCase(System.getenv("CI"))) {
            options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        }
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless", "--width=1920", "--height=1080");
        }
        return options;
    }
}
