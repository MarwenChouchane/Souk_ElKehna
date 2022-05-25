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
        getChatBoxManager().hideElement("lc_chatbox");   //To hide the chatBox

        var panier = homePage.checkOutRapid();
        String panierUrl = panier.getUrl();
        panier.getCommandePrice();
        var productsDetails = panier.getDetailsProducts(2);
        productsDetails.getTitle();
        productsDetails.getUnitPrice();
        String oldQuantity = productsDetails.getQuantity();
        String oldPrice = productsDetails.getPrice();
        productsDetails.setQuantity("3");
        String newQuantity = productsDetails.getQuantity();
        panier.pageRefresh();
        productsDetails = panier.getDetailsProducts(2);
        String newPrice = productsDetails.getPrice();
        Assert.assertNotEquals(oldQuantity, newQuantity);
        Assert.assertNotEquals(oldPrice, newPrice);

        var adresse = panier.commander();
        String adresseValidationUrl = adresse.getUrl();
        Assert.assertNotEquals(panierUrl, adresseValidationUrl);
        var adresseDetail = adresse.getAdresse(2);
        adresseDetail.getadresseDetail();
        adresseDetail.chooseAdresse();

        var delevery = adresse.validateAdresse();
        String deleveryValidationUrl = delevery.getUrl();
        Assert.assertEquals(deleveryValidationUrl, adresseValidationUrl);
        delevery.productDeleveryCheck(); //To check if there is a probleme with the delevery for those products

        var paiement = delevery.validateShipement();
    }
}
