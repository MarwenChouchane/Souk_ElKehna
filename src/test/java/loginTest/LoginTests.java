package loginTest;

import base.BaseTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


public class LoginTests extends BaseTests {

    @Test
    public void login(){
        var loginPage = homePage.clickLoginLink();
        loginPage.setEmail("marwen.chouchane@dotit-corp.com");
        loginPage.setPassword("123456789");
        var monComptePage = loginPage.loginSubmit();
        assertEquals(monComptePage.getTitle(), "Mon compte", "This is incorrecte page");
    }
}
