package monCompte;

import base.BaseTests;
import loginTest.LoginTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class HistoriqueTests extends BaseTests {

    @Test
    public void testcheckCommand(){
        var loginPage = homePage.clickLoginLink();
        loginPage.setEmail("marwen.chouchane@dotit-corp.com");
        loginPage.setPassword("123456789");
        var monComptePage = loginPage.loginSubmit();
        assertEquals(monComptePage.getTitle(), "Mon compte", "This is incorrecte page");
        homePage.backHomePage();
        var monComptPage = homePage.clickMonCompteLink();
        var historiquePage = monComptPage.clickHistoriqueCommandeLink();
        historiquePage.checkRefrenceExist("MGBBZRDQQ");
    }
}
