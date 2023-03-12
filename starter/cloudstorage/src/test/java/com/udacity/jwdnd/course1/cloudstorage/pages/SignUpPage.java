package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
        this.inputFirstName.sendKeys(firstName);
        this.inputLastName.sendKeys(lastName);
        this.inputUserName.sendKeys(username);
        this.inputPassword.sendKeys(password);
        this.buttonSignUp.click();
        this.signUpToLoginPage.click();
    }

}
