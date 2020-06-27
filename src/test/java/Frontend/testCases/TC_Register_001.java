package com.hellofresh.testCases;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hellofresh.pageObjects.RegisterPage;
import com.hellofresh.utilities.GlobalUtil;
import com.hellofresh.utilities.XLUtils;

public class TC_Register_001 extends BaseClass {
	XLUtils xlread = new XLUtils();

	@Test(dataProvider = "RegisterData")
	public void registerCustomer(Map<Object, Object> regData) throws ParseException, IOException {
		RegisterPage rp = new RegisterPage(globalUtil.getDriver());

		globalUtil.implicitWait(20);
		rp.clickSignIn();
		GlobalUtil.getLogger().info("user clicked on signup button");

		globalUtil.implicitWait(20);
		String generatedEmail = rp.setEmail(generateRandomEmail());
		GlobalUtil.getLogger().info("user entered the Email");
		if (generatedEmail.equals(rp.enteredEmail())) {
			softAssert.assertTrue(true);
		} else {
			softAssert.assertTrue(false);
		}

		globalUtil.implicitWait(20);
		rp.clickCreateAccount();
		GlobalUtil.getLogger().info("user clicked on create account button");

		// mapData.get("Password").toString();

		globalUtil.implicitWait(20);
		rp.clickTitle(regData.get("Title").toString());
		GlobalUtil.getLogger().info("title is selected");

		globalUtil.implicitWait(20);
		String fName = rp.setFName(regData.get("FirstName").toString());
		GlobalUtil.getLogger().info("Firstname is entered");

		globalUtil.implicitWait(20);
		String lName = rp.setLName(regData.get("LastName").toString());
		GlobalUtil.getLogger().info("Firstname is entered");

		globalUtil.implicitWait(20);
		rp.setPassword(regData.get("Password").toString());
		GlobalUtil.getLogger().info("Password is entered");

		String dob = regData.get("DOB").toString();
		GlobalUtil.getLogger().info("date of Birth is read from excel");
		System.out.println("dob is" + dob);

		Map<String, String> mapDob = globalUtil.splitDate(dob);

		System.out.println("date is" + mapDob.get("date"));
		System.out.println(mapDob.get("month"));
		System.out.println(mapDob.get("year"));
		rp.setDobDay(mapDob.get("date"));
		GlobalUtil.getLogger().info("date is selected from dropdown");

		rp.setDobMonth(mapDob.get("month"));
		GlobalUtil.getLogger().info("month is selected from dropdown");

		rp.setDobYear(mapDob.get("year"));
		GlobalUtil.getLogger().info("year is selected from dropdown");

		globalUtil.implicitWait(20);
		rp.setAddress(regData.get("Address").toString());
		GlobalUtil.getLogger().info("Address is entered");

		globalUtil.implicitWait(20);
		rp.setCity(regData.get("City").toString());
		GlobalUtil.getLogger().info("Address is entered");

		globalUtil.implicitWait(20);
		rp.setState(regData.get("State").toString());
		GlobalUtil.getLogger().info("State is entered");

		globalUtil.implicitWait(20);
		rp.setPostCode(regData.get("PostCode").toString());
		GlobalUtil.getLogger().info("PostCode is entered");

		globalUtil.implicitWait(20);
		rp.setCountry(regData.get("Country").toString());
		GlobalUtil.getLogger().info("Country is entered");

		globalUtil.implicitWait(60);
		rp.setMobPhone(regData.get("MobileNumber").toString());
		GlobalUtil.getLogger().info("MobileNumber is entered");

		globalUtil.implicitWait(60);
		globalUtil.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		rp.clickRegister();
		GlobalUtil.getLogger().info("Register button is clicked");

		// validating the account page title
		softAssert.assertEquals(globalUtil.getDriver().getTitle(), "Login - My Store");
		GlobalUtil.getLogger().info("Title of the page is Login - My Store ");

		// valdating URL contains controller=my-account
		softAssert.assertTrue(globalUtil.getDriver().getCurrentUrl().contains("controller=my-account"));
		GlobalUtil.getLogger().info("URL contains controller=my-account");

		// validating the logged in user
		String name = globalUtil.getDriver().findElement(By.className("account")).getText();
		softAssert.assertEquals(name, fName + lName);

		softAssert.assertTrue(globalUtil.getDriver().findElement(By.className("info-account")).getText()
				.contains("Welcome to your account."));
		// validating whether logout action is available
		softAssert.assertTrue(globalUtil.getDriver().findElement(By.className("logout")).isDisplayed());

	}

	@DataProvider(name = "RegisterData")
	public Object[][] regData() throws IOException {
		return xlread.testData("Register");
	}

}
