package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

@Listeners(listeners.TestListener.class)
public class BaseTest {

    private static final  ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static final AtomicInteger nextAccount = new AtomicInteger(1);
    private static final ThreadLocal<Integer> accountNumber = ThreadLocal.withInitial(nextAccount::getAndIncrement);
    protected Properties props = new Properties();

    protected String getEmail(){
        return account("email");
    }

    protected  String getPassword(){
        return account("password");
    }

    private String account(String field){
        int number = accountNumber.get();
        int max = Integer.parseInt(props.getProperty("accountCount"));
        if(number>max){
            throw new IllegalStateException("Thread needs account"+ number +"but only"+max+"are configured");
        }
        return fromEnv(field.toUpperCase()+"-"+number,field + number);
    }
    @BeforeMethod
    public void setUp() throws IOException {

        loadIfExists("src/test/resources/config.example.properties");
        loadIfExists("src/test/resources/config.properties");

        boolean headless = Boolean.parseBoolean(fromEnv("HEADLESS","headless"));
        String browser = fromEnv("BROWSER","browser");
        String gridUrl = fromEnv("GRID_URL","gridUrl");
        //props.load(new FileInputStream("src/test/resources/config.properties"));
        WebDriver driver = DriverFactory.createDriver(props.getProperty("browser"));
        driverThread.set(driver);
        if(!headless) {
            driver.manage().window().maximize();
        }
        driver.get(props.getProperty("baseUrl"));

    }

    @AfterMethod
    public  void tearDown(){
        WebDriver driver = driverThread.get();
        if(driver!=null){
            driver.quit();
            driverThread.remove();
        }

    }

    public WebDriver getDriver(){

        return driverThread.get();
    }

    private void loadIfExists(String path)throws IOException{
        File file = new File(path);
        if(file.exists()){
            try(FileInputStream in = new FileInputStream(file)){
                props.load(in);
            }
        }
    }

    private String fromEnv(String envName, String propKey){
        String value = System.getenv(envName);
        return (value!=null && !value.isBlank())?value:props.getProperty(propKey);
    }

}
