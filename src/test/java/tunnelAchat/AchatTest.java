package tunnelAchat;

import base.BaseTests;
import org.testng.annotations.Test;

public class AchatTest extends BaseTests {
    @Test
    public void testAjoutProduitFromHomePage(){
        var ficheProduitPage = homePage.clickProduitLink("COSCOS2");
    }
}
