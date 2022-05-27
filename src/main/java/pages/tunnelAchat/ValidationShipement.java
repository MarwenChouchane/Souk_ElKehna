package pages.tunnelAchat;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;


public class ValidationShipement {
    private final WebDriver driver ;
    WebDriverWait wait;

    public static final String ANSI_JAUNE = "\u001B[33m";
    public static final String ANSI_BLEU_BACKGROUND= "\u001B[44m";
    public static final String ANSI_RESET = "\u001B[0m";

    private final By deleveryOptions = By.className("delivery-options");
    private final By dropDownProductList = By.className("head-cart-drop");
    private final By productsList = By.className("cart-down");
    private final By productsListName = By.className("cart-name");
    private final By productsListDelete = By.className("remove-from-cart");

    public ValidationShipement(WebDriver driver) {
        this.driver = driver;
    }

    public void pageRefresh (){
        driver.navigate().refresh();
    }

    public String getUrl(){
        return driver.getCurrentUrl();
    }

    public void openPanierRapid(){
        boolean cartStyle = Boolean.parseBoolean(driver.findElement(By.className("cartdropd")).getAttribute("aria-expanded"));   //head-cart-drop
        System.out.println("The value of the attribute aria-expanded of the cart Drop is : "+cartStyle);
        //List<WebElement> productsName = null;
        if (cartStyle==false){
            WebElement panierButton = driver.findElement(By.className("cartdropd"));
            panierButton.click();
            waitForPageLoad();
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(driver.findElement(By.className("cartdropd")))));
            cartStyle = Boolean.parseBoolean(driver.findElement(By.className("cartdropd")).getAttribute("aria-expanded"));
            System.out.println("After clicking The value of the attribute aria-expanded of the cart Drop is : "+cartStyle);
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("cart-drop-table")));
        }
    }

    public void productDeleveryCheck(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        List <WebElement> erreurMessage = driver.findElements(By.className("invalid-feedback"));
        if (erreurMessage.size()==0){
            System.out.println(ANSI_JAUNE+"Element not present, appearing "+ANSI_RESET+erreurMessage.size()+ ANSI_JAUNE+" time"+ANSI_RESET);
        }else{
            for (WebElement webElement : erreurMessage){
                String erreur = webElement.getText();
                System.out.println(ANSI_JAUNE+"Nombre des messages d'erreur est "+ANSI_RESET+erreurMessage.size());
                System.out.println(ANSI_JAUNE+"Le texte d'erreur est "+ANSI_RESET+erreur);

                openPanierRapid();
/*
                boolean cartStyle = Boolean.parseBoolean(driver.findElement(By.className("cartdropd")).getAttribute("aria-expanded"));   //head-cart-drop
                System.out.println("The value of the attribute aria-expanded of the cart Drop is : "+cartStyle);
                if (cartStyle==false){
                    WebElement panierButton = driver.findElement(By.className("cartdropd"));
                    panierButton.click();
                    waitForPageLoad();
                    wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(driver.findElement(By.className("cartdropd")))));
                    cartStyle = Boolean.parseBoolean(driver.findElement(By.className("cartdropd")).getAttribute("aria-expanded"));
                    System.out.println("After clicking The value of the attribute aria-expanded of the cart Drop is : "+cartStyle);
                }
                wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className("cart-drop-table")));
*/
                List<WebElement> productsName = driver.findElements(productsList);
                System.out.println(ANSI_JAUNE+"Mon panier contient : "+ANSI_RESET+productsName.size()+ANSI_JAUNE+" Products"+ANSI_RESET);


                for (WebElement webElements : productsName) {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(webElements).perform();
                    System.out.println("Before test contains" + webElements.findElement(productsListName).getText());
                    if (erreur.contains(webElements.findElement(productsListName).getText())) {
                        System.out.println("After test contains" + webElements.findElement(productsListName).getText());
                        webElements.findElement(productsListDelete).click();
                        wait.until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOf(webElements.findElement(productsListDelete))));
                        pageRefresh();
                        break;
                    }
                }
            }
        }
    }

    public void waitForPageLoad() {
        String pageLoadStatus = null;
        JavascriptExecutor js;
        do {
            js = (JavascriptExecutor) driver;
            pageLoadStatus = (String)js.executeScript("return document.readyState");
        } while ( !pageLoadStatus.equals("complete") );
        System.out.println("Page Loaded.");
    }
/*
    public AdressesDetails getAdresse(int indexAdresse){
        List<WebElement> meAdresses = driver.findElements(deleveryOptions);
        System.out.println(ANSI_JAUNE+"Ma list des adresses contient : "+ANSI_RESET+meAdresses.size()+ANSI_JAUNE+" adresses"+ANSI_RESET);
        WebElement mesAdresses = driver.findElements(deleveryOptions).get(indexAdresse-1);
        Actions actions = new Actions(driver);
        actions.moveToElement(mesAdresses).perform();
        return new AdressesDetails(mesAdresses.findElement(adresseCart));
    }
*/
/*    public class AdressesDetails {
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
 */

    public ValidationPaiement validateShipement(){
        driver.findElement(By.name("confirmDeliveryOption")).click();
        return new ValidationPaiement(driver);
    }

}
