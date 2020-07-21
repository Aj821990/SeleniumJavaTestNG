package Frontend.pages;

import framework.base.BasePageMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePageMethods {

	@FindBy(xpath = "//a[@class='login']")
	WebElement userSignIn;

	@FindBy(xpath = "//input[@id='email_create']")
	WebElement txtEmail;

	// By txtEmail = By.id("email_create");

	@FindBy(xpath = "//*[@id='email']")
	WebElement enteredEmail;

	@FindBy(xpath = "//form[@id='create-account_form']//span[1]")
	WebElement btnCreateAccount;

	@FindBy(xpath = "//span[contains(text(),'Register')]")
	WebElement btnRegister;

	@FindBy(xpath = "//input[@id='id_gender1']")
	WebElement titleMr;

	@FindBy(xpath = "//input[@id='id_gender2']")
	WebElement titleMrs;

	@FindBy(xpath = "//input[@id='customer_firstname']")
	WebElement txtFirstName;

	@FindBy(xpath = "//input[@id='customer_lastname']")
	WebElement txtLastName;

	@FindBy(xpath = "//input[@id='passwd']")
	WebElement txtPassword;

	@FindBy(xpath = "//select[@id='days']")
	WebElement txtDobDay;

	@FindBy(xpath = "//select[@id='months']")
	WebElement txtDobMonth;

	@FindBy(xpath = "//select[@id='years']")
	WebElement txtDobYear;

	@FindBy(xpath = "//input[@id='address1']")
	WebElement txtAddress;

	@FindBy(xpath = "//input[@id='city']")
	WebElement txtCity;

	@FindBy(xpath = "//select[@id='id_state']")
	WebElement txtState;

	@FindBy(xpath = "//input[@id='postcode']")
	WebElement txtPostCode;

	@FindBy(xpath = "//select[@id='id_country']")
	WebElement txtCountry;

	@FindBy(xpath = "//input[@id='phone_mobile']")
	WebElement txtMobbilePhone;

	public void clickSignIn()
	{
		userSignIn.click();
	}

	public String setEmail(String uEmail)
	{
		// uEmail = "abc35@gmail.com";
		// globalutil.explicitWait(txtEmail, 60);
		// ldriver.findElement(txtEmail).sendKeys(uEmail);
		txtEmail.sendKeys(uEmail);
		return uEmail;
	}

	public String enteredEmail()
	{
		return enteredEmail.getText();
	}

	public void clickCreateAccount()
	{
		btnCreateAccount.click();
	}

	public void clickRegister()
	{
		btnRegister.click();
	}

	public void clickTitle(String title)
	{
		if (title.equalsIgnoreCase(title))
		{
			titleMr.click();
		} else if (title.equalsIgnoreCase(title))
		{
			titleMrs.click();
		}
	}

	public String setFName(String uFName)
	{
		txtFirstName.sendKeys(uFName);
		return uFName;
	}

	public String setLName(String uLName)
	{
		txtLastName.sendKeys(uLName);
		return uLName;
	}

	public void setPassword(String uPwd)
	{
		txtPassword.sendKeys(uPwd);
	}

	public void setDobDay(String uDobDay)
	{
		selectByValue(txtDobDay, uDobDay);
	}

	public void setDobMonth(String uDobMonth)
	{
		selectByValue(txtDobMonth, uDobMonth);
	}

	public void setDobYear(String uDobYear)
	{
		selectByValue(txtDobYear, uDobYear);
	}

	public void setAddress(String uAddress)
	{
		txtAddress.sendKeys(uAddress);
	}

	public void setCity(String uCity)
	{
		txtCity.sendKeys(uCity);
	}

	public void setState(String uState)
	{
		selectByVisibleText(txtState, uState);
	}

	public void setPostCode(String uPostCode)
	{
		txtPostCode.sendKeys(uPostCode);
	}

	public void setCountry(String uCountry)
	{
		selectByVisibleText(txtCountry, uCountry);
	}

	public void setMobPhone(String uMobPhone)
	{
		txtMobbilePhone.sendKeys(uMobPhone);
	}
}
