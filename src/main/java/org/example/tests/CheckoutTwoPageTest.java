package org.example.tests;

import org.example.poms.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.example.poms.LoginPage.driver;

public class CheckoutTwoPageTest extends CheckoutTwoPage {

    @Test
    public static void verifyCancelBtn(){
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();
        CartPage cartPage = new CartPage();
        CheckoutOnePage checkoutOnePage = new CheckoutOnePage();

        loginPage.login("standard_user","secret_sauce");
        InventoryPage.addBackpackToCart();
        InventoryPage.goToCart();
        CartPage.proceedToCheckout();
        CheckoutOnePage.fillCheckoutData();
        CheckoutOnePage.continueBtn();

        cancelCheckoutTwo();

        WebElement inventory = driver.findElement(By.id("inventory_container"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();

    }
    @Test
    public static void verifyFinishBtn() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();
        CartPage cartPage = new CartPage();
        CheckoutOnePage checkoutOnePage = new CheckoutOnePage();

        loginPage.login("standard_user", "secret_sauce");
        InventoryPage.addBackpackToCart();
        InventoryPage.goToCart();
        CartPage.proceedToCheckout();
        CheckoutOnePage.fillCheckoutData();
        CheckoutOnePage.continueBtn();

        finishCheckout();

        WebElement backHomeBtn = driver.findElement(By.id("back-to-products"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();
    }

    @Test
    public static void checkFinalPrice(){
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();
        CartPage cartPage = new CartPage();
        CheckoutOnePage checkoutOnePage = new CheckoutOnePage();

        loginPage.login("standard_user", "secret_sauce");
        InventoryPage.addBackpackToCart();
        InventoryPage.addBikeLightToCart();
        InventoryPage.goToCart();
        CartPage.proceedToCheckout();
        CheckoutOnePage.fillCheckoutData();
        CheckoutOnePage.continueBtn();

        String checkoutItemsPrice = getCheckoutItemsPrice();
        String totalPrice = getTotalPrice();

        // Assert that the prices match
        Assert.assertEquals(checkoutItemsPrice, totalPrice);

        driver.close();

    }

}
