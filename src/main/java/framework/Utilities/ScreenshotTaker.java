package framework.Utilities;

import com.aventstack.extentreports.MediaEntityBuilder;
import framework.extentFactory.ReportFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static framework.Utilities.DriverManager.getDriver;

public class ScreenshotTaker {

    public static void takeScreenShot(String name) throws IOException {
        ReportFactory.getChildTest().pass(name, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotTaker.addScreenshot()).build());
    }

    public static String addScreenshot() throws IOException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String path = System.getProperty("user.dir") + "/screenshots";

        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        String directory = path + "/" + df.format(cal.getTime()) + "/";

        LocalDateTime dateTime = LocalDateTime.now();
        String date2 = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String dateName = date2.replace(" ", "_").replace(":", ".");

        String fileName = dateName + ".png";

        File f = new File(directory);

        if (!f.isDirectory()) {
            f.mkdir();
            System.out.println("Folder was created successfully: " + directory);
        }

        String destination = directory + fileName;
        File sourceFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

        FileInputStream fileInputStreamReader = new FileInputStream(sourceFile);
        byte[] bytes = new byte[(int) sourceFile.length()];
        fileInputStreamReader.read(bytes);
        String encodedBase64 = new String(Base64.encodeBase64(bytes));

        File finalDestination = new File(destination);
        FileUtils.copyFile(sourceFile, finalDestination);
        System.out.println("screenshot taken");

        return encodedBase64;
    }
}
