package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.example.LoginPage.driver;

public class InventoryPage {
    private LoginPage loginPage;
    protected static void addBackpackToCart(){
        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addToCartButton.click();
    }
    protected static void addBoltTshirtToCart (){

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt"));
        addToCartButton.click();
    }
    protected static void addBikeLightToCart (){

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        addToCartButton.click();
    }
    protected static void addFleeceJacketToCart (){

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"));
        addToCartButton.click();
    }
    protected static void addOnesieToCart (){

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-onesie"));
        addToCartButton.click();
    }
    protected static void addAllTheThingsTshirtToCart (){

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)"));
        addToCartButton.click();
    }

    protected static int getShoppingCartSize(){

        WebElement shoppingCartBadge = driver.findElement(By.className("shopping_cart_badge"));
        String cartItemCountText = shoppingCartBadge.getText();

        return Integer.parseInt(cartItemCountText);
    }
    protected static boolean verifyBackpackImage() {
        return verifyItemImageSource("4", "/static/media/sauce-backpack-1200x1500.0a0b85a3.jpg");
    }
    protected static boolean verifyBoltTshirtImage() {
        return verifyItemImageSource("1", "/static/media/bolt-shirt-1200x1500.c2599ac5.jpg");
    }
    protected static boolean verifyBikeLightImage() {
        return verifyItemImageSource("0", "/static/media/bike-light-1200x1500.37c843b0.jpg");
    }
    protected static boolean verifyFleeceJacketImage() {
        return verifyItemImageSource("5", "/static/media/sauce-pullover-1200x1500.51d7ffaf.jpg");
    }
    protected static boolean verifyOnesieImage() {
        return verifyItemImageSource("2", "/static/media/red-tatt-1200x1500.30dadef4.jpg");
    }
    protected static boolean verifyAllTheThingsTshirtImage() {
        return verifyItemImageSource("3", "/static/media/red-onesie-1200x1500.2ec615b2.jpg");
    }
    private static boolean verifyItemImageSource(String itemId, String expectedImageSource) {
        String itemImageLinkId = "item_" + itemId + "_img_link";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement itemImageLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(itemImageLinkId)));

        if (itemImageLink != null) {
            WebElement imageElement = itemImageLink.findElement(By.tagName("img"));
            String actualImageSource = imageElement.getAttribute("src");
            return actualImageSource.contains(expectedImageSource);
        } else {
            return false; // Element not found, verification fails
        }
    }

    protected static boolean verifyItemUniquePage(String itemId) {
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
            return currentUrlAfterClick.contains("inventory-item.html?id=" + itemId);
        } else {
            return false; // Element not found, verification fails
        }
    }

    protected static void backToProducts(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement backToProductsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("back-to-products")));
       backToProductsButton.click();
    }

    public static void TestInventoryPage(){
        LoginPage loginPage = new LoginPage();

        loginPage.login("standard_user","secret_sauce");

        addBackpackToCart();

        System.out.println(getShoppingCartSize());
        System.out.println(verifyBackpackImage());
        System.out.println(verifyItemUniquePage("3"));


    }

}
