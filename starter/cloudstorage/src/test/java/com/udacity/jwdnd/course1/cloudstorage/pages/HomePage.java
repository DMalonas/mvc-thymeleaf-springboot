package com.udacity.jwdnd.course1.cloudstorage.pages;


import com.udacity.jwdnd.course1.cloudstorage.persistence.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.udacity.jwdnd.course1.cloudstorage.persistence.Credential;
import java.util.concurrent.TimeUnit;

public class HomePage {
    @FindBy(id = "btn-logout")
    private WebElement logoutButton;

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(id = "addNewNoteButton")
    private WebElement addNewNoteButton;

    @FindBy(id = "addNewCredentialsButton")
    private WebElement addNewCredentialsButton;
//
    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(css = "button[title='%s'][description='%s']")
    private WebElement editNoteButton;


    @FindBy(id = "note-description")
    private WebElement txtModifyNoteDescription;

    @FindBy(id = "note-description")
    private WebElement noteDescription;
//

    @FindBy(id = "nav-files-tab")
    private WebElement navFilesTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;
//
    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;


    @FindBy(id = "noteButtonSaveChanges")
    private WebElement noteButtonSaveChanges;

    @FindBy(id = "tableNoteTitle")
    private WebElement tableNoteTitle;
//
    @FindBy(id = "tableNoteDescription")
    private WebElement tableNoteDescription;






//
    @FindBy(id = "btnEditCredential")
    private WebElement btnEditCredential;
//

//
    @FindBy(id = "deleteNoteAnchor")
    private WebElement deleteNoteAnchor;
//
    @FindBy(id = "aDeleteCredential")
    private WebElement aDeleteCredential;
//
    @FindBy(id = "credential-url")
    private WebElement txtCredentialUrl;
//
    @FindBy(id = "credential-username")
    private WebElement txtCredentialUsername;

    @FindBy(id = "credential-password")
    private WebElement txtCredentialPassword;

    @FindBy(id = "btnCredentialSaveChanges")
    private WebElement btnCredentialSaveChanges;

    @FindBy(id = "tblCredentialUrl")
    private WebElement tblCredentialUrl;

    @FindBy(id = "tblCredentialUsername")
    private WebElement tblCredentialUsername;

    @FindBy(id = "tblCredentialPassword")
    private WebElement tblCredentialPassword;

    private final JavascriptExecutor js;

    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 500);
    }

    public void logout() {
        logoutButton.click();
    }

    public void editNote(WebDriver webDriver, String title, String description) {
        String cssSelector = String.format("button[title='%s'][description='%s']", title, description);
        webDriver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS); // 10 second timeout
        WebElement editNoteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
        editNoteButton.click();
    }

    public void editCredential() {
        js.executeScript("arguments[0].click();", btnEditCredential);
    }

public void deleteNote(WebDriver driver) throws Exception {
    WebDriverWait wait = new WebDriverWait(driver, 10);
    WebElement deleteNoteAnchor = wait.until(ExpectedConditions.elementToBeClickable(By.id("deleteNoteAnchor")));
    deleteNoteAnchor.click();
}


    public void deleteCredential() {
        js.executeScript("arguments[0].click();", aDeleteCredential);
    }

    public void uploadFile() {
        js.executeScript("arguments[0].click();", fileUpload);
    }

    public void createNote() {
        wait.until(ExpectedConditions.elementToBeClickable(addNewNoteButton));
        addNewNoteButton.click();
    }


    public void addNewCredential() {
        js.executeScript("arguments[0].click();", addNewCredentialsButton);
    }

    public void applyCredentials(String url, String username, String password) {
        js.executeScript("arguments[0].value='" + url + "';", txtCredentialUrl);
        js.executeScript("arguments[0].value='" + username + "';", txtCredentialUsername);
        js.executeScript("arguments[0].value='" + password + "';", txtCredentialPassword);
    }


    public void modifyNoteDescription(String newNoteDescription) {
        wait.until(ExpectedConditions.elementToBeClickable(txtModifyNoteDescription)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(txtModifyNoteDescription)).sendKeys(newNoteDescription);
    }

    public void modifyNote(String newNoteTitle, String newNoteDescription) {
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).clear();
        noteTitle.sendKeys(newNoteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).clear();
        noteDescription.sendKeys(newNoteDescription);
    }



    public void goToTab(WebDriver driver, int tab) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement tabToGoTo = null;
        if (tab == 0) {
            tabToGoTo = wait.until(ExpectedConditions.elementToBeClickable(navFilesTab));
        } else if (tab == 1) {
            tabToGoTo = wait.until(ExpectedConditions.elementToBeClickable(navNotesTab));
        }  else if (tab == 2) {
            tabToGoTo = wait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab));
        }
        tabToGoTo.click();
    }



    public void setNoteTitle(String title, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement noteTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        noteTitle.sendKeys(title);
    }

    public void setNoteDescription(String description, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement noteDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        noteDescription.sendKeys(description);
    }

    public void saveNoteChanges(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(noteButtonSaveChanges));
        saveButton.click();
    }


//
    public void saveCredentialChanges() {
        js.executeScript("arguments[0].click();", btnCredentialSaveChanges);
    }

    public boolean noNotes(WebDriver driver) {
        return !isElementPresent(By.id("tableNoteTitle"), driver) && !isElementPresent(By.id("tableNoteDescription"), driver);
    }

    public boolean noCredentials(WebDriver driver) {
        return !isElementPresent(By.id("tblCredentialUrl"), driver) &&
                !isElementPresent(By.id("tblCredentialUsername"), driver) &&
                !isElementPresent(By.id("tblCredentialPassword"), driver);
    }

    public boolean isElementPresent(By locatorKey, WebDriver driver) {
        try {
            driver.findElement(locatorKey);

            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public Note getFirstNote() {
        String title = wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
        String description = wait.until(ExpectedConditions.elementToBeClickable(tableNoteDescription)).getText();
        return new Note(title, description);
    }

    public Credential getFirstCredential() {
        String url = wait.until(ExpectedConditions.elementToBeClickable(tblCredentialUrl)).getText();
        String username = tblCredentialUsername.getText();
        String password = tblCredentialPassword.getText();
        return new Credential(url, username, password);
    }

    public Object getFirstObject(String objectType) {
        if (objectType.equalsIgnoreCase("note")) {
            String title = wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
            String description = wait.until(ExpectedConditions.elementToBeClickable(tableNoteDescription)).getText();
            return new Note(title, description);
        } else if (objectType.equalsIgnoreCase("credential")) {
            String url = wait.until(ExpectedConditions.elementToBeClickable(tblCredentialUrl)).getText();
            String username = tblCredentialUsername.getText();
            String password = tblCredentialPassword.getText();
            return new Credential(url, username, password);
        } else {
            throw new IllegalArgumentException("Invalid object type: " + objectType);
        }
    }

}
