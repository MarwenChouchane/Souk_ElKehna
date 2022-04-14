package tunnelAchat;

import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AchatTest extends BaseTests {

    @Test
    public void testAccesFicheProduitFromHomePage(){
        var ficheProduitPage = homePage.clickProduitLink("Huile De Noisette Clin De Nature");
    }

    @Test
    public void testAjoutProduitFromAgroalimentaireShop(){
        var agroalimentaireShop = homePage.clickAgroalimentaireShopLink();
        var ficheProduitPage = homePage.clickProduitLink("Collier En Laiton Trempé Avec Pierres Jawaher");
        String quantity1 = "2";
        ficheProduitPage.setQuantity(quantity1);
        ficheProduitPage.addToCart();
        ficheProduitPage.continueShopping();
        ficheProduitPage = ficheProduitPage.chooseSimilarProduitLink("Tunique Malia Rouge T S Mnagech");
        String quantity2 = "2";
        ficheProduitPage.setQuantity(quantity2);
        ficheProduitPage.addToCart();
        ficheProduitPage.continueShopping();
        Assert.assertEquals(ficheProduitPage.getPanierQuantity(),
                Integer.parseInt(quantity1)+Integer.parseInt(quantity2)+" article",
                "La quantité dans la panier est incorrecte");
    }

    @Test
    public void testDeleteFromWishlist() throws InterruptedException {
        homePage.addFavorisRapid();
        var favorisPage = homePage.clickFavorisLink();
        favorisPage.deleteFromFavoris("Corbeille Géante Alfa Art...");
        favorisPage.deleteFromFavoris("Huile de noisette clin de...");

    }

    @Test
    public void testAddToList_GoToCheckout_FromFicheProduct (){
        var ficheProduitPage = homePage.clickProduitLink("Tunique Malia Rouge T S Mnagech");
        ficheProduitPage.addToFavoris();
        ficheProduitPage.closePopupWislist();
        ficheProduitPage.addToCompare();
        ficheProduitPage.closePopupCompare();
        String quantity1 = "5";
        ficheProduitPage.setQuantity(quantity1);
        ficheProduitPage.addToCart();
        ficheProduitPage.continueShopping();
        ficheProduitPage = ficheProduitPage.chooseSimilarProduitLink("Tunique Brodé Lain Ecrue T S Mnagech");
        ficheProduitPage.addToFavoris();
        ficheProduitPage.closePopupWislist();
        ficheProduitPage.addToCompare();
        ficheProduitPage.closePopupCompare();
        String quantity2 = "7";
        ficheProduitPage.setQuantity(quantity2);
        ficheProduitPage.addToCart();
        ficheProduitPage.checkOut();
        Assert.assertEquals(ficheProduitPage.getPanierQuantity(),
                Integer.parseInt(quantity1)+Integer.parseInt(quantity2)+" article",
                "La quantité dans la panier est incorrecte");
    }

    @Test
    public void testGoToCheckoutFromHomePage() throws InterruptedException {
        homePage.addToCardRapid();
        var panier = homePage.checkOutRapid();
        panier.getCommandePrice();
        var productsDetails = panier.getDetailsProducts(2);
        productsDetails.getTitle();
        productsDetails.getUnitPrice();
        String firstPrice = productsDetails.getPrice();
        productsDetails.setQuantity("10");
        String newPrice = productsDetails.getPrice();
        Assert.assertNotEquals(firstPrice, newPrice);
        //panier.commander();
    }
}
