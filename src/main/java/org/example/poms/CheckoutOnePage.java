package org.example.poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.example.poms.LoginPage.driver;

public class CheckoutOnePage {

    protected static Properties readConfigFile() {
        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Config.txt"))) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    // Read order details from reader to fill checkout survey
    public static void fillCheckoutData(){

        Properties configProperties = readConfigFile();

        // Retrieve values from configProperties
        String firstName = configProperties.getProperty("firstName");
        String lastName = configProperties.getProperty("lastName");
        String postCode = configProperties.getProperty("postCode");


        WebElement newFirstName = driver.findElement(By.id("first-name"));
        WebElement newLastName = driver.findElement(By.id("last-name"));
        WebElement newPostCode = driver.findElement(By.id("postal-code"));

        newFirstName.sendKeys(firstName);
        newLastName.sendKeys(lastName);
        newPostCode.sendKeys(postCode);

    }

    public static void cancelCheckout(){
        WebElement cancelBtn = driver.findElement(By.id("cancel"));
        cancelBtn.click();
    }

    public static void continueBtn(){
        WebElement continueBtn = driver.findElement(By.id("continue"));
        continueBtn.click();
    }


}
