package org.example.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.example.poms.LoginPage.driver;

public class CheckoutTwoPage {

    public static void cancelCheckoutTwo(){
            WebElement cancelBtn = driver.findElement(By.id("cancel"));
            cancelBtn.click();
    }

    public static void finishCheckout(){
        WebElement finishBtn = driver.findElement(By.id("finish"));
        finishBtn.click();
    }

    public static String getCheckoutItemsPrice(){
        List<WebElement> titleElements = driver.findElements(By.className("inventory_item_price"));
        List<String> checkoutItemsPrices = new ArrayList<>();
        for (WebElement titleElement : titleElements) {
            checkoutItemsPrices.add(titleElement.getText());
        }
        double totalPrice = 0.0;
        for (String price : checkoutItemsPrices) {
            // Remove the "$" sign and any non-numeric characters, then convert to double
            double numericPrice = Double.parseDouble(price.replaceAll("[^\\d.]", ""));
            totalPrice += numericPrice;
        }
        // Format the total price with "$" sign and two decimal places
        String totalCheckout = String.format("$%.2f", totalPrice);

        return totalCheckout;
    }

    public static String getTotalPrice(){
        WebElement subtotalElement = driver.findElement(By.cssSelector("div.summary_subtotal_label"));
        String subtotalText = subtotalElement.getText();
        String[] parts = subtotalText.split(":");
        if (parts.length == 2) {
            return parts[1].trim(); // Extract and return the subtotal amount
        } else {
            return ""; // Return empty string if the format is not as expected
        }
    }



}
