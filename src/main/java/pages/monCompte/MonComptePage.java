package pages.monCompte;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.home.ArtisanatShop;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class MonComptePage {
    private WebDriver driver ;
    WebDriverWait wait;

    public MonComptePage (WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle(){
        return driver.getTitle();
    }

    private void clickItemLink (String id){
        driver.findElement(By.id(id)).click();
    }

    public HistoriquePage clickHistoriqueCommandeLink () {
//        wait = new WebDriverWait(driver, 5);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Historique et détails de mes commandes")));
        clickItemLink("history-link");
        return new HistoriquePage(driver);
    }
}
