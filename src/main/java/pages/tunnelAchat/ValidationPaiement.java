package pages.tunnelAchat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class ValidationPaiement {
    private final WebDriver driver ;
    WebDriverWait wait;

    public static final String ANSI_JAUNE = "\u001B[33m";
    public static final String ANSI_BLEU_BACKGROUND= "\u001B[44m";
    public static final String ANSI_RESET = "\u001B[0m";

    private final By deleveryOptions = By.className("delivery-options");

    public ValidationPaiement(WebDriver driver) {
        this.driver = driver;
    }

    public void pageRefresh (){
        driver.navigate().refresh();
    }

    public String getUrl(){
        return driver.getCurrentUrl();
    }


/*
    public ConfirmationPage validatePaiement(){
        driver.findElement(By.name("submitMPM")).click();
        return new ConfirmationPage(driver);
    }
*/
}
