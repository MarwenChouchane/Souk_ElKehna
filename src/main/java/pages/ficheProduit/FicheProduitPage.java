package pages.ficheProduit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FicheProduitPage {
    private WebDriver driver ;
    WebDriverWait wait;

    public FicheProduitPage(WebDriver driver) {
        this.driver = driver;
    }
}
