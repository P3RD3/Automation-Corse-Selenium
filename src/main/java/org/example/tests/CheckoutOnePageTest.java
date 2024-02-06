package org.example.tests;

import org.example.poms.CartPage;
import org.example.poms.CheckoutOnePage;
import org.example.poms.InventoryPage;
import org.example.poms.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import static org.example.poms.LoginPage.driver;
import static org.testng.Assert.assertTrue;

public class CheckoutOnePageTest extends CheckoutOnePage {
    @Test
    public static void verifyCheckout(){
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();
        CartPage cartPage = new CartPage();

        loginPage.login("standard_user","secret_sauce");
        inventoryPage.addBackpackToCart();
        inventoryPage.goToCart();
        cartPage.proceedToCheckout();

        fillCheckoutData();
        continueBtn();

        WebElement finish = driver.findElement(By.id("finish"));
        assertTrue(finish.isDisplayed(),"Proceed to 2nd part failed");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();

    }
@Test
    public static void verifyCancel() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();
        CartPage cartPage = new CartPage();

        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addBackpackToCart();
        inventoryPage.goToCart();
        cartPage.proceedToCheckout();

        cancelCheckout();

        WebElement continueShoppingBtn = driver.findElement(By.id("continue-shopping"));
        assertTrue(continueShoppingBtn.isDisplayed(),"Failed to cancel out of checkout");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();
    }



}
