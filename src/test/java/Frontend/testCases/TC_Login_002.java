package Frontend.testCases;

import java.io.IOException;
import java.util.Map;

import Frontend.pages.LoginPage;
import framework.base.BasePageMethods;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TC_Login_002 extends BasePageMethods {

	@Test(dataProvider = "LoginData")
	public void login(Map<Object, Object> loginData) throws IOException {

		LoginPage lp = new LoginPage(globalUtil.getDriver());

		lp.clickSignInHeader();
		GlobalUtil.getLogger().info("Clicked signIn header");

		lp.enterEmail("b@xyz.com");
		GlobalUtil.getLogger().info("Entered email");

		lp.enterPassword("12345");
		;
		GlobalUtil.getLogger().info("Entered password");

		lp.signIn();
		GlobalUtil.getLogger().info("Clicked signIn");

		// validating the account page title
		softAssert.assertEquals(globalUtil.getDriver().getTitle(), "Login - My Store");
		GlobalUtil.getLogger().info("Title of the page is Login - My Store ");

		// valdating URL contains controller=my-account
		softAssert.assertTrue(globalUtil.getDriver().getCurrentUrl().contains("controller=my-account"));
		GlobalUtil.getLogger().info("URL contains controller=my-account");

		// validating the logged in user
		/*
		 * String name =
		 * globalUtil.getDriver().findElement(By.className("account")).getText()
		 * ; softAssert.assertEquals(name, fName+lName);
		 */

		softAssert.assertTrue(globalUtil.getDriver().findElement(By.className("info-account")).getText()
				.contains("Welcome to your account."));
		// validating whether logout action is available
		softAssert.assertTrue(globalUtil.getDriver().findElement(By.className("logout")).isDisplayed());

	}

	@DataProvider(name = "LoginData")
	public Object[][] loginData() throws IOException {
		return xlread.testData("Login");

	}
}
