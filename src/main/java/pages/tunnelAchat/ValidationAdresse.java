package pages.tunnelAchat;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class ValidationAdresse {
    private final WebDriver driver ;
    WebDriverWait wait;

    public static final String ANSI_JAUNE = "\u001B[33m";
    public static final String ANSI_BLEU_BACKGROUND= "\u001B[44m";
    public static final String ANSI_RESET = "\u001B[0m";

    private final By monAdresse = By.className("js-address-item");  //ClassName = js-address-item.address-item
    private final By adresseCart = By.className("radio-block");

    public ValidationAdresse(WebDriver driver) {
        this.driver = driver;
    }

    public void pageRefresh (){
        driver.navigate().refresh();
    }

    public String getUrl(){
        return driver.getCurrentUrl();
    }

    public AdressesDetails getAdresse(int indexAdresse){
        WebElement mesAdresses = driver.findElements(monAdresse).get(indexAdresse-1);
        List<WebElement> meAdresses = driver.findElements(monAdresse);
        System.out.println(ANSI_JAUNE+"Ma list des adresses contient : "+ANSI_RESET+meAdresses.size()+ANSI_JAUNE+" adresses"+ANSI_RESET);
        Actions actions = new Actions(driver);
        actions.moveToElement(mesAdresses).perform();
        return new AdressesDetails(mesAdresses.findElement(adresseCart));
    }

    public class AdressesDetails {
        private final WebElement details;
        private final By radioButton = By.cssSelector("span.custom-radio");     // name = id_address_delivery
        private final By adresseDetail = By.className("address");

        public AdressesDetails (WebElement details){
            this.details = details;
        }

        public String getadresseDetail (){
            System.out.println(ANSI_JAUNE+"L'adresse choisi' est : "+ANSI_RESET+details.findElement(adresseDetail).getText());
            return details.findElement(adresseDetail).getText();
        }

        public void chooseAdresse (){
            if (details.findElement(radioButton).isSelected()) {
                System.out.println("L'adresse choisi est déja selectionné ");
            }else {
                details.findElement(radioButton).click();
            }
        }
    }

    public ValidationShipement validateAdresse(){
        driver.findElement(By.name("confirm-addresses")).click();
        return new ValidationShipement(driver);
    }

}
