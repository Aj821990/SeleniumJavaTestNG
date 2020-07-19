package framework.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static framework.Utilities.Constants.WAIT_MEDIUM;
import static framework.base.TestBase.getDriver;
import static java.util.Collections.emptyList;
import static org.aspectj.bridge.MessageUtil.info;
import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static org.testng.FileAssert.fail;

public class BasePageMethods {

    protected WebDriver driver = getDriver();
    private WebDriverWait webDriverWait = new WebDriverWait(driver, 60);
    private JavascriptExecutor jsExec = (JavascriptExecutor) driver;
    public Logger log = Logger.getLogger("rootLogger");

    // Element click method
    public void clickWebElement(By locator) {
        WebElement element = waitUntilVisibleByLocator(locator);
        scrollTo(element,100);
        waitUntilClickableByListOfElement(element).click();
    }

    //wait element until visible
    public WebElement waitUntilVisibleByLocator(By locator) {
        WebElement element = null;
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(60))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Throwable t) {
            Assert.fail("waitUntilVisibleByLocator fail", t);
        }
        return element;
    }

    //wait element until presence
    protected WebElement waitUntilPresenceOfElementByLocator(By locator) {
        WebElement element = null;

        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class);
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Throwable t) {
            Assert.fail("waitUntilPresenceOfElementByLocator fail", t);
        }
        return element;
    }

    //wait for search results
    protected void waitForSearchResults(By locator) {
        try {
            new WebDriverWait(driver, 30)
                    .until(ExpectedConditions
                    .numberOfElementsToBeMoreThan(locator, 0));
        } catch (TimeoutException e) {
            e.printStackTrace();
            Assert.fail("Timeout: The element couldn't be found in " + WAIT_MEDIUM + " seconds!");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Something went wrong");
        }
    }

    protected void scrollTo(WebElement element, int margin) {
        scrollTo(element.getLocation().x, element.getLocation().y - margin);
    }

    protected void scrollTo(int x, int y) {
        jsExec.executeScript("scrollTo(" + x + "," + y + ");");
    }

    public boolean isElementPresent(By by, int timeOutInSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeOutInSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NotFoundException.class)
                .ignoring(NoSuchElementException.class);
        try {
            wait.until(presenceOfElementLocated(by));
            return true;
        } catch (TimeoutException t) {
            return false;
        }
    }

    protected List<WebElement> visibilityOfAllWait(By by, int timeOutInSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeOutInSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NotFoundException.class)
                .ignoring(NoSuchElementException.class);

        return wait.until(visibilityOfAllElementsLocatedBy(by));
    }

    protected List<WebElement> visibilitiesWaitNested(WebElement element, By by, int timeoutInSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NotFoundException.class)
                .ignoring(NoSuchElementException.class);
        return wait.until(visibilityOfAllElementsLocatedByIn(by, element));
    }

    protected static ExpectedCondition<List<WebElement>> visibilityOfAllElementsLocatedByIn
            (final By locator, final WebElement parent) {
        return new ExpectedCondition<List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> elements;
                if (parent != null) {
                    elements = parent.findElements(locator);
                } else {
                    elements = driver.findElements(locator);
                }
                for (WebElement element : elements) {
                    if (!element.isDisplayed()) {
                        return emptyList();
                    }
                }
                return !elements.isEmpty() ? elements : null;
            }

            @Override
            public String toString() {
                return "visibility of all elements located by " + locator;
            }
        };
    }

    protected void waitForElementToGetAttribute(int seconds, By elementLocator, String attribute, String value) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(attributeContains(elementLocator, attribute, value));
    }

    protected void pressEnter() {
        Actions builder = new Actions(driver);
        builder.sendKeys(Keys.ENTER).build().perform();
    }

    public WebElement mouseHover(By locator) {
        WebElement element = null;
        try {
            element = driver.findElement(locator);
            Actions builder = new Actions(driver);
            builder.moveToElement(element).build().perform();
        } catch (Throwable t) {
            Assert.fail("Couldn't find element", t);
        }

        return element;
    }

    public WebElement waitUntilClickableByLocator(By locator) {
        WebElement element = null;
        try {
            Wait<WebDriver> wait = new FluentWait<>(getDriver())
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class);
            element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            Assert.fail("Element is not clickable", e);
        }

        return element;
    }

    public WebElement waitUntilVisibleByLocatorSafely(By locator, int time) {
        WebElement element = null;
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(time))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            Assert.fail("Element is not visible", e);
        }

        return element;
    }

    protected WebElement waitUntilClickableByListOfElement(WebElement webElement) {
        WebElement element = null;
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class);
            element = wait.until(ExpectedConditions.elementToBeClickable(webElement));
        } catch (Exception e) {
            Assert.fail("Webelement is not clickable", e);
        }

        return element;
    }

    protected boolean waitUntilUrlContains(String expectedValue) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .withTimeout(Duration.ofMillis(100))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class);
            boolean urlExists = wait.until(ExpectedConditions.urlContains(expectedValue));
            if (urlExists) {
                info("Waited until for URL and contains expected value: " + expectedValue);
            }
        } catch (Exception e) {
            fail("Waited until for URL and NOT contains expected value.");
            Assert.fail("Waited until for URL and NOT contains expected value.", e);
            return false;
        }
        return true;
    }



}
