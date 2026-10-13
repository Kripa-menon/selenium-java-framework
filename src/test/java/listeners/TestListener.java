package listeners;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestResult;
import utils.ExtentManager;
import utils.ScreenshotUtil;

import java.awt.event.ItemListener;
import java.io.IOException;
import java.nio.file.Paths;

import org.testng.ITestListener;

public class TestListener implements ITestListener {

    private final ExtentReports extent = ExtentManager.getInstance();
    private ExtentTest test;

    @Override
    public void onTestStart(ITestResult result){
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result){
        test.pass("Test passed");
    }


    @Override
    public void onTestFailure(ITestResult result){

        test.fail(result.getThrowable());
        Object testInstance = result.getInstance();
        if(testInstance instanceof BaseTest baseTest && baseTest.getDriver()!=null){
            try{
                String path = ScreenshotUtil.capture((baseTest.getDriver()),result.getMethod().getMethodName());
                System.out.println("Screenshot saved "+ path);

                String relative = Paths.get("target").relativize(Paths.get(path)).toString().replace("\\","/");
                test.addScreenCaptureFromPath(relative);
            }catch (IOException e){
                System.out.println("could not save screenshot "+e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result){
        test.skip("Test skipped");

    }

    @Override
    public void onFinish(ITestContext context){
        extent.flush();
    }
}
