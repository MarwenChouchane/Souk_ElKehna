package base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.home.HomePage;
import utiles.ChatBoxManager;
import utiles.CookiesManager;
import utiles.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.assertEquals;

public class BaseTests {
    protected static WebDriver driver;
    protected static EyesManager eyesManager;
    WebDriverWait wait;
    protected HomePage homePage ;


    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "resource/chromedriver1.exe");
        driver = new ChromeDriver(); //new ChromeDriver(getChromeOptions())
        driver.manage().window().maximize();

        Properties props = System.getProperties();
        try {
            props.load(new FileInputStream(new File("resource/property.properties")));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        eyesManager = new EyesManager(driver, "Souk ElKahina");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("http://s-e.dotit-corp.com/fr/");
        waitForPageLoad();
        List<WebElement> closePopup = driver.findElements(By.className("fancybox-close"));
        if (closePopup.size()>0){
            driver.findElement(By.className("fancybox-close")).click();
        }
        homePage = new HomePage(driver);
//        var chatBoxManager = getChatBoxManager();
//        chatBoxManager.hideElement("lc_chatbox");

        var loginPage = homePage.clickLoginLink();
        loginPage.setEmail("marwen2@yopmail.com");
        loginPage.setPassword("123456789");
        var monComptePage = loginPage.loginSubmit();
    }

    public void waitForPageLoad() {
        String pageLoadStatus = null;
        JavascriptExecutor js;
        do {
            js = (JavascriptExecutor) driver;
            pageLoadStatus = (String)js.executeScript("return document.readyState");
        } while ( !pageLoadStatus.equals("complete") );
        System.out.println("Page Loaded.");
    }

     @Test
     public void test (){
         boolean cartDrop = false;
         boolean cartStyle = Boolean.parseBoolean(driver.findElement(By.className("cartdropd")).getAttribute("aria-expanded"));   //head-cart-drop
         System.out.println("The value of the attribute aria-expanded of the cart Drop is : "+cartStyle);
     }


    @AfterClass
    public void tearDown(){
        //driver.quit();
        eyesManager.abort();
    }
/*
    @BeforeMethod
    public void login (){
        var loginPage = homePage.clickLoginLink();
        loginPage.setEmail("marwen2@yopmail.com");
        loginPage.setPassword("123456789");
        var monComptePage = loginPage.loginSubmit();
        assertEquals(monComptePage.getTitle(), "Mon compte", "This is incorrecte page");
        homePage.goHome();
    }
*/
//    @BeforeMethod
//    public void closeChatBox (){
//        var chatBoxManager = getChatBoxManager();
//        chatBoxManager.hideElement("lc_chatbox");
//    }

/*
    @AfterMethod //Take screen shot if the test failed and enregistrer in "resource/scrennshot"
    public void recordFailure (ITestResult result){
        if (ITestResult.FAILURE == result.getStatus()){
            var camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            //System.out.println("Screenshot taken : "+ screenshot.getAbsolutePath());
            try {
                Files.move(screenshot, new File("C:/Users/Administrator/IdeaProjects/Souk_ElKehna/resource/scrennshot/" +result.getName() +".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    public WindowManager getWindowManger (){
        return new WindowManager(driver);
    }

    public CookiesManager getCookieManager(){
        return new CookiesManager(driver);
    }

    private ChromeOptions getChromeOptions (){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars"); //Close the info bar (Chrome est controler par unn logiciel de test automatisé)
        //options.setHeadless(true);  //Run test without opening the browser
        return options;
    }

    public ChatBoxManager getChatBoxManager(){
        return new ChatBoxManager(driver);
    }

    public boolean waitForJSandJQueryToLoad() {

        wait = new WebDriverWait(driver, 30);

        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active") == 0);
                }
                catch (Exception e) {
                    // no jQuery present
                    return true;
                }
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

}
