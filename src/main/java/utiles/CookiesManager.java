package utiles;

//Get network response
//            String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
//            String netData = ((JavascriptExecutor)driver).executeScript(scriptToExecute).toString();
//            System.out.println(netData);

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class CookiesManager {
    private WebDriver driver;

    public CookiesManager(WebDriver driver){
        this.driver = driver;
    }

    public void addCookie(Cookie cookie){
        driver.manage().addCookie(cookie);
    }

    public void deleteCookie(Cookie cookie){
        driver.manage().deleteCookie(cookie);
    }

    public boolean isCookiePresent(Cookie cookie){
        return driver.manage().getCookieNamed(cookie.getName()) != null;
    }

    public Cookie buildCookie(String name, String value){
        Cookie cookie = new Cookie.Builder(name, value)
                .domain("the-internet.herokuapp.com")
                .build();
        return cookie;
    }
}
