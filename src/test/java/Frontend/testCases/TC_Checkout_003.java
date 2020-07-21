package Frontend.testCases;

import Frontend.pages.CheckoutPage;
import framework.base.BasePageMethods;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class TC_Checkout_003 extends BasePageMethods {

	@Test(dependsOnMethods = { "login" })
	public void checkoutProduct() {
		CheckoutPage cp = new CheckoutPage(getDriver());

		globalUtil.implicitWait(20);

		cp.clickWomenHeader();
		GlobalUtil.getLogger().info("Clicked women header");

		cp.selectTShirt();
		GlobalUtil.getLogger().info("Selected T-shirt");

		cp.addCart();
		GlobalUtil.getLogger().info("Selected T-shirt");

		cp.proccedCheckout();
		GlobalUtil.getLogger().info("Checkout selected T-shirt");

		cp.summaryCheckOut();
		GlobalUtil.getLogger().info("Checkout Summary page");

		cp.addressCheckout();
		GlobalUtil.getLogger().info("Checkout address page");

		cp.clickTnS();
		GlobalUtil.getLogger().info("Checked terms and service");

		cp.shippingCheckout();
		GlobalUtil.getLogger().info("Checked shipping page");

		cp.paymentMethod();
		GlobalUtil.getLogger().info("Selected payment page");

		cp.confirmOrder();
		GlobalUtil.getLogger().info("Confirm order");

		// verify if the final page is order confirmation page
		softAssert.assertEquals("ORDER CONFIRMATION", globalUtil.getDriver().getTitle());
		//
		softAssert.assertTrue(globalUtil.getDriver()
				.findElement(By.xpath("//li[@class='step_done step_done_last four']")).isDisplayed());
		//
		softAssert.assertTrue(globalUtil.getDriver()
				.findElement(By.xpath("//li[@id='step_end' and @class='step_current last']")).isDisplayed());
		// verify the text on the confirmation page
		softAssert.assertTrue(globalUtil.getDriver().findElement(By.xpath("//*[@class='cheque-indent']/strong"))
				.getText().contains("Your order on My Store is complete."));
		// validating landing page url after successful confirmation
		softAssert.assertTrue(globalUtil.getDriver().getCurrentUrl().contains("controller=order-confirmation"));

	}
}
