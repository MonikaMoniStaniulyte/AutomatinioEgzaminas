package egzaminas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.WaitUtils;

import java.util.concurrent.TimeUnit;

public class AntraUzduotis {

    private WebDriver driver;
    private String baseURL = "https://demo.opencart.com";

    @BeforeMethod
    public void before() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(baseURL);
    }

    @AfterMethod
    public void after() {
        driver.quit();
    }

    //Precondition: Susikurti DataProvider‘į: iPod Nano, iPod Touch, iPod Shuffle
    //1.Atidarykite testuojamą puslapį;
    //2.Iš Menu turi užvesti (hover) ant MP3 Players
    //3.Kai atsidaro MP3 Players paspausti ant Show All MP3 Players
    //4.Patikrinti ar atsidarė MP3 Players kategorija (assert)
    //5.Paspausti mygtuką, jog produktus rodytų kaip sąrašą
    //6.Iš data provider paimti produkto pavadinimą, surasti jį tarp produktų ir:
    //a.Paspausti mygtuką „Add to Cart“
    //b.Patikrinti, jog atsiranda žinutė „Success: You have added <Produkto pavadinimas> to your shopping cart!“
    //c.Patikrinti, jog produktas buvo įdėtas į krepšelį (viršuje, dešinėje kampe esantis mini krepšelis )

    @Test(dataProvider = "ipod")
    public void antraUzduotis(String ipodModel) throws InterruptedException {
        WebElement mp3MenuLink = driver.findElement(By.xpath("//*[@id='menu']//a[contains(text(), 'MP3 Players')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(mp3MenuLink).build().perform();

        WaitUtils.waitUntilElementDisplayed(driver, 3, driver.findElement(By.xpath("//*[@id='menu']//a[contains(text(), 'MP3 Players')]/following-sibling::*[@class='dropdown-menu']")));

        WebElement mp3AllLink = driver.findElement(By.xpath("//*[@id='menu']//a[contains(text(), 'Show All MP3 Players')]"));
        mp3AllLink.click();

        String expectedPageTitle = "MP3 Players";
        Assert.assertEquals(driver.getTitle(), expectedPageTitle, "Couldn't load mp3 players section");

        driver.findElement(By.id("list-view")).click();

        String productThumbSelector =  "//a[contains(text(), '" + ipodModel +  "')]/../../../..";
        WebElement productThumb = driver.findElement(By.xpath(productThumbSelector));

        productThumb.findElement(By.tagName("button")).click();

        Thread.sleep(2000);

        String actualNotificationText = driver.findElement(By.cssSelector(".alert.alert-success")).getText();

        Assert.assertTrue(actualNotificationText.contains("Success: You have added"));
        Assert.assertTrue(actualNotificationText.contains(ipodModel));

        Thread.sleep(2000);

        driver.findElement(By.cssSelector("header #cart button[data-toggle='dropdown']")).click();

        Assert.assertTrue(driver.findElement(By.cssSelector("#cart .dropdown-menu")).getText().contains(ipodModel));

        Thread.sleep(2000);
    }

    @DataProvider(name = "ipod")
    public static Object[] navigationData() {

        return new Object[]{
                "iPod Nano",
                "iPod Touch",
                "iPod Shuffle"
        };
    }

}
