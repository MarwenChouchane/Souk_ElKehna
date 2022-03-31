package pages.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.monCompte.MonComptePage;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class LoginPage {
    private WebDriver driver ;
    WebDriverWait wait;
    private By loginForm = By.id("login-form");
    private By loginButton = By.id("submit-login");

    public LoginPage (WebDriver driver) {
        this.driver = driver;
    }

    public void setEmail (String email){
        driver.findElement(with(By.cssSelector("section > div:nth-child(2) > div.col-md-6 > input")).near(loginForm)).sendKeys(email);
    }

    public void setPassword (String password){
        driver.findElement(with(By.cssSelector("section > div:nth-child(3) > div.col-md-6 > div > input")).near(loginForm)).sendKeys(password);
    }

    public MonComptePage loginSubmit(){
        driver.findElement(loginButton).click();
        return new MonComptePage(driver);
    }
}
