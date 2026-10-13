package listeners;

import base.BaseTest;
import org.testng.ITestResult;
import utils.ScreenshotUtil;

import java.awt.event.ItemListener;
import java.io.IOException;
import org.testng.ITestListener;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result){
        Object testInstance = result.getInstance();
        if(testInstance instanceof BaseTest baseTest && baseTest.getDriver()!=null){
            try{
                String path = ScreenshotUtil.capture((baseTest.getDriver()),result.getMethod().getMethodName());
                System.out.println("Screenshot saved "+ path);
            }catch (IOException e){
                System.out.println("could not save screenshot "+e.getMessage());
            }
        }
    }
}
