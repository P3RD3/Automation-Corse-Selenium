package org.example.tests;

import org.example.poms.InventoryPage;
import org.example.poms.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
public class InvetoryPageTest extends InventoryPage {

@Test
    public static void TestAddItemsToCart(){
        LoginPage loginPage = new LoginPage();
        int expected_count = 2;
        loginPage.login("standard_user","secret_sauce");

        addBackpackToCart();
        addBikeLightToCart();

    Assert.assertEquals(getShoppingCartSize(),expected_count);
    LoginPage.driver.close();
    }

}
