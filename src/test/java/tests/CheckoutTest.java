package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.JsonReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CheckoutTest extends BaseTest {

    @DataProvider(name = "orderData")
    public Object[][] orderData() throws IOException{
        List<Map<String,String>> data = JsonReader.readData("src/test/resources/testdata/orders.json");
        Object[][] result= new Object[data.size()][1];
        for(int i =0;i< data.size();i++){
            result[i][0] = data.get(i);
        }
        return result;
    }

    @Test(dataProvider = "orderData")
    public void placeOrderAppearsInOrders(Map<String, String > data){
        String productName =data.get("productName");
        String country = data.get("country");

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
