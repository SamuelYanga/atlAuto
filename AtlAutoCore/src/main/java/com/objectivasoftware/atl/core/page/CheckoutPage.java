package com.objectivasoftware.atl.core.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.objectivasoftware.atl.base.frame.BasePage;
import com.objectivasoftware.atl.base.wait.WaitUtil;
import com.objectivasoftware.atl.base.wait.WaitUtil.UntilEvent;

public class CheckoutPage extends BasePage {


	/**
	 * *******************************同意上述條款*********************************
	 */
	public static final String RULE_WARNING_CSS = ".warning.closeExpand";
	public static final String AGREE_CLAUSE_CHECKBOX_CSS = ".allowClause label.atl-checkbox";
	@FindBy(css = AGREE_CLAUSE_CHECKBOX_CSS)
	private WebElement agreeClauseCheckbox;

	public void agreeClause() {
		try {
			myDriver.findElement(By.cssSelector(RULE_WARNING_CSS));
		} catch (NoSuchElementException e) {
			return;
		}

		agreeClauseCheckbox.click();
		WaitUtil.waitOn(myDriver).untilRemoved(By.cssSelector(RULE_WARNING_CSS));
	}

	/**
	 * *******************************使用產品抵用券*********************************
	 */
	public static final String CART_CHECK_ETICKET_LIST_CSS = ".cart-check-Eticket-use-child";
	@FindBy(css = CART_CHECK_ETICKET_LIST_CSS)
	private List<WebElement> cartCheckEticketList;

	public static final String TICKET1_CHECK_CSS = "label[for=checkboxEticket0] .fake-check";
	@FindBy(css = TICKET1_CHECK_CSS)
	private WebElement ticket1Check;

	public static final String TICKET1_INPUT_CSS = "inputEticket0";
	@FindBy(id = TICKET1_INPUT_CSS)
	private WebElement ticket1Input;

	public void useTicket1(String value) {
		checkTicket1();
		ticket1Input.clear();
		WaitUtil.waitOn(myDriver).waitTime(WaitUtil.WAIT_TIME_LEVEL1);
		ticket1Input.clear();
		ticket1Input.sendKeys(value);
	}

	private void checkTicket1() {
		if (isTicket1Checked()) {
			return;
		}

		ticket1Check.click();

		WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1, new UntilEvent() {
			@Override
			public boolean excute() {
				return ticket1Input.isEnabled();
			}
		}).untilEventHappened();
	}

	private boolean isTicket1Checked() {
		WebElement ticket1 = cartCheckEticketList.get(0);
		String classValue = ticket1.getAttribute("class");
		return !classValue.contains("cart-check-Eticket-use-child-unCheck");
	}

	/**
	 * *******************************使用e化現金代用券*********************************
	 */
	public static final String TICKET2_CHECK_CSS = "label[for=checkboxEticket1] .fake-check";
	@FindBy(css = TICKET2_CHECK_CSS)
	private WebElement ticket2Check;

	public static final String TICKET2_INPUT_CSS = "inputEticket1";
	@FindBy(id = TICKET2_INPUT_CSS)
	private WebElement ticket2Input;

	public void useTicket2(String value) {
		checkTicket2();
		ticket2Input.clear();
		WaitUtil.waitOn(myDriver).waitTime(WaitUtil.WAIT_TIME_LEVEL1);
		ticket2Input.clear();
		ticket2Input.sendKeys(value);
	}

	private void checkTicket2() {
		if (isTicket2Checked()) {
			return;
		}

		ticket2Check.click();

		WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1, new UntilEvent() {
			@Override
			public boolean excute() {
				return ticket2Input.isEnabled();
			}
		}).untilEventHappened();
	}

	private boolean isTicket2Checked() {
		WebElement ticket2 = cartCheckEticketList.get(1);
		String classValue = ticket2.getAttribute("class");
		return !classValue.contains("cart-check-Eticket-use-child-unCheck");
	}

	/**
	 * *******************************使用現金抵用券*********************************
	 */
	public static final String TICKET3_CHECK_CSS = "label[for=checkboxEticket2] .fake-check";
	@FindBy(css = TICKET3_CHECK_CSS)
	private WebElement ticket3Check;

	public static final String TICKET3_INPUT_CSS = "inputEticket2";
	@FindBy(id = TICKET3_INPUT_CSS)
	private WebElement ticket3Input;

	public void useTicket3(String value) {
		checkTicket3();
		ticket3Input.clear();
		WaitUtil.waitOn(myDriver).waitTime(WaitUtil.WAIT_TIME_LEVEL1);
		ticket3Input.clear();
		ticket3Input.sendKeys(value);
	}

	private void checkTicket3() {
		if (isTicket3Checked()) {
			return;
		}

		ticket3Check.click();

		WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1, new UntilEvent() {
			@Override
			public boolean excute() {
				return ticket3Input.isEnabled();
			}
		}).untilEventHappened();
	}

	private boolean isTicket3Checked() {
		WebElement ticket3 = cartCheckEticketList.get(2);
		String classValue = ticket3.getAttribute("class");
		return !classValue.contains("cart-check-Eticket-use-child-unCheck");
	}


/**
 * *****************************核对订单*****************************
 */
	public static final String LAS_3_NUM_ID = "paymentModecvc2view";
	@FindBy(id = LAS_3_NUM_ID)
	private WebElement last3NumInput;

	public static final String CHECK_ORDER_BTN_CSS = ".cart-check-button-next";
	@FindBy(css = CHECK_ORDER_BTN_CSS)
	private WebElement checkOrderBtn;
	
	
	private void inputCardLast3Num(String last3Num) {
		
		try {
			WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL1).untilShown(By.id(LAS_3_NUM_ID));
			last3NumInput.clear();
			last3NumInput.sendKeys(last3Num);
		} catch (TimeoutException e) {
			
		}
	}

	public void checkOrder(String last3Num) {
		inputCardLast3Num(last3Num);
		scrollMoveToElement(checkOrderBtn);
		WaitUtil.waitOn(myDriver).untilElementToBeClickable(checkOrderBtn);
		checkOrderBtn.click();

		try {
			WaitUtil.waitOn(myDriver, WaitUtil.WAIT_TIME_LEVEL2, new UntilEvent() {
				@Override
				public boolean excute() {
					String currentUrl = myDriver.getCurrentUrl();
					return currentUrl.contains("checkout/custom/orderReview");
				}
			}).untilEventHappened();
		} catch (TimeoutException e) {
			checkOrderBtn.click();
		}
	}
}
