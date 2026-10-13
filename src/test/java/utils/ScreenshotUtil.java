package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    public static String capture(WebDriver driver, String testName) throws IOException{
        File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path folder = Paths.get("target","screenshots");
        Files.createDirectories(folder);

        Path destination=folder.resolve(testName +"_"+time+".png");
        Files.copy(source.toPath(),destination);
        return destination.toString();
    }
}
