import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TC {

    WebDriver driver, chrome, firefox;

    @BeforeMethod
    public void beforeMethod(){

        driver = new SafariDriver();
    }

    @Test
    void TC1() throws InterruptedException {

        String searchKeyWord = "iphone 11";
        String targetItem = "iPhone 11 Pro Max (64GB)";

        System.out.println("TC 1 start");

        // Access Amazon
        driver.get("http://www.amazon.ca/");

        // Find Search Bar and input iphone 11 in search bar, then click enter to search
        WebElement searchElement = driver.findElement(By.id("twotabsearchtextbox"));
        searchElement.sendKeys(searchKeyWord + Keys.ENTER);
        System.out.println("User searches " + searchKeyWord + " as search keyword.");

        // Wait Page load and then search target item
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement searchResult;
        String targetXpath = "//span[contains(text(),'" + targetItem + "')]//ancestor::a";
        try{
            searchResult = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(targetXpath)));
            System.out.println("User searched " + targetItem + ", and that is the target item.");
        }
        catch(Exception e){
            System.out.println("TC 1 is failed since the target item is not found.");
            return;
        }

        // Click the url from search result, normally use click()
        String searchResultUrl = searchResult.getAttribute("href");
        driver.get(searchResultUrl);
        System.out.println("User clicks the target item: " + targetItem);

        // Check the add to cart button to ensure whether item is available or not
        try{
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
            System.out.println("TC 1 is passed since the target item is available.");
        }
        catch (Exception e){
            System.out.println("TC 1 is failed since the target item is out of stock.");
        }
    }

    @AfterMethod
    void Teardown(){
        // if required, close the driver here
        driver.close();
    }
}
