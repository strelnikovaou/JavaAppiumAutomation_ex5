import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {
    private AppiumDriver driver;
    String onboardingSkipButtonId = "org.wikipedia:id/fragment_onboarding_skip_button";
    String errorMessageCannotFind = "Cannot find by ";
    String errorMessageCannotSendText = "Cannot send text ";
    String searchWikipediaInputXpath = "//*[contains(@text, \"Search Wikipedia\")]";
    String searchWikipediaInputId = "org.wikipedia:id/search_src_text";
    long defaultTimeOut = 5;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\projects\\mob_auto\\JavaAppiumAutomation_lesson4\\apks\\Wikipedia_2_7.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }


    @Test
    public void testSaveTwoArticlesAndRemove() {
        String folderName = "testFolderName";
        String searchLine = "Java";
        String saveButtonId = "org.wikipedia:id/page_save";
        String addToListButtonId = "org.wikipedia:id/snackbar_action";
        String textOkXpath = "//*[@text=\"OK\"]";
        String savedListNameTextInput = "org.wikipedia:id/text_input";
        String savedListButtonXpath = "//android.widget.FrameLayout[@content-desc=\"Saved\"]";
        String backButtonXpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]";
        String firstArticleTitle = "Java (programming language)";
        String firstArticleTitleDescription = "Object-oriented programming language";
        String secondArticleTitle = "Java version history";
        String secondArticleTitleDescription = "List of versions of the Java programming language";


        waitForElementAndClick(
                By.id(onboardingSkipButtonId),
                errorMessageCannotFind + onboardingSkipButtonId,
                defaultTimeOut);

        waitForElementAndClick(
                By.xpath(searchWikipediaInputXpath),
                errorMessageCannotFind + searchWikipediaInputXpath,
                defaultTimeOut);

        waitForElementAndSendKeys(
                By.id(searchWikipediaInputId),
                searchLine,
                errorMessageCannotFind +
                        searchWikipediaInputId + "\nsearchLine: " + searchLine,
                defaultTimeOut);

        waitForElementAndClick(
                By.xpath("//*[contains(@text,\"" + firstArticleTitleDescription + "\")]"),
                errorMessageCannotFind + "//*[contains(@text,\"" + firstArticleTitleDescription + "\")]",
                defaultTimeOut);

        waitForElementAndClick(
                By.id(saveButtonId),
                errorMessageCannotFind + saveButtonId,
                defaultTimeOut);

        waitForElementAndClick(
                By.id(addToListButtonId),
                errorMessageCannotFind + addToListButtonId,
                defaultTimeOut);

        waitForElementAndSendKeys(
                By.id(savedListNameTextInput),
                folderName,
                errorMessageCannotSendText + folderName,
                defaultTimeOut);

        waitForElementAndClick(
                By.xpath(textOkXpath),
                errorMessageCannotFind + textOkXpath,
                defaultTimeOut);

        waitForElementAndClick(
                By.xpath(backButtonXpath),
                errorMessageCannotFind + backButtonXpath,
                defaultTimeOut);

        waitForElementAndClick(
                By.xpath("//*[contains(@text,\"" + secondArticleTitleDescription + "\")]"),
                errorMessageCannotFind + "//*[contains(@text,\"" + secondArticleTitleDescription + "\")]",
                defaultTimeOut);

        waitForElementAndClick(
                By.id(saveButtonId),
                errorMessageCannotFind + saveButtonId,
                defaultTimeOut);

        waitForElementAndClick(
                By.id(addToListButtonId),
                errorMessageCannotFind + addToListButtonId,
                defaultTimeOut);

        waitForElementAndClick(
                By.xpath("//*[@resource-id=\"org.wikipedia:id/item_title\"][@text=\"" + folderName + "\"]"),
                errorMessageCannotFind + "//*[@resource-id=\"org.wikipedia:id/item_title\"][@text=\"" + folderName + "\"]",
                defaultTimeOut
        );
        waitForElementAndClick(
                By.xpath(backButtonXpath),
                errorMessageCannotFind + backButtonXpath,
                defaultTimeOut);

        waitForElementAndClick(
                By.xpath(backButtonXpath),
                errorMessageCannotFind + backButtonXpath,
                defaultTimeOut);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        waitForElementAndClick(
                By.xpath(savedListButtonXpath),
                errorMessageCannotFind + savedListButtonXpath,
                defaultTimeOut);


        waitForElementAndClick(
                By.xpath("//*[@text=\"" + folderName + "\"]"),
                errorMessageCannotFind + "//*[@text=\"" + folderName + "\"]",
                defaultTimeOut);


        swipeElementToLeft(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Cannot delete saved article");

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        waitForElementNotPresent(
                By.xpath("//*[@resource-id=\"org.wikipedia:id/page_list_item_title\"][@text=\"" + firstArticleTitle + "\"]"),
                "The article is still displayed on the screen " + firstArticleTitle,
                defaultTimeOut);


        waitForElementAndClick(
                By.xpath("//*[@resource-id=\"org.wikipedia:id/page_list_item_title\"][@text=\"" + secondArticleTitle + "\"]"),
                errorMessageCannotFind + "//*[@resource-id=\"org.wikipedia:id/page_list_item_title\"][@text=\"" + secondArticleTitle + "\"]",
                defaultTimeOut);

        waitForElementPresent(
                By.xpath("//android.view.View[@content-desc=\"" + secondArticleTitle + "\"]"),
                errorMessageCannotFind + "//android.view.View[@content-desc=\"" +
                        secondArticleTitle + "\"]" + " expected article title: " + secondArticleTitle,
                defaultTimeOut);


    }


    private WebElement waitForElementPresent(By by, String error_message, long timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by));
    }

    protected void swipeElementToLeft(By by, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage, 10);
        int leftX = element.getLocation().getX();
        int rightX = (int) (0.9 * (leftX + element.getSize().getWidth()));
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;
        TouchAction action = new TouchAction(driver);
        action.press(rightX, middleY)
                .waitAction(150)
                .moveTo(leftX, middleY)
                .release()
                .perform();

    }
}
