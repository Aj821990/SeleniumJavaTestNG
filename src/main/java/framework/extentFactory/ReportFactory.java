package framework.extentFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ReportFactory {

    private static ExtentReports extentReports = null;
    private static Map<Integer, ExtentTest> extentParentMap = new HashMap<>();
    private static Map<Integer, ExtentTest> extentChildMap = new HashMap<>();
    private static Map<ExtentTest, Boolean> childWithSuccess = new HashMap<>();
    private static String reportPath = null;

    public static ExtentReports createReportFile() {
        ExtentHtmlReporter extentHtmlReporter = setPropertiesOfReport();
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentHtmlReporter);
        try{
            extentReports.setSystemInfo("HostName", InetAddress.getLocalHost().getHostName());
            extentReports.setSystemInfo("IP Address", InetAddress.getLocalHost().getHostAddress());
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("UserName", System.getProperty("user.name"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return extentReports;
    }

    private static ExtentHtmlReporter setPropertiesOfReport() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.now();

        String path = System.getProperty("user.dir") + "/reports";

        File folder = new File(path);
        if(!folder.exists()) {
            folder.mkdir();
        }

        path = path + "/" + dtf.format(localDate) + "-TestReport.html";
        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(path);
        extentHtmlReporter.config().setReportName("Automation Project");
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.setAppendExisting(true);
        extentHtmlReporter.config().setEncoding("utf-8");

        return extentHtmlReporter;
    }

    public static ExtentReports getExtentReports() {
        return extentReports;
    }

    public static void saveReport() {
        removeFailedTests();
        if(extentReports != null) {
            extentReports.flush();
        }
    }

    public static synchronized ExtentTest createTest(String parentName) {
        String newParentName = parentName.split("/.")[parentName.split("/.").length - 1];
        ExtentTest parent = extentReports.createTest(newParentName);
        extentParentMap.put((int) (Thread.currentThread().getId()), parent);
        return parent;
    }

    public static synchronized void createChildTest(String parentName, String childName) {
        int threadId = 0;
        String newParentName = parentName.split("/.")[parentName.split("/.").length - 1];

        for (Map.Entry<Integer, ExtentTest> eachParent : extentParentMap.entrySet()) {
            if (eachParent.getValue().getModel().getName().equals(newParentName)) {
                threadId = eachParent.getKey();
                break;
            }
        }

        ExtentTest child = getParent(threadId).createNode(childName);
        extentChildMap.put((int) (Thread.currentThread().getId()), child);
        childWithSuccess.put(child, true);
    }

    private static synchronized ExtentTest getParent(int id) {
        return extentParentMap.get(id);
    }

    public static synchronized ExtentTest getParentTest() {
        return extentParentMap.get((int) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest getChildTest() {
        return extentChildMap.get((int) (Thread.currentThread().getId()));
    }

    public static void setSuccessToChild(boolean success) {
        childWithSuccess.put(getChildTest(), success);
    }

    private static void removeFailedTests() {
        for (Map.Entry<ExtentTest, Boolean> eachParent : childWithSuccess.entrySet()) {
            if (!eachParent.getValue()) {
                extentReports.removeTest(eachParent.getKey());
            }
        }
    }
}
