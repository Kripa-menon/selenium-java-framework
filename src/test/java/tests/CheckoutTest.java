package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutTest extends BaseTest {

    @Test
    public void placeOrderAppearsInOrders(){
        String productName ="ADIDAS ORIGINAL";
        String country = "India";

        ProductsPage productsPage = new LoginPage(driver).login(props.getProperty("email"),props.getProperty("password"));
        Assert.assertTrue(productsPage.isLoaded(),"Products page did not load");

        productsPage.addToCart(productName);
        CartPage cartPage = productsPage.goToCart();
        Assert.assertTrue(cartPage.isProductInCart(productName),productName + "was not in the cart");

        CheckoutPage checkoutPage = cartPage.gotoCheckout();
        checkoutPage.selectCountry(country);
        ConfirmationPage confirmationPage = checkoutPage.placeOrder();

        String orderId = confirmationPage.getOrderId();
        Assert.assertFalse(orderId.isEmpty(),"Order ID was enpty on the confirmation page");

        OrdersPage ordersPage = confirmationPage.gotoOrders();
        Assert.assertTrue(ordersPage.isOrderPresent(orderId),"order"+ orderId+"not found on orders page");


    }
}
