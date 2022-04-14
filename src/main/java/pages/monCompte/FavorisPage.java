package pages.monCompte;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class FavorisPage {

    private WebDriver driver ;
    WebDriverWait wait;

    public static final String ANSI_ROUGE = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";


    public FavorisPage (WebDriver driver) {
        this.driver = driver;
    }

    public void openWishlist(){
        WebElement wishlistLink = driver.findElement(By.cssSelector("tr > td:nth-child(1) > a"));
        wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(wishlistLink));
        wishlistLink.click();
        WebElement wishlist = driver.findElement(By.className("wlp_bought"));
        wait.until(ExpectedConditions.visibilityOf(wishlist));
    }

    public void scrollToElement (WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
        js.executeScript("window.scrollBy(0,-50)", "");
    }

    public void delete(){
        driver.findElement(By.cssSelector("div.wlp_bought > ul > li > div > div:nth-child(2) > div > a")).click();
    }

    public void deleteFromFavoris(String productsName){
        openWishlist();

        List<WebElement> allProductsTitle = driver.findElements(By
                .cssSelector("div.wlp_bought > ul > li > div > div:nth-child(2) > div > div.product-name > a"));
        int defaultSize = allProductsTitle.size();
        System.out.println(ANSI_ROUGE+"The default size is : "+ANSI_RESET+defaultSize);
        wait = new WebDriverWait(driver, 5);
        for (WebElement webElement : allProductsTitle) {
            wait.until(ExpectedConditions.visibilityOf(webElement));
            System.out.println(ANSI_ROUGE+"Le produit a supprimer de la list favoris est : "+ANSI_RESET+webElement.getText());
            if (webElement.getText().contains(productsName)) {
                WebElement deleteButton = driver.findElement(By.cssSelector("div.wlp_bought > ul > li > div > div:nth-child(2) > div > a"));
                if (deleteButton.isDisplayed()) {
                    wait.until(ExpectedConditions.visibilityOf(webElement));
                    Actions actions = new Actions(driver);
                    actions.moveToElement(webElement).perform();
                    delete();
                    wait.until(ExpectedConditions.invisibilityOf(webElement));
                    break;
                } else {
                    scrollToElement(deleteButton);
                    delete();
                    wait.until(ExpectedConditions.invisibilityOf(webElement));
                }
            } else {
                System.out.println("This product " + productsName + " Doesnt existe in the favoris liste");
            }
        }
        driver.navigate().refresh();
        openWishlist();
        allProductsTitle = driver.findElements(By
                .cssSelector("div.wlp_bought > ul > li > div > div:nth-child(2) > div > div.product-name > a"));
        int currentSize = allProductsTitle.size();
        System.out.println(ANSI_ROUGE+"The current size is : "+ANSI_RESET+currentSize);
        Assert.assertNotEquals(currentSize, defaultSize);
        driver.navigate().refresh();
    }
}

