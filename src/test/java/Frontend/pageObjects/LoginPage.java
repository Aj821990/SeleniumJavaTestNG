package com.hellofresh.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hellofresh.utilities.GlobalUtil;

public class LoginPage {

	WebDriver ldriver;
	GlobalUtil globalutil = GlobalUtil.getInstance();

	public LoginPage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	@FindBy(xpath = "//a[@class='login']")
	WebElement userSignIn;

	@FindBy(xpath = "//input[@id='email']")
	WebElement txtEmail;

	@FindBy(xpath = "//input[@id='passwd']")
	WebElement txtPassword;

	@FindBy(xpath = "//p[@class='submit']//span[1]")
	WebElement signIn;

	public void clickSignInHeader() {
		userSignIn.click();
	}

	public void enterEmail(String loginEmail) {
		txtEmail.sendKeys(loginEmail);
	}

	public void enterPassword(String pwd) {
		txtPassword.sendKeys(pwd);
	}

	public void signIn() {
		signIn.click();
	}

}
