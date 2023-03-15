package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.persistence.Credential;
import com.udacity.jwdnd.course1.cloudstorage.util.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import static com.udacity.jwdnd.course1.cloudstorage.util.UtilTests.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialsTests extends BaseTest {
    public static final String URL = "https://www.udacity.com/course/java-developer-nanodegree--nd035";
    public static final String CREDENTIAL = "credential";
    private HomePage homePage;

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        homePage = getHomePage(driver, baseURL);
    }

    //    Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
    @Test
    public void credentialsCreateTest() {
        manipulateCredentials(URL, USERNAME, PASSWORD, homePage, true);
        Assertions.assertTrue(isCredentialDisplayedOnCredentialsPage((Credential) homePage.popObject(CREDENTIAL), URL, USERNAME, PASSWORD));
    }

    //Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted,
    // edits the credentials,
    // and verifies that the changes are displayed.
    @Test
    public void credentialsManipulationTest() {
        manipulateCredentials(URL, USERNAME, PASSWORD, homePage, true);
        manipulateCredentials(URL + 1, USERNAME + 1, PASSWORD + 1, homePage, false);
        Credential editedCredentials = (Credential) homePage.popObject(CREDENTIAL);
        Assertions.assertEquals(URL + 1, editedCredentials.getUrl());
        Assertions.assertEquals(USERNAME+ 1, editedCredentials.getUserName());
        String modifiedCredentialPassword = editedCredentials.getPassword();
        Assertions.assertNotEquals(PASSWORD+ 1, modifiedCredentialPassword);
        homePage.deleteCredentials();
        homePage.logout();
    }

    // Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
    @Test
    public void credentialsDeleteTest() {
        for (int i = 0; i < 5; i++) {
            manipulateCredentials(URL + i, USERNAME+ i, PASSWORD+ i, homePage, true);
        }
        while (true) {
            try {
                homePage.goToTab(driver, 2);
                homePage.deleteCredentials();
            } catch (Exception e) {
                break;
            }
        }
        Assertions.assertTrue(homePage.areElementsAbsent(driver, "credential", By.id("credentialUsername"), By.id("credentialPassword")));
    }

    private boolean isCredentialDisplayedOnCredentialsPage(Credential credential, String url, String username, String password) {
        if (!(url.equals(credential.getUrl()))
                || !(username.equals(credential.getUserName()))
                || (password.equals(credential.getPassword()))) {
            return false;
        }
        return true;
    }

    //Write a test that edits an existing note and verifies that the changes are displayed.
    private void manipulateCredentials(String url, String username, String password, HomePage homePage, boolean isNewEntry) {
        if (isNewEntry) {
            homePage.addCredentials();
        } else {
            homePage.editCredentials();
        }
        homePage.applyCredentials(url, username, password);
        homePage.saveCredentials();
        homePage.goToTab(driver, 2);
    }
}
