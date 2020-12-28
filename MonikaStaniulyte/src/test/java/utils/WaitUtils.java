package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

    /**
     * Waits until element is present in DOM
     *
     * @param driver  WebDriver
     * @param timeout timeout in seconds
     * @param locator By
     */
    public static void waitUntilElementPresent(WebDriver driver, int timeout, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitUntilElementDisplayed(WebDriver driver, int timeout, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
