package utiles;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ChatBoxManager {
    private WebDriver driver ;
    private WebDriver.Navigation navigate ;

    public ChatBoxManager(WebDriver driver){
        this.driver = driver ;
    }

    public void hideElement(String className) {
        WebElement element = driver.findElement(By.className(className));
        ((JavascriptExecutor)driver).executeScript("arguments[0].style = 'position:unset;'", element);
    }
}
