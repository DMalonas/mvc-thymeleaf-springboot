package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.persistence.Credential;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.udacity.jwdnd.course1.cloudstorage.util.UtilTests.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialsTests {


    public static final String BEATLES_URL = "https://www.thebeatles.com/";
    public static final String MCCARTNEY_USERNAME = "mccartney";
    public static final String MCCARTNEY_PASSWORD = "mary";
    public static final String RINGO_URL = "http://www.ringostarr.com/";
    public static final String RINGO_USERNAME = "starr";
    public static final String RINGO_PASSWORD = "barbara";
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


//    Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
    @Test
    public void testCredentialCreation() {
        HomePage homePage = getHomePage(driver, baseURL);
        createAndVerifyCredential(BEATLES_URL, MCCARTNEY_USERNAME, MCCARTNEY_PASSWORD, homePage);
        homePage.deleteCredential();
        homePage.logout();
    }




    //Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
    @Test
    public void testCredentialModification() {
        HomePage homePage = getHomePage(driver, baseURL);

        createAndVerifyCredential(BEATLES_URL, MCCARTNEY_USERNAME, MCCARTNEY_PASSWORD, homePage);
        Credential originalCredential = homePage.getFirstCredential();
        String firstEncryptedPassword = originalCredential.getPassword();
        homePage.editCredential();
        String newUrl = RINGO_URL;
        String newCredentialUsername = RINGO_USERNAME;
        String newPassword = RINGO_PASSWORD;
        setCredentialFields(newUrl, newCredentialUsername, newPassword, homePage);
        homePage.saveCredentialChanges();
        homePage.navToCredentialsTab();
        Credential modifiedCredential = homePage.getFirstCredential();
        Assertions.assertEquals(newUrl, modifiedCredential.getUrl());
        Assertions.assertEquals(newCredentialUsername, modifiedCredential.getUserName());
        String modifiedCredentialPassword = modifiedCredential.getPassword();
        Assertions.assertNotEquals(newPassword, modifiedCredentialPassword);
        Assertions.assertNotEquals(firstEncryptedPassword, modifiedCredentialPassword);
        homePage.deleteCredential();
        homePage.logout();
    }


    // Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
    @Test
    public void testDeletion() {
        HomePage homePage = getHomePage(driver, baseURL);

        createCredential(BEATLES_URL, MCCARTNEY_USERNAME, MCCARTNEY_PASSWORD, homePage);
        createCredential(RINGO_URL, RINGO_USERNAME, RINGO_PASSWORD, homePage);
        createCredential("http://www.johnlennon.com/", "lennon", "julia", homePage);

        Assertions.assertFalse(homePage.noCredentials(driver));
        homePage.deleteCredential();

        homePage.navToCredentialsTab();
        homePage.deleteCredential();

        homePage.navToCredentialsTab();
        homePage.deleteCredential();

        homePage.navToCredentialsTab();
        Assertions.assertTrue(homePage.noCredentials(driver));
        homePage.logout();
    }


    //Write a test that edits an existing note and verifies that the changes are displayed.
    private void createAndVerifyCredential(String url, String username, String password, HomePage homePage) {
        createCredential(url, username, password, homePage);
        homePage.navToCredentialsTab();
        Credential credential = homePage.getFirstCredential();
        Assertions.assertEquals(url, credential.getUrl());
        Assertions.assertEquals(username, credential.getUserName());
        Assertions.assertNotEquals(password, credential.getPassword());
    }

    private void createCredential(String url, String username, String password, HomePage homePage) {
        homePage.navToCredentialsTab();
        homePage.addNewCredential();
        setCredentialFields(url, username, password, homePage);
        homePage.saveCredentialChanges();
        homePage.navToCredentialsTab();
    }


    private void setCredentialFields(String url, String username, String password, HomePage homePage) {
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(username);
        homePage.setCredentialPassword(password);
    }

}
