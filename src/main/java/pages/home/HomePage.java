package pages.home;

import org.testng.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import pages.ficheProduit.FicheProduitPage;
import pages.login.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.monCompte.ComparePage;
import pages.monCompte.FavorisPage;
import pages.monCompte.MonComptePage;
import pages.tunnelAchat.MonPanier;

import java.util.List;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class HomePage{

    public HomePage (WebDriver driver) {
        this.driver = driver;
    }

    private WebDriver driver ;
    WebDriverWait wait;
    private By topProductContainer = By.className("owl-stage");
    private By topProductSection = By.id("owl-fea");
    private By flecheDroiteOfTopProduct = By
            .cssSelector("#owl-fea > div.owl-nav > button.owl-next > span > i");

    private By ratedProductContainer = By.className("toprt-ap");
    private By ratedProductSection = By.id("owl-rate");
    private By flecheDroiteOfRatedProduct = By
            .cssSelector("#owl-rate > div.owl-nav > button.owl-next > span > i");

    private By popWishlist = By.className("whishlist-alert");

    public static final String ANSI_JAUNE = "\u001B[33m";
    public static final String ANSI_BLEU_BACKGROUND= "\u001B[44m";
    public static final String ANSI_RESET = "\u001B[0m";

    public void goHome(){
        driver.get("http://s-e.dotit-corp.com/fr/");
    }

    public void navigateBack(){driver.navigate().back();}

    private void clickShopLink (String linkText){
        driver.findElement(By.linkText(linkText)).click();
    }

    public AgroalimentaireShop clickAgroalimentaireShopLink () {
        //System.out.println(driver.findElement(By.cssSelector("#_desktop_top_menu > div > div > ul > li.level-1.agro-nav-link.nav-link > a")).getText());
        clickShopLink("AGROALIMENTAIRE");
        return new AgroalimentaireShop(driver);
    }

    public ArtisanatShop clickArtisanatShopLink () {
        //System.out.println(driver.findElement(By.cssSelector("#_desktop_top_menu > div > div > ul > li.level-1.artisanat-nav-link.nav-link > a")).getText());
        clickShopLink("ARTISANAT");
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

    public void clickFlechDroite( WebElement fleche){ //WebElement container,
        Actions actions = new Actions(driver);
        //actions.moveToElement(container);
        actions.moveToElement(fleche).click().perform();
    }
    public void scrollToElement (WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
        js.executeScript("window.scrollBy(0,-250)", "");
    }
    public void closePopupWislist(WebElement webElement){
        WebElement popup = driver.findElement(By.className("close-wishlist"));

        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(popup));
        popup.click();
        System.out.println(ANSI_JAUNE+"After click " + ANSI_RESET+ webElement.getAttribute("onclick"));
    }
    public boolean retryingFindClick(WebElement button) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                button.click();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    public FicheProduitPage clickProduitLink (String productName) {
        WebElement topProducts = driver.findElement(topProductContainer);
        scrollToElement(topProducts);
        String listeTitle = driver.findElement(By.cssSelector("#content > div.product-tab-item > div:nth-child(1) > div > h2 > span")).getText();
        List<WebElement> allProductsTitle = driver.findElements(By
                .cssSelector("#owl-fea > div.owl-stage-outer > div > div > article > div > div.wb-product-desc.text-xs-left > h3"));
        System.out.println(ANSI_JAUNE+" La liste "+ANSI_BLEU_BACKGROUND+listeTitle+ANSI_RESET+ANSI_JAUNE+" contient "+ANSI_RESET+allProductsTitle.size()+ANSI_JAUNE+" elements"+ ANSI_RESET);
        String urlHome = driver.getCurrentUrl();
        for (WebElement webElement : allProductsTitle) {
            System.out.println(ANSI_JAUNE+" La nom du produit est "+ANSI_RESET+webElement.getText());
            if(webElement.getText().contains(productName)) {
                wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.visibilityOf(webElement));
                webElement.click();
                break;
            }
            else{
                wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.visibilityOf(webElement));
                WebElement container = driver.findElement(topProductSection);
                WebElement fleche = driver.findElement(flecheDroiteOfTopProduct);
                clickFlechDroite(fleche); //container,
            }
        }
        if (driver.getCurrentUrl().equalsIgnoreCase(urlHome)){
            System.out.println("This name "+productName+" of product doesn't exist");
        }
        Assert.assertNotEquals(driver.getCurrentUrl(), urlHome);
        return new FicheProduitPage(driver);
    }

    public void addFavorisRapid () throws InterruptedException {
        WebElement topProducts = driver.findElement(topProductSection);
        scrollToElement(topProducts);
        List<WebElement> allProductsWishlistButton = driver.findElements(By
                .cssSelector("#owl-fea > div.owl-stage-outer > div.owl-stage > div.owl-item > article > div.thumbnail-container > div.wb-image-block > button"));
        System.out.println(ANSI_JAUNE+"The list has : "+ ANSI_RESET+allProductsWishlistButton.size()+ANSI_JAUNE+" Elements"+ ANSI_RESET);
        for (WebElement webElement : allProductsWishlistButton) {
            wait = new WebDriverWait(driver, 10);
            if (webElement.isDisplayed()) {
                wait.until(ExpectedConditions.visibilityOf(webElement));
                webElement.click();
                closePopupWislist(webElement);
            }else{
                WebElement fleche = driver.findElement(flecheDroiteOfTopProduct);
                clickFlechDroite(fleche);
                wait.until(ExpectedConditions.visibilityOf(webElement));
                Thread.sleep(500);
                webElement.click();

                WebElement popup = driver.findElement(By.className("close-wishlist"));
//                wait = new WebDriverWait(driver, 5);
//                wait.until(ExpectedConditions.visibilityOf(popup));
                //retryingFindClick(popup);
                popup.click();
                System.out.println(ANSI_JAUNE+"After click " + ANSI_RESET+ webElement.getAttribute("onclick"));
                //closePopupWislist(webElement);
            }
        }
    }

    public void continueShopping(){
        By continueButton = By
                .cssSelector("#blockcart-modal > div > div > div > div.col-xs-12.cart-content > div:nth-child(2) > div > button");
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(continueButton));
        driver.findElement(continueButton).click();
    }
    public String getPanierQuantity (){
        WebElement panierQuantity = driver.findElement(By
                .cssSelector("#_desktop_cart > div > div > div > div > span.cart-products-count.cart-c"));
        return panierQuantity.getText();  //  .substring(0,2);(0,2) = 0 : indice debut et 2: indice fin
    }

    public void addToCardRapid () throws InterruptedException {
        WebElement ratedProducts = driver.findElement(ratedProductSection);
        scrollToElement(ratedProducts);
        String listeTitle = driver.findElement(By.cssSelector("#content > div.toprt-ap > div > section > h2 > span")).getText();
        List<WebElement> allProductsAddToCardButton = driver.findElements(By
                .cssSelector("#owl-rate > div.owl-stage-outer > div > div > article > div > div.wb-product-desc.text-xs-left > div.add-cart.atc_div.bootstrap-touchspin > form > button"));
        System.out.println(ANSI_JAUNE+"La liste : "+ ANSI_RESET+ANSI_BLEU_BACKGROUND+listeTitle+ANSI_RESET+ANSI_JAUNE+" contient "+ANSI_RESET+allProductsAddToCardButton.size()+ANSI_JAUNE+" Elements"+ANSI_RESET);
        for (WebElement webElement : allProductsAddToCardButton) {
            wait = new WebDriverWait(driver, 10);
            if (webElement.isDisplayed()) {
                wait.until(ExpectedConditions.visibilityOf(webElement));
                webElement.click();
                Thread.sleep(1000);
                continueShopping();
            }else{
                WebElement fleche = driver.findElement(flecheDroiteOfRatedProduct);
                clickFlechDroite(fleche);
                wait.until(ExpectedConditions.visibilityOf(webElement));
                Thread.sleep(1000);
                webElement.click();
                Thread.sleep(500);

                continueShopping();
            }
        }
        String listSize = allProductsAddToCardButton.size()+" article";
        String panierSize = getPanierQuantity();
        Assert.assertEquals(listSize, panierSize, "La quantité dans la panier est incorrecte");
    }

    public FavorisPage clickFavorisLink (){
        driver.findElement(By.className("user-info")).click();
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(with(By.cssSelector("#_desktop_user_info > ul > div.wishl > a"))
                .below(By.className("user-info"))));
        driver.findElement(with(By.cssSelector("#_desktop_user_info > ul > div.wishl > a"))
                .below(By.className("user-info"))).click();
        return new FavorisPage(driver);
    }

    public ComparePage clickCompareLink (){
        driver.findElement(By.className("user-info")).click();
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(with(By.cssSelector("#_desktop_user_info > ul > div.hcom"))
                .below(By.className("user-info"))));
        driver.findElement(with(By.cssSelector("#_desktop_user_info > ul > div.hcom"))
                .below(By.className("user-info"))).click();
        return new ComparePage(driver);
    }

    public MonPanier checkOutRapid(){
        WebElement panierButton = driver.findElement(By.className("cartdropd"));
        String panierButtonStatus = panierButton.getAttribute("aria-expanded");
            panierButton.click();
        //Assert.assertEquals(panierButtonStatus, true, "The drop down menu isn't enabled");
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#_desktop_cart > div > div > div > ul > li > a")));
        driver.findElement(By.cssSelector("#_desktop_cart > div > div > div > ul > li > a")).click();
        return new MonPanier(driver);
    }
}
