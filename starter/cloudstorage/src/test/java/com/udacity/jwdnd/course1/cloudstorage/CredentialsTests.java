package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.persistence.Credential;
import com.udacity.jwdnd.course1.cloudstorage.util.BaseTest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import static com.udacity.jwdnd.course1.cloudstorage.util.UtilTests.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialsTests extends BaseTest {
    public static final String URL = "https://www.udacity.com/course/java-developer-nanodegree--nd035";
    public static final String CREDENTIAL = "credential";
    private HomePage homePage;

    @BeforeEach
    public void beforeEach() {
        super.beforeEach(); // Call the superclass beforeEach method to set up the driver and baseURL
        homePage = getHomePage(driver, baseURL);
    }

    //    Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
    @Test
    public void newCredentialTest() {
        createCredential(URL, USERNAME, PASSWORD, homePage);
        Assertions.assertTrue(isCredentialDisplayedOnCredentialsPage((Credential) homePage.getFirstObject(CREDENTIAL), URL, USERNAME, PASSWORD));
    }





    //Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted,
    // edits the credentials,
    // and verifies that the changes are displayed.
    @Test
    public void testCredentialModification() {
        createCredential(URL, USERNAME, PASSWORD, homePage);
        updateCredentialsEntry();
        homePage.goToTab(driver, 2);
        Credential editedCredentials = (Credential) homePage.getFirstObject(CREDENTIAL);
        Assertions.assertEquals(URL + 1, editedCredentials.getUrl());
        Assertions.assertEquals(USERNAME+ 1, editedCredentials.getUserName());
        String modifiedCredentialPassword = editedCredentials.getPassword();
        Assertions.assertNotEquals(PASSWORD+ 1, modifiedCredentialPassword);
        homePage.deleteCredential();
        homePage.logout();
    }

    private void updateCredentialsEntry() {
        homePage.editCredential();
        homePage.applyCredentials(URL + 1, USERNAME+ 1, PASSWORD+ 1);
        homePage.saveCredentialChanges();
    }


    // Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
    @Test
    public void deleteCredentialsTest() {
        for (int i = 0; i < 5; i++) {
            createCredential(URL + i, USERNAME+ i, PASSWORD+ i, homePage);
        }
        while (true) {
            try {
                homePage.goToTab(driver, 2);
                homePage.deleteCredential();
            } catch (Exception e) {
                break;
            }
        }
        Assertions.assertTrue(homePage.noCredentials(driver));
    }


    //Write a test that edits an existing note and verifies that the changes are displayed.
    private void createCredential(String url, String username, String password, HomePage homePage) {
        homePage.addNewCredential();
        homePage.applyCredentials(url, username, password);
        homePage.saveCredentialChanges();
        homePage.goToTab(driver, 2);
    }

    public boolean isCredentialDisplayedOnCredentialsPage(Credential credential, String url, String username, String password) {
        if (!(url.equals(credential.getUrl()))
                || !(username.equals(credential.getUserName()))
                || (password.equals(credential.getPassword()))) {
            return false;
        }
        return true;
    }

}
