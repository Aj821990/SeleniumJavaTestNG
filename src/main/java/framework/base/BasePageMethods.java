package framework.base;

import com.mysql.jdbc.jmx.LoadBalanceConnectionGroupManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static framework.base.TestBase.getDriver;

public class BasePageMethods {

    protected WebDriver driver = getDriver();
    private WebDriverWait webDriverWait = new WebDriverWait(driver, 60);
    private JavascriptExecutor jsExec = (JavascriptExecutor) driver;
    public Logger log = Logger.getLogger("rootLogger");


    public void clickWebElement(By locator){
        WebElement element = null;
    }


}
