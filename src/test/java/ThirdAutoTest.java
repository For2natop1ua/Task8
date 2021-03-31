import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.apache.commons.io.FileUtils.copyFile;


public class ThirdAutoTest {
    WebDriver driver;
    By searchResults = By.cssSelector(".catalog-grid .goods-tile__title");
    By searchButtonBuy = By.xpath("//button[@aria-label = 'Купить']");
    By LinkOrder = new By.ByLinkText("Оформить заказ");
    By OrderListContacts = By.cssSelector(".checkout-form__content .checkout-block");
    String searchText = "Монитор";

    @BeforeClass
    public void setUP() {
        System.setProperty("webdriver.chrome.driver", "src/Driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://rozetka.com.ua/");
    }

    @Test
    public void test() {
        WebDriverWait wait = new WebDriverWait(driver, 4);

        WebElement searchElement = driver.findElement(By.xpath("//input[@name='search']"));
        searchElement.clear();
        searchElement.sendKeys(searchText);

        WebElement searchButtonSubmit = driver.findElement(By.cssSelector(".button.search-form__submit"));
        searchButtonSubmit.click();

        List<WebElement> results = driver.findElements(searchResults);
        results.get(0).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButtonBuy));

        driver.findElement(searchButtonBuy).click();
        wait.until(ExpectedConditions.elementToBeClickable(LinkOrder));

        driver.findElement(LinkOrder).click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(OrderListContacts, 4));
        driver.quit();
    }

    @AfterMethod(alwaysRun = true)
    public void takeScreenshot(ITestResult result) {
        if (!result.isSuccess())
            try {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                copyFile(scrFile, new File(result.getName() + "[" + LocalDate.now() + "][" + System.currentTimeMillis() + "].png"));
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
    }
}
