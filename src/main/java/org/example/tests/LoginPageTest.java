package org.example.tests;

import org.example.poms.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class LoginPageTest extends LoginPage {

    private static final long PERFORMANCE_THRESHOLD = 3000;

    @Test
    public static void testLoginForAllUsers() {
        //Providing file path to Config file
        String filePath = "src/main/resources/Config.txt";
        // A boolean to tell the reader from where it should start reading
        boolean startReadingUsers = false;

        try {
            //Create a list of Strings(Userdata) containing all the user data we need for testing
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            //A for loop to read through the list, until it ends
            for (String line : lines) {
                // Skip lines before the "Users:" section
                if (!startReadingUsers && !line.trim().equals("Users:")) {
                    continue;
                }

                // Start reading users after finding the "Users:" section
                startReadingUsers = true;

                // Skip lines that do not contain user data
                if (line.trim().isEmpty() || line.trim().startsWith("wait:")) {
                    continue;
                }
                //Skip TestURL line
                if (line.trim().isEmpty() || line.trim().startsWith("TestURL:")) {
                    continue;
                }
                // Split data as username, password, userType
                String[] data = line.split(",");
                // Once it has read all 3, we start the login process
                if (data.length == 3) {
                    String newUsername = data[0];
                    String newPassword = data[1];
                    String newUserType = data[2];

                    //Since we have different types of users, we have a couple of know outcomes, such as invalid user and lockedout users
                    switch (newUserType) {
                        //This case handles all users that SHOULD be able to log in
                        case "valid", "visual", "problem", "error","performance":
                            //Try-Catch block to intercept expected exception
                            try {
                                /* To make sure that all user have the same performance experience,
                                * there is a 1-second timer in which we expect all user should log in(performance issue user is going to fail here)*/
                                Date startTime = new Date(); // Record the start time of the login
                                login(newUsername, newPassword);
                                Date endTime = new Date(); // Record the end time of the login attempt
                                long loginTime; // Calculate the login time
                                //Calculate the time it took to log in the user
                                loginTime = endTime.getTime() - startTime.getTime();

                                //If user has managed to login faster than our expected threshold the test continues
                                if (loginTime > PERFORMANCE_THRESHOLD) {
                                    driver.close();
                                }
                                //We confirm login by seeing the Inventory page
                                WebElement inventory = driver.findElement(By.id("inventory_container"));
                                Assert.assertTrue(inventory.isDisplayed(), "Login failed for username: " + newUsername);
                                break;
                            }
                            //Since we close the driver prematurely upon detecting a slow login it will throw the below excpetion
                            catch (org.openqa.selenium.NoSuchSessionException e) {
                                // Handle the exception without printing the stack trace
                                System.out.println("User with type '" + newUsername + "' experienced performance issues during login.");
                                driver.close();
                                break;
                            }
                        case "locked":
                            //Locked in user SHOULD NOT be able to login
                            login(newUsername,newPassword);
                            WebDriverWait waitLocked = new WebDriverWait(driver, Duration.ofSeconds(10));
                            //We use explicit wait, to wait out the animation for the error container
                            WebElement errorContainer = waitLocked.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@data-test='error']")));
                            //Read the error container message
                            String errorMessageTextLocked = errorContainer.getText();
                            //Make sure that the user was not able to log in and got the appropriate error message
                            Assert.assertTrue(errorMessageTextLocked.contains("Sorry, this user has been locked out."),
                                    "Unexpected error message for locked-out user");
                            break;

                            //Same as locked user, different error message
                        case "invalid":
                            login(newUsername,newPassword);
                            WebDriverWait waitInvalid = new WebDriverWait(driver, Duration.ofSeconds(10));
                            WebElement errorContainerInvalid = waitInvalid.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@data-test='error']")));
                            String errorMessageTextInvalid = errorContainerInvalid.getText();
                            Assert.assertTrue(errorMessageTextInvalid.contains("Epic sadface: Username and password do not match any user in this service"),
                                    "Unexpected error message for invalid user");
                            break;

                    }

                }

            }

        }
        //In case there is something wrong with config file reader
        catch(IOException e){
            e.printStackTrace();
        }
    }

    //A basic function to test the logout of a user
    @Test
    public static void testLogout() {

        String username = "standard_user";
        String password = "secret_sauce";


        login(username,password);
        //For this particular case we use Implicit wait to wait out the burger menu animation
        logout();
        WebElement loginButton = driver.findElement(By.id("login-button"));
        //Confirm logout by the presence of login button
        Assert.assertTrue(loginButton.isDisplayed(), "Logout failed");
        driver.close();
    }

}

