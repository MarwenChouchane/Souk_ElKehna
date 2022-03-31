package pages.home;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pages.ficheProduit.FicheProduitPage;
import pages.login.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.monCompte.MonComptePage;

import java.sql.SQLOutput;
import java.util.List;
import java.util.stream.Collectors;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class HomePage {

    private WebDriver driver ;
    WebDriverWait wait;
    private By topProduit = By.id("owl-fea");
    private By flecheDroite = By.cssSelector("div.owl-nav > button.owl-next.disabled");
    private By productsTopProduit = By.className("owl-item");

    public HomePage (WebDriver driver) {
        this.driver = driver;
    }

    public void backHomePage(){
        driver.get("http://s-e.dotit-corp.com/fr/");
    }

    private void clickShopLink (String linkText){
        driver.findElement(By.linkText(linkText)).click();
    }

    public AgroalimentaireShop clickAgroalimentaireShopLink () {
        clickShopLink("Agroalimentaire");
        return new AgroalimentaireShop(driver);
    }

    public ArtisanatShop clickArtisanatShopLink () {
        clickShopLink("Artisanat");
        return new ArtisanatShop(driver);
    }

    public LoginPage clickLoginLink(){
        driver.findElement(By.className("user-info")).click();
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(with(By.cssSelector("li > a")).below(By.className("user-info"))));
        driver.findElement(with(By.cssSelector("li > a")).below(By.className("user-info"))).click();
        return new LoginPage(driver);
    }

    public MonComptePage clickMonCompteLink(){
        driver.findElement(By.className("user-info")).click();
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(with(By.cssSelector("li:nth-child(1) > a")).below(By.className("user-info"))));
        driver.findElement(with(By.cssSelector("li:nth-child(1) > a")).below(By.className("user-info"))).click();
        return new MonComptePage(driver);
    }

    public List<String> getallProductTitleInTopProduct (){
        List<WebElement> allProductTitle = driver.findElements(with(By
                .cssSelector("owl-item > article > div > div.wb-product-desc.text-xs-left > h3 > a"))
                .below(By.id("owl-fea")));
        return allProductTitle.stream().map(e->e.getText()).collect(Collectors.toList());
    }

    public void clickFlechDroite(){
        Actions actions = new Actions(driver);
        WebElement topProductSection = driver.findElement(By.id("owl-fea"));
        actions.moveToElement(topProductSection);

        WebElement flecheDroiteOfTopProduct = driver.findElement(By
                .cssSelector("#owl-fea > div.owl-nav > button.owl-next > span > i"));
        actions.moveToElement(flecheDroiteOfTopProduct);
        actions.click().perform();
    }

    public FicheProduitPage clickProduitLink (String productName) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement topProducts = driver.findElement(By.className("product-tab-item"));
        js.executeScript("arguments[0].scrollIntoView();", topProducts);
        List<WebElement> allProductsTitle = driver.findElements(By
                .cssSelector("#owl-fea > div.owl-stage-outer > div > div > article > div > div.wb-product-desc.text-xs-left > h3 > a"));
        //div.owl-stage-outer > div > div.owl-item > article > div > div.wb-product-desc.text-xs-left > h3
        System.out.println(allProductsTitle.size());
        for (WebElement webElement : allProductsTitle) {
            System.out.println(webElement.getText());
            if(webElement.getText().contains(productName)) {
                wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.visibilityOf(webElement));
                webElement.click();
                break;
            }
            else{
                clickFlechDroite();
            }
        }
        return new FicheProduitPage(driver);
    }
}
