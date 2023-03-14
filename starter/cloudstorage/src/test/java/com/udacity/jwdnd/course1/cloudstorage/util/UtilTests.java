package com.udacity.jwdnd.course1.cloudstorage.util;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UtilTests {

    public static final String LOCALHOST = "http://localhost:";
    public static final String LOGIN = "/login";
    public static final String SIGNUP= "/signup";
    public static final String HOME = "/home";
    public static final String USERNAME = "jSmith";
    public static final String PASSWORD = "1234";


    public static HomePage getHomePage(WebDriver driver, String baseURL) {
        HomePage homePage = new HomePage(driver);
        SignUpPage signUpPage = new SignUpPage(driver);
        driver.get(baseURL + SIGNUP);
        signUpPage.signupAndGoToLogin(USERNAME, PASSWORD, USERNAME, PASSWORD);
        driver.get(baseURL + LOGIN);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);
        return homePage;
    }


}
