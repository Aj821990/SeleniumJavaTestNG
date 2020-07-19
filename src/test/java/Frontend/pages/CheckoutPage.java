package Frontend.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {

/*
	WebDriver ldriver;
	GlobalUtil globalutil = GlobalUtil.getInstance();

	public CheckoutPage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}
*/

	@FindBy(xpath = "//a[@class='sf-with-ul'][contains(text(),'Women')]")
	WebElement womenHeader;

	@FindBy(xpath = "//li[@class='ajax_block_product col-xs-12 col-sm-6 col-md-4 first-in-line first-item-of-tablet-line first-item-of-mobile-line']//img[@class='replace-2x img-responsive']")
	WebElement selectTshirt;

	@FindBy(xpath = "//span[contains(text(),'Add to cart')]")
	WebElement addTocart;

	@FindBy(xpath = "//span[contains(text(),'Proceed to checkout')]")
	WebElement proceedCheckOut;

	@FindBy(xpath = "//a[@class='button btn btn-default standard-checkout button-medium']//span[contains(text(),'Proceed to checkout')]")
	WebElement proceedCheckOutSummary;

	@FindBy(xpath = "//button[@name='processAddress']//span[contains(text(),'Proceed to checkout')]")
	WebElement proceedCheckOutAddress;

	@FindBy(xpath = "//input[@id='cgv']")
	WebElement termsAndService;

	@FindBy(xpath = "//button[@name='processCarrier']//span[contains(text(),'Proceed to checkout')]")
	WebElement proceedCheckOutShipping;

	@FindBy(xpath = "//a[@class='bankwire']")
	WebElement paymentMethod;

	@FindBy(xpath = "//span[contains(text(),'I confirm my order')]")
	WebElement confirmOrder;

	public void clickWomenHeader() {
		womenHeader.click();
	}

	public void selectTShirt() {
		selectTshirt.click();
	}

	public void addCart() {
		addTocart.click();
	}

	public void proccedCheckout() {
		proceedCheckOut.click();
	}

	public void summaryCheckOut() {
		proceedCheckOutSummary.click();
	}

	public void addressCheckout() {
		proceedCheckOutAddress.click();
	}

	public void clickTnS() {
		termsAndService.click();
	}

	public void shippingCheckout() {
		proceedCheckOutShipping.click();
	}

	public void paymentMethod() {
		paymentMethod.click();
	}

	public void confirmOrder() {
		confirmOrder.click();
	}

}
