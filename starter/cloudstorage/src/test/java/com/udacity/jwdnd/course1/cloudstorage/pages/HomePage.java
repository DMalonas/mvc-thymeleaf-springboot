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

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePage {

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(id = "noteButtonSaveChanges")
    private WebElement noteButtonSaveChanges;

    @FindBy(id = "addNewNoteButton")
    private WebElement addNewNoteButton;
    @FindBy(id = "addNewCredentialsButton")
    private WebElement addNewCredentialsButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(css = "button[title='%s'][description='%s']")
    private WebElement editNoteButton;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "btn-logout")
    private WebElement logoutButton;

    @FindBy(id = "credentialsSaveButton")
    private WebElement credentialsSaveButton;
    @FindBy(id = "credential-password")
    private WebElement txtCredentialPassword;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "noteTitleId")
    private WebElement noteTitleId;

    @FindBy(id = "editCredentialsButton")
    private WebElement editCredentialsButton;

    @FindBy(id = "deleteNoteAnchor")
    private WebElement deleteNoteAnchor;

    @FindBy(id = "noteDescriptionId")
    private WebElement noteDescriptionId;

    @FindBy(id = "credential-url")
    private WebElement txtCredentialUrl;

    @FindBy(id = "credentialsUrlId")
    private WebElement credentialsUrlId;
    @FindBy(id = "nav-files-tab")
    private WebElement navFilesTab;
    @FindBy(id = "credential-username")
    private WebElement txtCredentialUsername;

    @FindBy(id = "credentialsUserNameId")
    private WebElement credentialsUserNameId;


    @FindBy(id = "deleteCredentialsId")
    private WebElement deleteCredentialsId;


    @FindBy(id = "credentialsPasswordId")
    private WebElement credentialsPasswordId;




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


    public void deleteNote(WebDriver driver) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement deleteNoteAnchor = wait.until(ExpectedConditions.elementToBeClickable(By.id("deleteNoteAnchor")));
        deleteNoteAnchor.click();
    }
    public void addCredentials() {
        js.executeScript("arguments[0].click();", addNewCredentialsButton);
    }

    public void editCredentials() {
        js.executeScript("arguments[0].click();", editCredentialsButton);
    }
    
    public void deleteCredentials() {
        js.executeScript("arguments[0].click();", deleteCredentialsId);
    }


    public void createNote() {
        wait.until(ExpectedConditions.elementToBeClickable(addNewNoteButton));
        addNewNoteButton.click();
    }
    public void modifyNote(WebDriver driver, String noteTitle, String noteDescription, String newNoteTitle, String newNoteDescription) {
        String cssSelector = String.format("button[title='%s'][description='%s']", noteTitle, noteDescription);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS); // 10 second timeout
        WebElement editNoteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
        editNoteButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(this.noteTitle)).clear();
        this.noteTitle.sendKeys(newNoteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(this.noteDescription)).clear();
        this.noteDescription.sendKeys(newNoteDescription);
        saveNoteChanges(driver);
    }

    public void applyCredentials(String url, String username, String password) {
        js.executeScript("arguments[0].value='" + url + "';", txtCredentialUrl);
        js.executeScript("arguments[0].value='" + username + "';", txtCredentialUsername);
        js.executeScript("arguments[0].value='" + password + "';", txtCredentialPassword);
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



    public void saveCredentials() {
        js.executeScript("arguments[0].click();", credentialsSaveButton);
    }

    public boolean areElementsAbsent(WebDriver driver, String elementType, By... locatorKeys) {
        String xpath = "//table[@id='%s']//td[contains(text(), '%s')]";
        for (By locatorKey : locatorKeys) {
            List<WebElement> elements = driver.findElements(locatorKey);
            for (WebElement element : elements) {
                String elementText = element.getText();
                String formattedXpath = String.format(xpath, elementType, elementText);
                By elementLocator = By.xpath(formattedXpath);
                if (driver.findElements(elementLocator).size() > 0) {
                    return false;
                }
            }
        }
        return true;
    }


    public Object popObject(String objectType) {
        if (objectType.equalsIgnoreCase("note")) {
            String title = wait.until(ExpectedConditions.elementToBeClickable(noteTitleId)).getText();
            String description = wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionId)).getText();
            return new Note(title, description);
        } else if (objectType.equalsIgnoreCase("credential")) {
            String url = wait.until(ExpectedConditions.elementToBeClickable(credentialsUrlId)).getText();
            String username = credentialsUserNameId.getText();
            String password = credentialsPasswordId.getText();
            return new Credential(url, username, password);
        } else {
            throw new IllegalArgumentException("Invalid object type: " + objectType);
        }
    }

}
