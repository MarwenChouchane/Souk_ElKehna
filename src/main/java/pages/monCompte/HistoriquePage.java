package pages.monCompte;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.stream.Collectors;

public class HistoriquePage {
    private WebDriver driver ;
    WebDriverWait wait;
    private By TABLE = By.className("table");
    private String HEADER_FORMAT = "table/descendant::th[text()='%s']/parent";
    private By LAST_NAME_HEADER = By.xpath(String.format(HEADER_FORMAT, "Last Name"));

    public HistoriquePage (WebDriver driver) {
        this.driver = driver;
    }

    public By getTableElementLocator(){
        return TABLE;
    }

    public List<String> getallReferences (){
        List<WebElement> allReferences = driver.findElements(By.cssSelector("#content > table > tbody > tr > th"));
        return allReferences.stream().map(e->e.getText()).collect(Collectors.toList());
    }

    public boolean checkRefrenceExist(String reference){
        System.out.println(getallReferences());
        if (getallReferences().contains(reference)){
             System.out.println("La référence de la nouvelle commande a été ajouté avec succès");
        }
        return true;
    }
}
