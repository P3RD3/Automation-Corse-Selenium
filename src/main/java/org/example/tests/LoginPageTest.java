package org.example.tests;

import org.example.poms.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class LoginPageTest extends LoginPage {
    private static Properties readConfigFile() {
        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Config.txt"))) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Test
    public static void testLoginForAllUsers() {
        String filePath = "C:\\Users\\Boev\\IdeaProjects\\Selenium\\src\\main\\resources\\Config.txt";
        boolean startReadingUsers = false;

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

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
                String[] data = line.split(",");
                if (data.length == 3) {
                    String newUsername = data[0];
                    String newPassword = data[1];
                    String newUserType = data[2];

                    LoginPage loginPage = new LoginPage();

                    switch (newUserType) {

                        case "valid", "visual", "problem", "error":
                            loginPage.login(newUsername,newPassword);
                            WebElement inventory = driver.findElement(By.id("inventory_container"));
                            Assert.assertTrue(inventory.isDisplayed(), "Login failed for username: " + newUsername);
                            driver.close();
                            break;
                        case "locked":
                            loginPage.login(newUsername,newPassword);
                            WebDriverWait waitLocked = new WebDriverWait(driver, Duration.ofSeconds(10));
                            WebElement errorContainer = waitLocked.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@data-test='error']")));
                            String errorMessageTextLocked = errorContainer.getText();
                            Assert.assertTrue(errorMessageTextLocked.contains("Sorry, this user has been locked out."),
                                    "Unexpected error message for locked-out user");
                            driver.close();
                            break;
                        case "invalid":
                            loginPage.login(newUsername,newPassword);
                            WebDriverWait waitInvalid = new WebDriverWait(driver, Duration.ofSeconds(10));
                            WebElement errorContainerInvalid = waitInvalid.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@data-test='error']")));
                            String errorMessageTextInvalid = errorContainerInvalid.getText();
                            Assert.assertTrue(errorMessageTextInvalid.contains("Epic sadface: Username and password do not match any user in this service"),
                                    "Unexpected error message for invalid user");
                            driver.close();
                            break;
                        case "performance":
                            loginPage.login(newUsername,newPassword);
                            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                            inventory = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
                            Assert.assertTrue(inventory.isDisplayed(), "Login failed for username: " + newUsername);
                            driver.close();
                            break;

                    }

                }

            }

        } catch(IOException e){
            e.printStackTrace();
        }
    }
    @Test
    public static void testLogout() {

        LoginPage loginPage = new LoginPage();
        String username = "standard_user";
        String password = "secret_sauce";

        Properties configProperties = readConfigFile();
        String implicitWaitValue = configProperties.getProperty("wait");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(implicitWaitValue)));
        loginPage.login(username,password);
        loginPage.logout();

        WebElement loginButton = driver.findElement(By.id("login-button"));
        Assert.assertTrue(loginButton.isDisplayed(), "Logout failed");
        driver.close();
    }

    public static void TestLoginPage(){
        testLoginForAllUsers();
        testLogout();
    }

}

