package pages.ficheProduit;


import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.tunnelAchat.MonPanier;

import java.util.List;

public class FicheProduitPage {

    private WebDriver driver ;
    WebDriverWait wait;
    public static final String ANSI_JAUNE = "\u001B[33m";
    public static final String ANSI_BLEU_BACKGROUND= "\u001B[44m";
    public static final String ANSI_RESET = "\u001B[0m";

    private By quantityField = By.id("quantity_wanted");
    private By addToCartButton = By.cssSelector("#add-to-cart-or-refresh > div.product-add-to-cart > div > div.add > button");

    private By autresProductContainer = By.cssSelector("#main > section");
    private By autresProductSection = By.id("owl-same");
    private By flecheDroiteOfautresProduct = By
            .cssSelector("#owl-same > div.owl-nav > button.owl-next > span > i");

    private By addToFavorisButton = By.className("prowish");
    private By addToCompareButton = By.cssSelector("#add-to-cart-or-refresh > div.product-additional-info > div.compare > a");


    public FicheProduitPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clearField(WebElement element){
//        Actions action = new Actions(driver);
//        action.click(element).perform();
//        element.clear();
        String s = Keys.chord(Keys.CONTROL, "a");
        element.sendKeys(s);
        element.sendKeys(Keys.DELETE);
    }

    public void continueShopping(){
        WebElement continueButton = driver.findElement(By
                .cssSelector("#blockcart-modal > div > div > div > div.col-xs-12.cart-content > div:nth-child(2) > div > button"));
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(continueButton));
        continueButton.click();
    }

    public MonPanier checkOut(){
        WebElement checkoutButton = driver.findElement(By
                        .cssSelector("#blockcart-modal > div > div > div > div.col-xs-12.cart-content > div:nth-child(2) > div > a"));
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(checkoutButton));
        checkoutButton.click();
        return new MonPanier(driver);
    }

    public void setQuantity (String quantity){
        WebElement quantityclick = driver.findElement(quantityField);
        clearField(quantityclick);
        System.out.println(driver.findElement(quantityField).getText());
        driver.findElement(quantityField).sendKeys(quantity);
        System.out.println(driver.findElement(quantityField).getText());
    }

    public void addToCart (){
        driver.findElement(addToCartButton).click();
    }

    public void clickFlechDroite(WebElement container, WebElement fleche){
        Actions actions = new Actions(driver);
        actions.moveToElement(container);

        actions.moveToElement(fleche);
        actions.click().perform();
    }

    public void scrollToElement (WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
        js.executeScript("window.scrollBy(0,-100)", "");
    }

    public FicheProduitPage chooseSimilarProduitLink (String productName) {
        WebElement topProducts = driver.findElement(autresProductContainer);
        scrollToElement(topProducts);
        String listeTitle = driver.findElement(By.cssSelector("#main > section:nth-child(6) > h2 > span")).getText();
        List<WebElement> allProductsTitle = driver.findElements(By
                .cssSelector("#owl-same > div.owl-stage-outer > div > div > article > div > div.wb-product-desc.text-xs-left > h2"));
        System.out.println(ANSI_JAUNE+" La liste "+ANSI_BLEU_BACKGROUND+listeTitle+ANSI_RESET+ANSI_JAUNE+" contient "+ANSI_RESET+allProductsTitle.size()+ANSI_JAUNE+" elements"+ ANSI_RESET);
        String urlHome = driver.getCurrentUrl();
        for (WebElement webElement : allProductsTitle) {
            System.out.println(ANSI_JAUNE+" La nom du produit est "+ANSI_RESET+webElement.getText());
            String defaultName = webElement.getText();
            if(webElement.getText().equalsIgnoreCase(productName)) {
                wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.visibilityOf(webElement));
                webElement.click();
                break;
            }
            else{
                wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.visibilityOf(webElement));
                WebElement container = driver.findElement(autresProductSection);
                WebElement fleche = driver.findElement(flecheDroiteOfautresProduct);
                clickFlechDroite(container, fleche);
            }
        }
        if (driver.getCurrentUrl().equalsIgnoreCase(urlHome)){
            System.out.println(ANSI_JAUNE+"This name "+ANSI_RESET+productName+ANSI_JAUNE+" of product doesn't exist"+ANSI_RESET);
        }
        Assert.assertNotEquals(driver.getCurrentUrl(), urlHome);
        return new FicheProduitPage(driver);
    }

    public String getPanierQuantity (){
        WebElement panierQuantity = driver.findElement(By
                .cssSelector("#_desktop_cart > div > div > div > div > span.cart-products-count.cart-c"));
        return panierQuantity.getText();  //  .substring(0,2);(0,2) = 0 : indice debut et 2: indice fin
    }

    public void addToFavoris (){
        WebElement favorisButton = driver.findElement(addToFavorisButton);
        favorisButton.click();
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(favorisButton));
    }

    public void closePopupWislist(){
        WebElement popup = driver.findElement(By.className("close-wishlist"));

        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(popup));
        popup.click();
    }

    public void closePopupCompare(){
        WebElement popup = driver.findElement(By.className("close-popcompare"));

        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(popup));
        popup.click();
    }

    public void addToCompare (){
        WebElement comapreButton = driver.findElement(addToCompareButton);
        if (comapreButton.isSelected()){
            System.out.println("Le produit est deja dans la liste de compare");
        }else {
            comapreButton.click();
            wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOf(comapreButton));
        }
    }
}
