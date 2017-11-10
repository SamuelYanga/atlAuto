package com.objectivasoftware.atl.base.frame;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.objectivasoftware.atl.base.wait.WaitUtil;

public abstract class BasePage extends AbstractFrame {
	
	
	public static String LOADING = ".ajax-loading";
	

	public static String handle;

	public String getTitle() {
		return this.myDriver.getTitle();
	}

	public String getCurrentUrl() {
		return this.myDriver.getCurrentUrl();
	}

	/** 注入jQuery支持 */
	public void inJectJquery(JavascriptExecutor jsExecutor) {
		if (jQueryLoaded(myDriver)) {
			return;
		}

		jsExecutor.executeScript("var headID = document.getElementsByTagName(\"head\")[0];"
				+ "var newScript = document.createElement('script');" + "newScript.type = 'text/Javascript';"
				+ "newScript.src=\"http://code.jquery.com/jquery-2.1.4.min.js\";" + "headID.appendChild(newScript);");
	}

	/** 判断当前页面是否使用了jQuery */
	public Boolean jQueryLoaded(JavascriptExecutor jsExecutor) {
		Boolean loaded = true;
		try {
			loaded = (Boolean) jsExecutor.executeScript("return jQuery()! = null");
		} catch (WebDriverException e) {
			loaded = false;
		}

		return loaded;
	}

	public void windowScrollToBottom() {
		int webPageViewHeight0 = getWebPageViewHeight();
		windowScrollToTop(webPageViewHeight0);
	}

	public void windowScrollToBottoms() {
		int webPageViewHeight0 = getWebPageViewHeight();
		windowScrollToTop(webPageViewHeight0);
		int webPageViewHeight1 = getWebPageViewHeight();
		if (webPageViewHeight0 != webPageViewHeight1) {
			windowScrollToBottoms();
		} else {
			return;
		}
	}

	private int getWebPageViewHeight() {
		String script = "return document.body.clientHeight;";
		Object obj = myDriver.executeScript(script);
		int webPageViewHeight = Integer.valueOf(obj.toString());
		return webPageViewHeight;
	}

	public void scrollMoveToElement(WebElement we) {
		int y = we.getLocation().getY();
		String script = "return screen.availHeight;";
		Object obj = myDriver.executeScript(script);
		int screenAvailHeight = Integer.valueOf(obj.toString());
		int move = y - (screenAvailHeight / 3);

		if (move <= 0) {
			move = 1;
		}
		windowScrollToTop(move);
	}

	public void scrollToTop() {
		windowScrollToTop(1);
	}

	public void windowScrollToTop(int move) {
//		inJectJquery(myDriver);
		String setscroll = "window.scrollTo(0," + move + ")";
		myDriver.executeScript(setscroll);
		WaitUtil.waitOn(myDriver).waitTime(300);
	}

	public String currentHandle;

	public boolean switchToWindowByUrl(String windowUrl) {
		boolean flag = false;
		try {
			currentHandle = myDriver.getWindowHandle();
			Set<String> handles = myDriver.getWindowHandles();
			if (!handles.contains(currentHandle)) {
				currentHandle = handles.iterator().next();
				myDriver.switchTo().window(currentHandle);
			}
			for (String s : handles) {
				if (isCurrentUrl(windowUrl)) {
					flag = true;
					break;
				}

				if (s.equals(currentHandle)) {
					continue;
				} else {
					myDriver.switchTo().window(s);
					if (isCurrentUrl(windowUrl)) {
						flag = true;
						break;
					} else
						continue;
				}
			}
		} catch (NoSuchWindowException e) {
			flag = false;
		}
		WaitUtil.waitOn(myDriver).waitTime(500L);
		return flag;
	}

	private boolean isCurrentUrl(String windowUrl) {
		String currentUrl = myDriver.getCurrentUrl();
		if (currentUrl.contains("?")) {
			currentUrl = currentUrl.substring(0, currentUrl.indexOf("?"));
		}
		return currentUrl.contains(windowUrl);
	}

	public boolean closeCurrentWindow() {
		String currentUrl = myDriver.getCurrentUrl();
		currentUrl = currentUrl.substring(currentUrl.lastIndexOf("/"));
		return closeWindowByUrl(currentUrl);
	}

	public boolean closeWindowByUrl(String windowUrl) {
		if (switchToWindowByUrl(windowUrl)) {
			myDriver.close();
			Set<String> handles = myDriver.getWindowHandles();
			myDriver.switchTo().window(handles.iterator().next());
			return true;
		}
		return false;
	}

	public void switchToIframe(String framId) {
		if (framId == null) {
			myDriver.switchTo().defaultContent();
		} else {
			myDriver.switchTo().frame(framId);
		}
	}

	public void refreshPage() {
		myDriver.navigate().refresh();
	}

	public int getSizeOfOneElement(List<WebElement> list) {
		if (list == null || list.size() <= 1) {
			return 0;
		}

		int start = list.size() - 2;
		int end = list.size() - 1;

		try {
			return list.get(end).getLocation().getY() - list.get(start).getLocation().getY();
		} catch (Exception e) {
			return 0;
		}
	}

	public void screenshot() {
		byte[] screenshot = myDriver.getScreenshotAs(OutputType.BYTES);
		imgList.add(screenshot);
	}

	public static List<byte[]> imgList = new ArrayList<byte[]>();

	protected boolean elementIsShown(By by) {
		WebElement element = null;
		try {
			element = myDriver.findElement(by);
		} catch (NoSuchElementException e) {
			return false;
		}
		return element.isDisplayed();
	}

	protected void mouseMoveToElement(WebElement element) {
		Actions action = new Actions(myDriver.getDelegate());
		action.moveToElement(element).perform();
	}
}
