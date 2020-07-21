package Frontend.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

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
