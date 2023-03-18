package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage {

    private final WebDriver driver;
    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUserName;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "buttonSignUp")
    private WebElement buttonSignUp;

    @FindBy(id = "signUpToLoginPage")
    private WebElement signUpToLoginPage;

    public SignUpPage( WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.driver = webDriver;
    }



    public void signupAndGoToLogin(String firstName, String lastName, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 10); // Wait up to 10 seconds

        wait.until(ExpectedConditions.visibilityOf(this.inputFirstName)); // Wait for input field to be visible
        this.inputFirstName.sendKeys(firstName);

        wait.until(ExpectedConditions.visibilityOf(this.inputLastName)); // Wait for input field to be visible
        this.inputLastName.sendKeys(lastName);

        wait.until(ExpectedConditions.visibilityOf(this.inputUserName)); // Wait for input field to be visible
        this.inputUserName.sendKeys(username);

        wait.until(ExpectedConditions.visibilityOf(this.inputPassword)); // Wait for input field to be visible
        this.inputPassword.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(this.buttonSignUp)); // Wait for button to be clickable
        this.buttonSignUp.click();
    }


}
