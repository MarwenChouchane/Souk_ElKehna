package pages.tunnelAchat;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class MonPanier {
    private final WebDriver driver ;
    WebDriverWait wait;

    public static final String ANSI_JAUNE = "\u001B[33m";
    public static final String ANSI_BLEU_BACKGROUND= "\u001B[44m";
    public static final String ANSI_RESET = "\u001B[0m";

    private final By productsList = By.className("cart-item");
    private final By productDetails = By.className("product-line-grid");

    public MonPanier(WebDriver driver) {
        this.driver = driver;
    }

    public boolean waitForJSandJQueryToLoad() {

        wait = new WebDriverWait(driver, 30);

        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active") == 0);
                }
                catch (Exception e) {
                    // no jQuery present
                    return true;
                }
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

    public void pageRefresh (){
        driver.navigate().refresh();
    }

    public String getUrl(){
        return driver.getCurrentUrl();
    }

    public ValidationAdresse commander(){
        driver.findElement(By.linkText("Commander")).click();
        return new ValidationAdresse(driver);
    }

    public void getCommandePrice(){
        String commandePrice = driver.findElement(By.cssSelector("div.cart-summary-line.cart-total > span.value")).getText();
        System.out.println(ANSI_JAUNE+"Le prix actuel de la commande est : "
                +ANSI_RESET+ANSI_BLEU_BACKGROUND+commandePrice+ANSI_RESET);
    }


    public ProductsDetails getDetailsProducts(int index){
        WebElement produitAcheter = driver.findElements(productsList).get(index-1);
        Actions actions = new Actions(driver);
        actions.moveToElement(produitAcheter).perform();
        return new ProductsDetails(produitAcheter.findElement(productDetails));
    }

    public class ProductsDetails {
        private final WebElement details;
        private final By title = By.cssSelector("div.product-line-grid-body > div > a");
        private final By unitPrice = By.cssSelector("div.product-line-grid-body > div > div >span.price");
        private final By price = By.cssSelector("span.product-price");
        private final By deleteButton = By.cssSelector("div.product-line-grid-right > div > div.col-md-2.col-xs-2.text-xs-right > div > a");
        private final By newQuantity = By.cssSelector("div.product-line-grid-right > div > div.col-md-10.col-xs-6 > div > div.col-md-6.col-xs-6.qty> div > input");

        public ProductsDetails (WebElement details){
            this.details = details;
        }

        public void getTitle (){
            System.out.println(ANSI_JAUNE+"Le nom du produit est : "+ANSI_RESET+details.findElement(title).getText());
            details.findElement(title).getText();
        }

        public void getUnitPrice (){
            System.out.println(ANSI_JAUNE+"Le prix unitaire du produit est : "+ANSI_RESET+details.findElement(unitPrice).getText());
            details.findElement(unitPrice).getText();
        }

        public String getPrice (){
            wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(price));
            System.out.println(ANSI_JAUNE+"Le prix total du produit est : "+ANSI_RESET+details.findElement(price).getText());
//            try {
//                System.out.println(ANSI_JAUNE+"Le prix total du produit est : "+ANSI_RESET+details.findElement(price).getText());
//            } catch (StaleElementReferenceException e) {
//                System.err.println("Test lost sync... this will be ignored.");
//            }
            return details.findElement(price).getText();
        }

        public void deleteProductFromPanier (){
            details.findElement(deleteButton).click();
        }

        public String getQuantity(){
            wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(newQuantity));
            System.out.println(ANSI_JAUNE+"La quantité total du produit est : "+ANSI_RESET+details.findElement(newQuantity).getAttribute("value"));
            return details.findElement(newQuantity).getAttribute("value");
        }

        public void setQuantity (String quantity) {
            wait = new WebDriverWait(driver, 30);
            WebElement quantityField = details.findElement(newQuantity);
            clearField(quantityField);
            details.findElement(newQuantity).sendKeys(quantity);
            details.findElement(price).click();
            //waitForJSandJQueryToLoad();

        }

        public void clearField(WebElement element){
//        Actions action = new Actions(driver);
//        action.click(element).perform();
//        element.clear();
            String s = Keys.chord(Keys.CONTROL, "a");
            element.sendKeys(s);
            element.sendKeys(Keys.DELETE);
        }

        public void selectTextField(WebElement element){
            wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            String s = Keys.chord(Keys.CONTROL, "a");
            element.sendKeys(s);
            //element.getText();
        }
    }

}
