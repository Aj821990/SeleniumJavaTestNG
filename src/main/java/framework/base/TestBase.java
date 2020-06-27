package framework.base;

import framework.Listener.Listener;
import framework.Utilities.Config;
import framework.Utilities.DriverManager;
import framework.Utilities.ReTryTestCase;
import framework.extentFactory.ReportFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import static framework.base.Browsers.prepareDriver;
import static framework.extentFactory.ReportFactory.createReportFile;

@Listeners({Listener.class})
public class TestBase {

    public static String BROWSER = null;
    public static String GRIDURL = null;
    public static String BASEURL = null;
    public static String REMOTE = null;
    public static String GRIDNAME = null;
    public static int RETRY;
    public String testNameFromXML = null;
    public static Logger log = Logger.getLogger("rootLogger");

    private Properties loadPropertyFile(String filePath) throws IOException {
        Properties properties = new Properties();
        File file = new File(filePath);
        InputStream inputStream = new FileInputStream(file);
        properties.load(inputStream);

        return properties;
    }

    public void initializeConfig(String reTry,String browser, String gridUrl) throws Throwable {

        Properties properties = loadPropertyFile(System.getProperty("user.dir") + "/src/main/java/resources/config.properties");

        BROWSER = browser;
        GRIDURL = gridUrl;
        RETRY = Integer.parseInt(reTry);
        BASEURL = Config.getInstance().getBaseUrl();
        REMOTE = properties.getProperty("remote");
    }

    @Parameters(value = {"reTry", "browser", "gridUrl"})
    @BeforeSuite
    public void beforeSuite(ITestContext context,
                            @Optional String reTry,
                            @Optional String browser,
                            @Optional String gridUrl) throws Throwable {

        initializeConfig(reTry, browser, gridUrl);
        System.out.println("before creating report");
        createReportFile();
        System.out.println("after creating report");

        for(ITestNGMethod method : context.getSuite().getAllMethods()) {
            method.setRetryAnalyzer(new ReTryTestCase());
        }
    }

    @BeforeClass
    public void beforeClass() {
        org.apache.log4j.PropertyConfigurator.configure("log4j.properties");
        testNameFromXML = this.getClass().getName();
        ReportFactory.createTest(testNameFromXML);
    }

    @BeforeMethod
    public void beforeMethod(Method method) throws RuntimeException {
        DriverManager.setDriver(prepareDriver());
        getDriver().navigate().to(BASEURL);
        ReportFactory.createChildTest(testNameFromXML, method.getName());
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        getDriver().quit();
        IRetryAnalyzer retry = result.getMethod().getRetryAnalyzer();
        if (retry == null) {
            return;
        }
        result.getTestContext().getSkippedTests().removeResult(result.getMethod());
    }

    public synchronized static WebDriver getDriver()
    {
        return DriverManager.getDriver();
    }
}
