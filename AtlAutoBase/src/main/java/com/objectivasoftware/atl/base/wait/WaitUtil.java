package com.objectivasoftware.atl.base.wait;

/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.lift.Matchers;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.objectivasoftware.atl.base.Configurations;
import com.objectivasoftware.atl.base.Constants;
import com.objectivasoftware.atl.base.browser.DriverFactory;
import com.objectivasoftware.atl.base.browser.MyDriver;

import lombok.NonNull;

/**
 * This helper class provides a few utility methods really helpful in testing
 * asynchronous web applications like those made with AngularJS or jQuery.
 *
 * <p>
 * Many JavaScript frameworks operate by manipulating the page DOM either by
 * adding/removing or by showing/hiding elements as a consequence of a user
 * action: this implies your test code needs to synchronize with those
 * asynchronous events in order to proceed.
 * </p>
 *
 * <p>
 * The utility methods presented in here provide the basic facility to create
 * this synchronization points.
 * </p>
 *
 * <p>
 * More advanced synchronization than presented here is achievable by directly
 * using the <code>org.openqa.selenium.support.ui.Wait</code> or
 * <code>org.openqa.selenium.support.ui.FluentWait</code> APIs and the code
 * provided hereby can be used as a reference for such advanced usage.
 * </p>
 */
public class WaitUtil {

	public static final int WAIT_TIME_LEVEL0 = 500;
	public static final int WAIT_TIME_LEVEL1 = 1000;
	public static final int WAIT_TIME_LEVEL2 = 2000;
	public static final int WAIT_TIME_LEVEL3 = 3000;
	public static final int WAIT_TIME_LEVEL4 = 5000;
	public static final int WAIT_TIME_LEVEL5 = 10000;
	public static final int WAIT_TIME_LEVEL6 = 50000;
	public static final int WAIT_TIME_LEVEL7 = 90000;
	
	private static long defaultTimeout = Configurations.getIntegerConfiguration(Constants.SELENIUM_WAITTIME);
	private static long defaultInterval = Configurations.getIntegerConfiguration(Constants.SELENIUM_INTERVAL);

	private SearchContext context;
	private long timeout, interval;
	private TimeUnit timeUnit;
	private UntilEvent untilEvent;

	/**
	 * @param context
	 *            the <code>WebDriver</code> or <code>WebElement</code> used as
	 *            search root
	 * @param timeout
	 *            the maximum wait duration
	 * @param timeUnit
	 *            the maximum wait time unit
	 * @param interval
	 *            the polling interval in milliseconds
	 */
	protected WaitUtil(@NonNull SearchContext context, long timeout, @NonNull TimeUnit timeUnit, long interval) {
		this.context = context;
		this.timeout = timeout;
		this.interval = interval;
		this.timeUnit = timeUnit;
	}

	/**
	 * @param untilEvent
	 *            the waiting event
	 */
	protected WaitUtil(@NonNull SearchContext context, long timeout, @NonNull TimeUnit timeUnit, long interval,
			UntilEvent untilEvent) {
		this.context = context;
		this.timeout = timeout;
		this.interval = interval;
		this.timeUnit = timeUnit;
		this.untilEvent = untilEvent;
	}

	/**
	 * @param context
	 *            the <code>WebDriver</code> or <code>WebElement</code> used as
	 *            search root
	 * @return a <code>WaitHelper</code> instance operating on the default
	 *         parameters
	 */
	public static WaitUtil waitOn(SearchContext context) {
		return new WaitUtil(context, defaultTimeout, TimeUnit.MILLISECONDS, defaultInterval);
	}

	public static WaitUtil waitOn(SearchContext context, UntilEvent untilEvent) {
		return new WaitUtil(context, defaultTimeout, TimeUnit.MILLISECONDS, defaultInterval, untilEvent);
	}

	public static WaitUtil waitOn(SearchContext context, long timeout, UntilEvent untilEvent) {
		return new WaitUtil(context, timeout, TimeUnit.MILLISECONDS, defaultInterval, untilEvent);
	}

	/**
	 * @param context
	 *            the <code>WebDriver</code> or <code>WebElement</code> used as
	 *            search root
	 * @param timeout
	 *            the maximum wait in milliseconds
	 * @return a <code>WaitHelper</code> instance operating on the above
	 *         parameters
	 */
	public static WaitUtil waitOn(SearchContext context, long timeout) {
		return new WaitUtil(context, timeout, TimeUnit.MILLISECONDS, defaultInterval);
	}

	/**
	 * @param context
	 *            the <code>WebDriver</code> or <code>WebElement</code> used as
	 *            search root
	 * @param timeout
	 *            the maximum wait in milliseconds
	 * @param interval
	 *            the polling interval in milliseconds
	 * @return a <code>WaitHelper</code> instance operating on the above
	 *         parameters
	 */
	public static WaitUtil waitOn(SearchContext context, long timeout, long interval) {
		return new WaitUtil(context, timeout, TimeUnit.MILLISECONDS, interval);
	}

	/**
	 * @param context
	 *            the <code>WebDriver</code> or <code>WebElement</code> used as
	 *            search root
	 * @param timeout
	 *            the maximum wait duration
	 * @param timeUnit
	 *            the maximum wait time unit
	 * @return a <code>WaitHelper</code> instance operating on the above
	 *         parameters
	 */
	public static WaitUtil waitOn(SearchContext context, long timeout, @NonNull TimeUnit timeUnit) {
		return new WaitUtil(context, timeout, timeUnit, defaultInterval);
	}

	/**
	 * @param context
	 *            the <code>WebDriver</code> or <code>WebElement</code> used as
	 *            search root
	 * @param timeout
	 *            the maximum wait duration
	 * @param timeUnit
	 *            the maximum wait time unit
	 * @param interval
	 *            the polling interval in milliseconds
	 * @return a <code>WaitHelper</code> instance operating on the above
	 *         parameters
	 */
	public static WaitUtil waitOn(SearchContext context, long timeout, @NonNull TimeUnit timeUnit, long interval) {
		return new WaitUtil(context, timeout, timeUnit, interval);
	}

	/**
	 * @param millis
	 *            the maximum wait in milliseconds to be used as default value
	 */
	public static void setDefaultTimeout(long millis) {
		WaitUtil.defaultTimeout = millis;
	}

	/**
	 * @param millis
	 *            the polling interval in milliseconds to be used as default
	 *            value
	 */
	public static void setDefaultInterval(long millis) {
		WaitUtil.defaultInterval = millis;
	}

	/**
	 * Holds the execution until an element matching the search condition is
	 * <b>found</b> or the maximum wait expires.
	 *
	 * @param by
	 *            search condition
	 * @return the found element
	 */
	public WebElement untilAdded(final @NonNull By by) {
		try {
			return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
					.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
					.until(new Function<SearchContext, WebElement>() {
						public WebElement apply(SearchContext context) {
							return context.findElement(by);
						}
					});
		} catch (Exception e) {
			throw new NoSuchElementException("Can not find the element:" + by.toString(), e);
		}

	}

	public WebElement untilAdded(final @NonNull WebElement element, final @NonNull By by) {
		try {
			return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
					.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
					.until(new Function<SearchContext, WebElement>() {
						public WebElement apply(SearchContext context) {
							return element.findElement(by);
						}
					});
		} catch (Exception e) {
			throw new NoSuchElementException("Can not find the element:" + by.toString(), e);
		}

	}

	/**
	 * Holds the execution until <b>no</b> element matching the search condition
	 * is <b>found</b> or the maximum wait expires.
	 *
	 * WARNING: the return value of this method might be <code>null</code> and,
	 * even when it is not, it represents a stale node.
	 *
	 * @param by
	 *            search condition
	 * @return the last known version of the element or <code>null</code> if the
	 *         element was never found
	 */
	public Boolean untilRemoved(final @NonNull By by) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, Boolean>() {
					public Boolean apply(SearchContext context) {
						try {
							context.findElement(by);
						} catch (NoSuchElementException nsee) {
							return true;
						}
						throw new NoSuchElementException("Element found");
					}
				});
	}
	
	public Boolean untilRemoved(final @NonNull WebElement element, final @NonNull By by) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, Boolean>() {
					public Boolean apply(SearchContext context) {
						try {
							element.findElement(by);
						} catch (NoSuchElementException nsee) {
							return true;
						}
						throw new NoSuchElementException("Element found");
					}
				});
	}

	/**
	 * Holds the execution until an element matching the search condition is
	 * <b>found and visible</b> or the maximum wait expires.
	 *
	 * @param by
	 *            search condition
	 * @return the found element
	 */
	public WebElement untilShown(final @NonNull By by) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, WebElement>() {
					public WebElement apply(SearchContext context) {
						WebElement result = context.findElement(by);
						try {
							if (!result.isDisplayed()) {
								throw new NoSuchElementException("Element not visible");
							}
							return result;
						} catch (StaleElementReferenceException sere) {
							throw new NoSuchElementException("Element is currently stale");
						}
					}
				});
	}

	/**
	 * Holds the execution until an element matching the search condition is
	 * <b>found and visible</b> or the maximum wait expires.
	 *
	 * @param by
	 *            search condition
	 * @return the found element
	 */
	public Boolean untilShown(final @NonNull WebElement element, final @NonNull By by) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, Boolean>() {
					public Boolean apply(SearchContext context) {
						WebElement result = element.findElement(by);
						try {
							if (!result.isDisplayed()) {
								throw new NoSuchElementException("Element not visible");
							}
							return true;
						} catch (StaleElementReferenceException sere) {
							throw new NoSuchElementException("Element is currently stale");
						}
					}
				});
	}
	
	/**
	 * Holds the execution until an element matching the search condition is
	 * <b>found and non visible</b> or the maximum wait expires.
	 *
	 * @param by
	 *            search condition
	 * @return the found element
	 */
	public Boolean untilHidden(final @NonNull By by) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, Boolean>() {
					public Boolean apply(SearchContext context) {
						WebElement result = context.findElement(by);
						try {
							if (result.isDisplayed()) {
								throw new NoSuchElementException("Element is visible");
							}
							return true;
						} catch (StaleElementReferenceException sere) {
							throw new NoSuchElementException("Element is currently stale");
						}
					}
				});
	}
	
	public Boolean untilHidden(final @NonNull WebElement element, final @NonNull By by) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, Boolean>() {
					public Boolean apply(SearchContext context) {
						WebElement result = element.findElement(by);
						try {
							if (result.isDisplayed()) {
								throw new NoSuchElementException("Element is visible");
							}
							return true;
						} catch (StaleElementReferenceException sere) {
							throw new NoSuchElementException("Element is currently stale");
						}
					}
				});
	}

	/**
	 * Holds the execution until an element matching the search condition is
	 * <b>found and enabled</b> or the maximum wait expires.
	 *
	 * @param by
	 *            search condition
	 * @return the found element
	 */
	public WebElement untilEnabled(final @NonNull By by) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, WebElement>() {
					public WebElement apply(SearchContext context) {
						WebElement result = context.findElement(by);
						try {
							if (!result.isEnabled()) {
								throw new NoSuchElementException("Element not enabled");
							}
							return result;
						} catch (StaleElementReferenceException sere) {
							throw new NoSuchElementException("Element is currently stale");
						}
					}
				});
	}

	/**
	 * Holds the execution until an element matching the search condition is
	 * <b>found and disabled</b> or the maximum wait expires.
	 *
	 * @param by
	 *            search condition
	 * @return the found element
	 */
	public WebElement untilDisabled(final @NonNull By by) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, WebElement>() {
					public WebElement apply(SearchContext context) {
						WebElement result = context.findElement(by);
						try {
							if (result.isEnabled()) {
								throw new NoSuchElementException("Element is enabled");
							}
							return result;
						} catch (StaleElementReferenceException sere) {
							throw new NoSuchElementException("Element is currently stale");
						}
					}
				});
	}

	/**
	 * Holds the execution until an element matching the search condition is
	 * <b>found and the generic matcher is verified</b> or <b>not found</b> or
	 * the maximum wait expires.
	 *
	 * @see org.openqa.selenium.lift.Matchers
	 * @see org.hamcrest.Matcher
	 *
	 * @param by
	 *            search condition
	 * @param matcher
	 *            the matching condition to check
	 * @return the found element
	 */
	public WebElement until(final @NonNull By by, final @NonNull Matcher<WebElement> matcher) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, WebElement>() {
					public WebElement apply(SearchContext context) {
						WebElement result = context.findElement(by);
						try {
							if (!matcher.matches(result)) {
								throw new NoSuchElementException("Element does not match");
							}
							return result;
						} catch (StaleElementReferenceException sere) {
							throw new NoSuchElementException("Element is currently stale");
						}
					}
				});
	}

	/**
	 * Holds the execution until at the elements count matching the search
	 * condition <b>matches with the generic matcher condition</b> or the
	 * maximum wait expires.
	 *
	 * @see org.openqa.selenium.lift.Matchers
	 * @see org.hamcrest.Matcher
	 *
	 * @param by
	 *            search condition
	 * @param matcher
	 *            the matching condition to check
	 * @return a <code>java.util.List</code> containing found elements
	 */
	public List<WebElement> untilCount(final @NonNull By by, final @NonNull Matcher<Integer> matcher) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, List<WebElement>>() {
					public List<WebElement> apply(SearchContext context) {
						List<WebElement> result = context.findElements(by);
						try {
							if (!matcher.matches(result.size())) {
								throw new NoSuchElementException("Element does not match");
							}
							return result;
						} catch (StaleElementReferenceException sere) {
							throw new NoSuchElementException("Element is currently stale");
						}
					}
				});
	}

	/**
	 * Holds the execution until at the elements count matching the search
	 * condition <b>matches the provided count</b> or the maximum wait expires.
	 *
	 * @see org.openqa.selenium.lift.Matchers
	 * @see org.hamcrest.Matcher
	 *
	 * @param by
	 *            search condition
	 * @param count
	 *            the element count to match
	 * @return a <code>java.util.List</code> containing found elements
	 */
	public List<WebElement> untilCount(final @NonNull By by, final int count) {
		return this.untilCount(by, Matchers.exactly(count));
	}

	public List<WebElement> untilCountGe(final @NonNull By by, final int count) {
		return this.untilCount(by, Matchers.atLeast(count));
	}
	
	public Boolean untilListShown(final List<WebElement> elementList, final WebElement element, final By by) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout * 2, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, Boolean>() {
					public Boolean apply(SearchContext context) {
						List<WebElement> targets = null;
						if (elementList != null) {
							targets = elementList;
						} else {
							if (element == null) {
								targets = context.findElements(by);
							} else {
								targets = element.findElements(by);
							}
						}

						for (WebElement target : targets) {
							if (!target.isDisplayed()) {
								throw new NoSuchElementException("The element is not shown.");
							}
						}
						return true;
					}
				});
	}

	public Boolean untilListShown(final By by) {
		return untilListShown(null, by);
	}

	public Boolean untilListShown(final WebElement element, final By by) {
		return untilListShown(null, element, by);
	}
	
	public Boolean untilAjaxFinish() {
		return new FluentWait<SearchContext>(context).withTimeout(timeout * 5, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, JavascriptException.class)
				.until(new Function<SearchContext, Boolean>() {
					public Boolean apply(SearchContext context) {
						MyDriver browser = DriverFactory.getBrowser();
						if(browser.executeScript(
						        "window.onload = function(){};return jQuery.active == 0;").equals(false)) {
							throw new NoSuchElementException("Ajax is not down");
						} else {
							return true;
						}
					}
				});
	}


	/** 注入jQuery支持 */
	public void inJectJquery() {
		if (jQueryLoaded()) {
			return;
		}
		MyDriver browser = DriverFactory.getBrowser();
		browser.executeScript("var headID = document.getElementsByTagName(\"head\")[0];"
				+ "var newScript = document.createElement('script');" + "newScript.type = 'text/Javascript';"
				+ "newScript.src=\"http://code.jquery.com/jquery-2.1.4.min.js\";" + "headID.appendChild(newScript);");
	}

	/** 判断当前页面是否使用了jQuery */
	public Boolean jQueryLoaded() {
		Boolean loaded = true;
		try {
			MyDriver browser = DriverFactory.getBrowser();
			loaded = (Boolean) browser.executeScript("return jQuery()! = null");
		} catch (WebDriverException e) {
			loaded = false;
		}

		return loaded;
	}
	
	public Boolean untilPageDown() {
		inJectJquery();
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, JavascriptException.class)
				.until(new Function<SearchContext, Boolean>() {
					public Boolean apply(SearchContext context) {
						MyDriver browser = DriverFactory.getBrowser();
						if(browser.executeScript(
						        "window.onload = function(){};return document.readyState == 'complete';").equals(false)) {
							throw new NoSuchElementException("page is not down");
						} else {
							return true;
						}
						
					}
				});
	}
	
	public WebElement untilElementToBeClickable(final @NonNull By by) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
				.until(new Function<SearchContext, WebElement>() {
					public WebElement apply(SearchContext context) {
						WebElement element = context.findElement(by);
						if (element.isDisplayed() && element.isEnabled()) {
							return element;
						} else {
							throw new NoSuchElementException("Element not clickable");
						}
					}
				});
	}
	
	public Boolean untilElementToBeClickable(final @NonNull WebElement element) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
				.until(new Function<SearchContext, Boolean>() {
					public Boolean apply(SearchContext context) {
						if (element.isDisplayed() && element.isEnabled()) {
							return true;
						} else {
							throw new NoSuchElementException("Element not clickable");
						}
					}
				});
	}
	
	public Integer untilHandleCount(final @NonNull MyDriver browser, @NonNull int count) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, Integer>() {
					public Integer apply(SearchContext context) {
						int size = browser.getWindowHandles().size();
						if (size != count) {
							throw new NoSuchElementException("window hanlde count not: " + count + " but: " + size);
						}
						return size;
					}
				});
	}
	
	public Boolean untilTextToBe(final @NonNull By by, final @NonNull String text) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, Boolean>() {
					public Boolean apply(SearchContext context) {
						WebElement result = context.findElement(by);
						if (result.getText().replaceAll(" ", "").equalsIgnoreCase(text)) {
							return true;
						} else {
							throw new NoSuchElementException("text is not equal:" + text);
						}
					}
				});
	}
	
	public Boolean untilTextToBe(final @NonNull WebElement element, final @NonNull String text) {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, Boolean>() {
					public Boolean apply(SearchContext context) {
						if (element.getText().equalsIgnoreCase(text)) {
							return true;
						} else {
							throw new NoSuchElementException("text is not equal:" + text);
						}
					}
				});
	}
	
	public Boolean untilClassChanged(final WebElement element, final By by, final @NonNull String changed) {

		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, Boolean>() {
					public Boolean apply(SearchContext context) {
						WebElement target = null;
						if (element == null && by == null) {
							throw new RuntimeException("Invalid waiting.");
						} else if (element == null) {
							target = context.findElement(by);
						} else if (by == null) {
							target = element;
						} else {
							target = element.findElement(by);
						}

						String classStr = target.getAttribute("class");
						String[] classes = classStr.split(" ");
						for (String style : classes) {
							if (changed.equalsIgnoreCase(style)) {
								return true;
							}
						}
						throw new NoSuchElementException("The class of element is not changed.");
					}
				});
	}

	public void waitTime(long time) {
		long start = System.currentTimeMillis();
		while (true) {
			long end = System.currentTimeMillis();
			if (end >= (start + time)) {
				break;
			}
		}
	}

	public Boolean untilEventHappened() {
		return new FluentWait<SearchContext>(context).withTimeout(timeout, timeUnit)
				.pollingEvery(interval, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.until(new Function<SearchContext, Boolean>() {
					public Boolean apply(SearchContext context) {
						if (!untilEvent.excute()) {
							throw new NoSuchElementException("The result is not expectedly.");
						}
						return true;
					}
				});
	}

	public interface UntilEvent {
		boolean excute();
	}
}
