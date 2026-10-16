package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

public class CartTest extends BaseTest {

    @Test
    public void addedProductAppearsInCart(){
        String productName = "ADIDAS ORIGINAL";

        ProductsPage productsPage = new LoginPage(getDriver()).login(getEmail(),getPassword());
        Assert.assertTrue(productsPage.isLoaded(),"Product page did not load");

        productsPage.addToCart(productName);
        CartPage cartPage = productsPage.goToCart();

        Assert.assertTrue(cartPage.isProductInCart(productName),productName+"was not found in the cart");

    }
}
