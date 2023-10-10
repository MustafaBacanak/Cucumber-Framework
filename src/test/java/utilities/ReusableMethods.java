package utilities;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Function;

import static org.junit.Assert.assertTrue;

public class ReusableMethods {

    private static int timeout = 5;
    private static String alertText;


    public static String getScreenshot(String name) throws IOException {
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        FileUtils.copyFile(source, finalDestination);
        return target;
    }
    
    public static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }
    
    public static void hoverOverElement(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    public static void hoverOverAndClickElement(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).click(element).perform();
    }
    
    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elementTexts = new ArrayList<>();
        for (WebElement w : list) {
            if (!w.getText().isEmpty()) {
                elementTexts.add(w.getText());
            }
        }
        return elementTexts;
    }
    
    public static List<String> getElementsText(By locator) {
        List<WebElement> elements = Driver.getDriver().findElements(locator);
        List<String> elementTexts = new ArrayList<>();
        for (WebElement w : elements) {
            if (!w.getText().isEmpty()) {
                elementTexts.add(w.getText());
            }
        }
        return elementTexts;
    }
    
    public static void waitFor(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitSeconds(String key) throws InterruptedException {
        Thread.sleep(Integer.parseInt(key) * 1000);
    }

    public void waitMinutes(String key) throws InterruptedException {
        Thread.sleep(Integer.parseInt(key) * 1000 * 60);
    }

    public static void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }

    //======Fluent Wait====//
    public static WebElement fluentWait(final WebElement webElement, int timeout) {
        //FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver()).withTimeout(timeinsec, TimeUnit.SECONDS).pollingEvery(timeinsec, TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(3))//Wait 3 second each time
                .pollingEvery(Duration.ofSeconds(1));//Check for the element every 1 second
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return webElement;
            }
        });
        return element;
    }


    public static void checkSelectedElseClickIt(WebElement element) {
            if (!element.isSelected()) {
                element.click();
            }
    }


    public static WebElement selectRandomTextFromDropdown(Select select) {
        Random random = new Random();
        List<WebElement> weblist = select.getOptions();
        int optionIndex = 1 + random.nextInt(weblist.size() - 1);
        select.selectByIndex(optionIndex);
        return select.getFirstSelectedOption();
    }

    public static void waitAndClick(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                wait(1);
            }
        }
    }
    public static void waitAndClick(WebElement element) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                wait(1);
            }
        }
    }
    public static void waitAndSendText(WebElement element, String text, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.sendKeys(text);
                return;
            } catch (WebDriverException e) {
                wait(1);
            }
        }
    }

    public static void waitAndSendText(WebElement element, String text) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.sendKeys(text);
                return;
            } catch (WebDriverException e) {
                wait(1);
            }
        }
    }
    public static void waitAndSendTextWithDefaultTime(WebElement element, String text) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.sendKeys(text);
                return;
            } catch (WebDriverException e) {
                wait(1);
            }
        }
    }
    public static String waitAndGetText(WebElement element, int timeout) {
        String text = "";
        for (int i = 0; i < timeout; i++) {
            try {
                text = element.getText();
                return text;
            } catch (WebDriverException e) {
                wait(1);
            }
        }
        return null;
    }

    public static void wait2(int sec) {
        try {
            Thread.sleep(1000 * sec);
        } catch (java.util.NoSuchElementException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        } catch (ElementClickInterceptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void waitAndClickElement(WebElement element, int seconds) {
        for (int i = 0; i < seconds; i++) {
            try {
                element.click();
                break;
            } catch (Exception e) {
                wait2(1);
            }
        }
    }
    public static void wait(int secs) {
        try {
            Thread.sleep(1000 * secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public static Boolean waitForInVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
            wait.until(expectation);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public static void executeJScommand(WebElement element, String command) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript(command, element);
    }
    public static void selectAnItemFromDropdown(WebElement item, String selectableItem) {
        wait(5);
        Select select = new Select(item);
        for (int i = 0; i < select.getOptions().size(); i++) {
            if (select.getOptions().get(i).getText().equalsIgnoreCase(selectableItem)) {
                select.getOptions().get(i).click();
                break;
            }
        }
    }

    public static void scrollIntoViewJS(WebElement element) {
        JavascriptExecutor jsexecutor = (JavascriptExecutor) Driver.getDriver();
        jsexecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollElementWithJs(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15));
        WebElement elementName = wait.until(ExpectedConditions.visibilityOf(element));
        js.executeScript("arguments[0].scrollIntoView(true);", elementName);
    }

    public static void clickElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15));
        WebElement elementName = wait.until(ExpectedConditions.visibilityOf(element));
        elementName.click();
    }

    public static void scrollAndClickElementWithJs(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
    }

    public static void clickElementWithJs(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15));
        WebElement elementName = wait.until(ExpectedConditions.visibilityOf(element));
        js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", element);
        js.executeScript("arguments[0].click();", elementName);
    }
    public static void sendKeysText(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15));
        WebElement elementName = wait.until(ExpectedConditions.visibilityOf(element));
        clearInputWithCTRL_A_Del(element);
        elementName.sendKeys(text);
    }
    public static void sendKeysDouble(WebElement element, Double text) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15));
        WebElement elementName = wait.until(ExpectedConditions.visibilityOf(element));
        clearInputWithCTRL_A_Del(element);
        String doubleString = Double.toString(text);
        elementName.sendKeys(doubleString);
    }

    public static void checkTextEquals(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15));
        WebElement elementName = wait.until(ExpectedConditions.visibilityOf(element));
        if (elementName.getText().replaceAll("\\s+", "").equals(text.replaceAll("\\s+", ""))) {
            Assert.assertTrue(true);
        } else
            Assert.fail(text + " değerine eşit element bulunamadı. Element text :" + elementName.getText());
    }

    public static void checkTextContains(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15));
        WebElement elementName = wait.until(ExpectedConditions.visibilityOf(element));
        if (elementName.getText().trim().contains(text.trim())) {
            Assert.assertTrue(true);
        } else
            Assert.fail(text + " değerini içeren element bulunamadı. Element text :" + elementName.getText());
    }

    public static void selectWithVisibleOnDD(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15));
        WebElement elementName = wait.until(ExpectedConditions.visibilityOf(element));

        Select select = new Select(elementName);
        select.selectByVisibleText(text);
    }

    public static void refreshThisPage() {
        Driver.getDriver().navigate().refresh();
    }

    public static void clickWithJSAsList(List<WebElement> elements) {
        for (WebElement each : elements) {
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", waitForVisibility(each, 5));
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", each);
        }
    }


    public static void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }

    public static void selectByVisibleText(WebElement element, String text) {
        Select objSelect = new Select(element);
        objSelect.selectByVisibleText(text);
    }

    public static void selectByIndex(WebElement element, int index) {
        Select objSelect = new Select(element);
        objSelect.selectByIndex(index);
    }

    public static void selectByValue(WebElement element, String value) {
        Select objSelect = new Select(element);
        List<WebElement> elementCount = objSelect.getOptions();
        objSelect.selectByValue(value);
        System.out.println("number of elements: " + elementCount.size());
    }

    public static void sleep(int timeOut) {
        try {
            Thread.sleep(timeOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void clickWithText(String text) {

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),Duration.ofSeconds(5));
        WebElement element = Driver.getDriver().findElement(By.xpath("//*[text()='" + text + "']"));
        wait.until(ExpectedConditions.visibilityOf(element)).click();
    }

    public static void ifDisplayClickElement(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            WebElement elementName = wait.until(ExpectedConditions.visibilityOf(element));
            clickElementWithJs(elementName);
        } catch (Exception e) {
            System.out.println("Element Bulunamadı");
        }
    }


    public void hoverOverAndClickVisibleElement(WebElement element) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(3))
                .withMessage("Ignoring No Such Element Exception")
                .ignoring(java.util.NoSuchElementException.class);

        Actions actions = new Actions(Driver.getDriver());
        WebElement byElement = wait.until(ExpectedConditions.visibilityOf(element));
        actions.moveToElement(byElement).perform();
        byElement.click();
    }

    public void hoverOverAndClickPresenseElement(By locator) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(3))
                .withMessage("Ignoring No Such Element Exception")
                .ignoring(java.util.NoSuchElementException.class);

        Actions actions = new Actions(Driver.getDriver());
        WebElement byElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        actions.moveToElement(byElement).perform();
        byElement.click();
    }

    public static void clearInputWithJs(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15));
        WebElement elementName = wait.until(ExpectedConditions.visibilityOf(element));
        js.executeScript("arguments[0].value = '';", elementName);
    }


    public static void clearInputWithCTRL_A_Del(WebElement element) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(15));
        WebElement elementName = wait.until(ExpectedConditions.visibilityOf(element));
        elementName.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);

    }

    public static void verifyElementDisplayed(WebElement element) {
        try {
            assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (org.openqa.selenium.NoSuchElementException e) {
            Assert.fail("Element not found: " + element);
        }
    }

    public static void alertTextEqualsText( String text) {
        alertText = Driver.getDriver().switchTo().alert().getText();
        Assert.assertEquals(alertText, text);
    }

    public static void alertTextContainsText( String text) {
        alertText = Driver.getDriver().switchTo().alert().getText();
        Assert.assertTrue(alertText.contains(text));
    }

    public void ifDisplayPopupAccept(){
        try {
          Driver.getDriver().switchTo().alert().accept();
        }catch (Exception e){
            TestCase.assertTrue("Alert yok",true);
        }
    }




}