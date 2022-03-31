package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.home.HomePage;
import utiles.CookiesManager;
import utiles.WindowManager;
import java.util.concurrent.TimeUnit;

public class BaseTests {
    private WebDriver driver ;
    protected HomePage homePage ;


    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "resource/chromedriver.exe");
        driver = new ChromeDriver(getChromeOptions());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://s-e.dotit-corp.com/fr/");
        homePage = new HomePage(driver);
    }

    public void goHome(){
        driver.get("http://s-e.dotit-corp.com/fr/");
    }

    @AfterClass
    public void tearDown(){
        //driver.quit();
    }
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
}