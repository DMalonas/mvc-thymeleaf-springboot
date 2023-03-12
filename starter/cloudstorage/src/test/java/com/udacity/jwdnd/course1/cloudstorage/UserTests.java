package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {
    private static final String LOCALHOST = "http://localhost:";
    private static final String LOGIN = "/login";
    private static final String SIGNUP= "/signup";
    private static final String HOME = "/home";

    private static final String USERNAME = "jSmith";

    private static final String PASSWORD = "1234";
    @LocalServerPort
    private int port;

    private static WebDriver driver;

    public String baseURL;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    @BeforeEach
    public void beforeEach() {
        baseURL = LOCALHOST + port;
    }

    //Write a test that verifies that an unauthorized user can only access the login and signup pages.
    @Test
    public void unauthorizedUserTest() {
        //Cannot access home
        driver.get(baseURL + HOME);
        Assertions.assertEquals(baseURL + LOGIN, driver.getCurrentUrl());
        //Can access login
        driver.get(baseURL + LOGIN);
        Assertions.assertEquals(baseURL + LOGIN, driver.getCurrentUrl());
        //Can access signup
        driver.get(baseURL + SIGNUP);
        Assertions.assertEquals(baseURL + SIGNUP, driver.getCurrentUrl());
    }

    //Write a test that signs up a new user, logs in,
    // verifies that the home page is accessible, logs out,
    // and verifies that the home page is no longer accessible.
    @Test
    public void signUpLoginLogout() {
        driver.get(LOCALHOST + this.port + "/signup");
        Assertions.assertEquals(baseURL + SIGNUP, driver.getCurrentUrl());
        SignUpPage signupPage = new SignUpPage(driver);
        signupPage.signupAndGoToLogin("John", "Smith", "jSmith", "1234");
        driver.get(baseURL + LOGIN);
        Assertions.assertEquals(baseURL + LOGIN, driver.getCurrentUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);
        HomePage homePage = new HomePage(driver);
        homePage.logout();
        driver.get(baseURL + HOME);
        Assertions.assertEquals(baseURL + LOGIN, driver.getCurrentUrl());
    }
}
