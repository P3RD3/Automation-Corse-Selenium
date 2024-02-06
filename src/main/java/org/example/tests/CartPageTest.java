package org.example.tests;

import org.example.poms.CartPage;
import org.example.poms.InventoryPage;
import org.example.poms.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.example.poms.LoginPage.driver;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CartPageTest extends CartPage {

    private static List<String> getItemTitles() {
        List<WebElement> titleElements = driver.findElements(By.className("inventory_item_name"));
        List<String> itemTitles = new ArrayList<>();
        for (WebElement titleElement : titleElements) {
            itemTitles.add(titleElement.getText());
        }
        return itemTitles;
    }

    @Test
    public static void verifyItemRemoval(){
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();
        loginPage.login("standard_user","secret_sauce");
        inventoryPage.addBackpackToCart();
        inventoryPage.goToCart();

        List<String> itemTitlesBeforeRemoval = getItemTitles();

        // Remove the backpack from the shopping cart
        removeBackpack();

        // Capture titles of items in the shopping cart after removal
        List<String> itemTitlesAfterRemoval = getItemTitles();

        // Assert that the removed item's title is not present in the list after removal
        assertFalse(itemTitlesAfterRemoval.contains("Backpack"));
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.close();
    }

    @Test
    public static void checkContinueShoppingBtn() {
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.goToCart();
        continueShopping();

        WebElement inventory = driver.findElement(By.id("inventory_container"));
        Assert.assertTrue(inventory.isDisplayed(), "Return to shopping failed");
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.close();
    }

    @Test
    public static void verifyproceedToCheckout(){
        LoginPage loginPage = new LoginPage();
        InventoryPage inventoryPage = new InventoryPage();
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.goToCart();

        proceedToCheckout();

        WebElement firstName = driver.findElement(By.id("first-name"));
        Assert.assertTrue(firstName.isDisplayed(), "Proceed to checkout failed");

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        driver.close();
    }


}
