package monCompte;

import base.BaseTests;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import pages.monCompte.HistoriquePage;
import pages.monCompte.MonComptePage;


import static org.testng.Assert.assertEquals;

public class HistoriqueTests extends BaseTests {

    private MonComptePage monComptePage = new MonComptePage(driver);

    @BeforeClass
    public static void startVisualTestSuite(){
        eyesManager.setBatchName("Historique page");
        driver.get(System.getProperty("site.monCompte.url"));
        //driver.get(System.getProperty("site.historiqueCommandes.url"));
    }

    @Test
    public void testValidateWindow(){
        eyesManager.validateWindow();
    }

    @Test
    public void testcheckCommand(){
/*        var loginPage = homePage.clickLoginLink();
        loginPage.setEmail("marwen2@yopmail.com");
        loginPage.setPassword("123456789");
        var monComptePage = loginPage.loginSubmit();
        assertEquals(monComptePage.getTitle(), "Mon compte", "This is incorrecte page");
*/        //homePage.goHome();
        monComptePage = homePage.clickMonCompteLink();
        var historiquePage = monComptePage.clickHistoriqueCommandeLink();
        historiquePage.checkRefrenceExist("MGBBZRDQQ");
        //eyesManager.validateWindow();
        eyesManager.validateElement(By.id("content-wrapper"));
    }
}
