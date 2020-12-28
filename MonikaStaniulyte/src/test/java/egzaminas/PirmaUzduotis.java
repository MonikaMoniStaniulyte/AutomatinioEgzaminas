package egzaminas;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.WaitUtils;

import java.util.concurrent.TimeUnit;

public class PirmaUzduotis {

    private WebDriver driver;
    private String baseURL = "https://demo.opencart.com";

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Testai prasidėjo");
    }

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(baseURL);
    }

    @After
    public void after() {
        driver.quit();
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("Testai pasibaigė");
    }

    //1.Pačiame viršuje paspausti MyAccount
    //2.Atsidarius dropdown‘ui paspausti Login
    //3.Patikrinti, jog matomas „New customer“ blokas
    //4.Patikrinti, jog matomas „Returning customer“ blokas
    //5.Suvesti blogus duomenis į email / password
    //6.Paspausti Login
    //7.Patikrinti, jog rodomas error, kuris turi tekstą „Warning: No match for E-Mail Address and/or Password.“

    @Test
    public void pirmaUzduotis() {

        driver.findElement(By.cssSelector("#top-links a[title='My Account']")).click();

        driver.findElement(By.xpath("//*[@id='top-links']//a[contains(text(), 'Login')]")).click();
        driver.findElement(By.xpath("//*[@class='well']//h2[contains(text(), 'New Customer')]")).click();

        WebElement newCustomerBlock = driver.findElement(By.xpath("//*[@class='well']//h2[contains(text(), 'New Customer')]"));
        WebElement returningCustomerBlock = driver.findElement(By.xpath("//*[@class='well']//h2[contains(text(), 'Returning Customer')]"));

        WaitUtils.waitUntilElementDisplayed(driver, 3, newCustomerBlock);
        WaitUtils.waitUntilElementDisplayed(driver, 3, returningCustomerBlock);

        driver.findElement(By.id("input-email")).sendKeys("TEST");
        driver.findElement(By.id("input-password")).sendKeys("TEST");
        driver.findElement(By.cssSelector("input[type='submit']")).click();

        String actualError = driver.findElement(By.cssSelector(".alert-danger")).getText();
        String expectedError = "Warning: No match for E-Mail Address and/or Password.";

        Assert.assertEquals("Error message is wrong", expectedError, actualError);
    }
}
