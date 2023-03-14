package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import com.udacity.jwdnd.course1.cloudstorage.util.BaseTest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static com.udacity.jwdnd.course1.cloudstorage.util.UtilTests.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests extends BaseTest {


    //Write a test that verifies that an unauthorized user can only access the login and signup pages.
    @Test
    public void onlyLoginAndSignupAccessibleToUnauthorizedUserTest() {
        // Verify that unauthorized user cannot access restricted pages
        navigateToPageAndVerifyURL(baseURL + HOME, baseURL + LOGIN);
        navigateToPageAndVerifyURL(baseURL + LOGIN, baseURL + LOGIN);
        navigateToPageAndVerifyURL(baseURL + SIGNUP, baseURL + SIGNUP);
        navigateToPageAndVerifyURL(baseURL + HOME, baseURL + LOGIN);
    }

    //Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.
    @Test
    public void userAuthenticationFlowTest() {
        navigateToPageAndVerifyURL(baseURL + SIGNUP, baseURL + SIGNUP);
        SignUpPage signupPage = new SignUpPage(driver);
        signupPage.signupAndGoToLogin(USERNAME, USERNAME, USERNAME, PASSWORD);
            navigateToPageAndVerifyURL(baseURL + LOGIN, baseURL + LOGIN);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);
        HomePage homePage = new HomePage(driver);
        homePage.logout();
        navigateToPageAndVerifyURL(baseURL + HOME, baseURL + LOGIN);
    }


    private void navigateToPageAndVerifyURL(String url, String expectedURL) {
        driver.get(url);
        Assertions.assertEquals(expectedURL, driver.getCurrentUrl());
    }
}
