package org.example.tests;

import org.example.poms.InventoryPage;
import org.example.poms.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.example.poms.LoginPage.driver;

public class InvetoryPageTest extends InventoryPage {

    @Test
    public static void verifyInventoryImages()
    {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user","secret_sauce");
        boolean backpackImageVerified = verifyBackpackImage();
        driver.close();
        boolean onesieImageVerified = verifyOnesieImage();
        driver.close();
        boolean allTheThingsTshirtImageVerified = verifyAllTheThingsTshirtImage();
        driver.close();
        boolean bikeLightImageVerified = verifyBikeLightImage();
        driver.close();
        boolean fleeceJacketImageVerified = verifyFleeceJacketImage();
        driver.close();
        boolean boltTshirtImageVerified = verifyBoltTshirtImage();
        driver.close();

        // Assert that at least one of the verification methods returned false
        Assert.assertTrue(!backpackImageVerified || !onesieImageVerified || !allTheThingsTshirtImageVerified || !bikeLightImageVerified || !fleeceJacketImageVerified || !boltTshirtImageVerified,"At least one image verification failed");
    }
@Test
    public static boolean verifyItemUniquePage(String itemId) {
    LoginPage loginPage = new LoginPage();
    loginPage.login("standard_user","secret_sauce");

        String titleButtonId = "item_" + itemId + "_img_link";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement titleButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(titleButtonId)));

        if (titleButton != null) {
            // Get the current URL before clicking
            String currentUrlBeforeClick = driver.getCurrentUrl();

            // Click on the title button
            titleButton.click();

            // Wait for the new URL to load
            wait.until(ExpectedConditions.urlContains("inventory-item.html?id=" + itemId));

            // Get the current URL after clicking
            String currentUrlAfterClick = driver.getCurrentUrl();

            backToProducts();
            // Verify that the current URL contains the expected item URL
            LoginPage.driver.close();
            return currentUrlAfterClick.contains("inventory-item.html?id=" + itemId);
        } else {
            LoginPage.driver.close();
            return false; // Element not found, verification fails

        }
    }

@Test
    public static void TestAddItemsToCart(){
        int expected_count = 2;
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user","secret_sauce");

        addBackpackToCart();
        addBikeLightToCart();

    Assert.assertEquals(getShoppingCartSize(),expected_count);
    LoginPage.driver.close();
    }

}
