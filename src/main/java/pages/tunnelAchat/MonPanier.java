package pages.tunnelAchat;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MonPanier {
    private WebDriver driver ;
    WebDriverWait wait;

    public static final String ANSI_JAUNE = "\u001B[33m";
    public static final String ANSI_BLEU_BACKGROUND= "\u001B[44m";
    public static final String ANSI_RESET = "\u001B[0m";

    private By productsList = By.className("cart-item");
    private By productDetails = By.className("product-line-grid");

    public MonPanier(WebDriver driver) {
        this.driver = driver;
    }

    public void commander(){
        driver.findElement(By.linkText("Commander")).click();
    }

    public void getCommandePrice(){
        String commandePrice = driver.findElement(By.cssSelector("div.cart-summary-line.cart-total > span.value")).getText();
        System.out.println(ANSI_JAUNE+"Le prix actuel de la commande est : "
                +ANSI_RESET+ANSI_BLEU_BACKGROUND+commandePrice+ANSI_RESET);
    }

    public ProductsDetails getDetailsProducts(int index){
        WebElement produitAcheter = driver.findElements(productsList).get(index-1);
        //System.out.println(ANSI_JAUNE+"La panier contient "+ANSI_RESET+ANSI_BLEU_BACKGROUND+produitAcheter.size()+ANSI_RESET+ANSI_JAUNE+" produits"+ANSI_RESET);

        Actions actions = new Actions(driver);
        actions.moveToElement(produitAcheter).perform();
        return new ProductsDetails(produitAcheter.findElement(productDetails));
    }

    public class ProductsDetails {
        private WebElement details;
        private By title = By.cssSelector("div.product-line-grid-body > div > a");
        private By unitPrice = By.cssSelector("div.product-line-grid-body > div > div >span.price");
        private By Price = By.cssSelector("div.product-line-grid-right > div > div.col-md-10.col-xs-6 > div > div.col-md-6.col-xs-2.price > span.product-price ");
        private By deleteButton = By.cssSelector("div.product-line-grid-right > div > div.col-md-2.col-xs-2.text-xs-right > div > a");
        private By newQuantity = By.cssSelector("div.product-line-grid-right > div > div.col-md-10.col-xs-6 > div > div.col-md-6.col-xs-6.qty> div > input");

        public ProductsDetails (WebElement details){
            this.details = details;
        }

        public String getTitle (){
            System.out.println(ANSI_JAUNE+"Le nom du produit est : "+ANSI_RESET+details.findElement(title).getText());
            return details.findElement(title).getText();
        }

        public String getUnitPrice (){
            System.out.println(ANSI_JAUNE+"Le prix unitaire du produit est : "+ANSI_RESET+details.findElement(unitPrice).getText());
            return details.findElement(unitPrice).getText();
        }

        public String getPrice (){
            System.out.println(ANSI_JAUNE+"Le prix total du produit est : "+ANSI_RESET+details.findElement(Price).getText());
            return details.findElement(Price).getText();
        }

        public void deleteProductFromPanier (){
            details.findElement(deleteButton).click();
        }

        public void setQuantity (String quantity){
            details.findElement(newQuantity).sendKeys(quantity);
        }
    }
    public void clearField(WebElement element){
//        Actions action = new Actions(driver);
//        action.click(element).perform();
//        element.clear();
        String s = Keys.chord(Keys.CONTROL, "a");
        element.sendKeys(s);
        element.sendKeys(Keys.DELETE);
    } // il faut terminer la fonction setQuantity : clear field
}
