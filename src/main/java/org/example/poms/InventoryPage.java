package org.example.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


import static org.example.poms.LoginPage.driver;

public class InventoryPage {

    //Create object of LoginPage to user methods needed from there(Login)
    private LoginPage loginPage;

    //Constructor to call this page if we need it and get access to its methods
    public InventoryPage() {
    }

    //Basic add item methods
    public static void addBackpackToCart(){
        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addToCartButton.click();
    }
    public static void addBoltTshirtToCart (){

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt"));
        addToCartButton.click();
    }
    public static void addBikeLightToCart (){

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        addToCartButton.click();
    }
    public static void addFleeceJacketToCart (){

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"));
        addToCartButton.click();
    }
    public static void addOnesieToCart (){

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-onesie"));
        addToCartButton.click();
    }
    public static void addAllTheThingsTshirtToCart (){

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)"));
        addToCartButton.click();
    }

    //Methods to get the amount of items in cart(badge in the top right)
    public static int getShoppingCartSize(){

        WebElement shoppingCartBadge = driver.findElement(By.className("shopping_cart_badge"));
        String cartItemCountText = shoppingCartBadge.getText();

        return Integer.parseInt(cartItemCountText);
    }

    //Booleans that check if the Item images are the same
    public static boolean verifyBackpackImage() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user","secret_sauce");
        return verifyItemImageSource("4", "/static/media/sauce-backpack-1200x1500.0a0b85a3.jpg");
    }

    public static boolean verifyBoltTshirtImage() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user","secret_sauce");
        return verifyItemImageSource("1", "/static/media/bolt-shirt-1200x1500.c2599ac5.jpg");
    }

    public static boolean verifyBikeLightImage() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user","secret_sauce");
        return verifyItemImageSource("0", "/static/media/bike-light-1200x1500.37c843b0.jpg");
    }

    public static boolean verifyFleeceJacketImage() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user","secret_sauce");
        return verifyItemImageSource("5", "/static/media/sauce-pullover-1200x1500.51d7ffaf.jpg");
    }

    public static boolean verifyOnesieImage() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user","secret_sauce");
        return verifyItemImageSource("2", "/static/media/red-tatt-1200x1500.30dadef4.jpg");
    }

    public static boolean verifyAllTheThingsTshirtImage() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user","secret_sauce");
        return verifyItemImageSource("3", "/static/media/red-onesie-1200x1500.2ec615b2.jpg");
    }

    //This finds the current image and checks it with the original
    public static boolean verifyItemImageSource(String itemId, String expectedImageSource) {
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

//Methods to user the navigation buttons
    public static void backToProducts(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement backToProductsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("back-to-products")));
       backToProductsButton.click();
    }

    public static void goToCart(){
        WebElement cartButton = driver.findElement(By.id("shopping_cart_container"));
        cartButton.click();
    }

}
