package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static synchronized ExtentReports getInstance(){
        if(extent == null){
            ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
            spark.config().setDocumentTitle("Selenium Framework Report");
            spark.config().setReportName("ClientDashboard Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}
