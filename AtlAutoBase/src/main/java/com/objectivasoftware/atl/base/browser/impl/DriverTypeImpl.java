package com.objectivasoftware.atl.base.browser.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.TemporaryFilesystem;
import org.openqa.selenium.io.Zip;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;

import com.objectivasoftware.atl.base.browser.DriverType;
import com.objectivasoftware.atl.base.browser.MyDriver;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public enum DriverTypeImpl implements DriverType {
	FIREFOX {
		public DesiredCapabilities getDesiredCapabilities() {
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			try {
				File dir = TemporaryFilesystem.getDefaultTmpFS().createTempDir("webdriver", "duplicated");
				new Zip().unzip(this.getClass().getResource("/firefoxProfile.zip").openStream(), dir);
				FirefoxProfile profile = new FirefoxProfile(dir);
//				FirefoxProfile profile = new ProfilesIni().getProfile("selenium");
				profile.setPreference("geo.enabled", false);
				profile.setPreference("media.gmp-provider.enabled", false);
				profile.setPreference("media.gmp-eme-adobe.autoupdate", false);				
				profile.setPreference("media.gmp.trial-create.enabled", false);
				profile.setPreference("media.wave.enabled", false);
				profile.setPreference("media.gmp-gmpopenh264.enabled", false);
				profile.setPreference("media.gmp-widevinecdm.enabled", false);
				firefoxOptions.setProfile(profile);
			} catch (IOException e) {
			}
			return firefoxOptions.addTo(DesiredCapabilities.firefox());
		}

		public MyDriver getBrowserObject(DesiredCapabilities capabilities) {
			WebDriver webDriver = new FirefoxDriver(capabilities);
			return new MyDriver(webDriver);
		}
	},
	CHROME {
		public DesiredCapabilities getDesiredCapabilities() {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
			HashMap<String, String> chromePreferences = new HashMap<String, String>();
			chromePreferences.put("profile.password_manager_enabled", "false");
			capabilities.setCapability("chrome.prefs", chromePreferences);

			ChromeOptions op = new ChromeOptions();
			op.addArguments("--lang=zh-CN");
			capabilities.setCapability("chromeOptions", op);

			return capabilities;
		}

		public MyDriver getBrowserObject(DesiredCapabilities capabilities) {
			WebDriver webDriver = new ChromeDriver(capabilities);
			return new MyDriver(webDriver);
		}
	},
	IE {
		public DesiredCapabilities getDesiredCapabilities() {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
			capabilities.setCapability("requireWindowFocus", true);
			capabilities.setCapability("nativeEvents", false);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			return capabilities;
		}

		public MyDriver getBrowserObject(DesiredCapabilities capabilities) {
			WebDriver webDriver = new InternetExplorerDriver(capabilities);
			return new MyDriver(webDriver);
		}
	},
	EDGE {
		public DesiredCapabilities getDesiredCapabilities() {
			DesiredCapabilities capabilities = DesiredCapabilities.edge();
			return capabilities;
		}

		public MyDriver getBrowserObject(DesiredCapabilities capabilities) {
			WebDriver webDriver = new EdgeDriver(capabilities);
			return new MyDriver(webDriver);
		}
	},
	SAFARI {
		public DesiredCapabilities getDesiredCapabilities() {
			DesiredCapabilities capabilities = DesiredCapabilities.safari();
			capabilities.setCapability("safari.cleanSession", true);
			return capabilities;
		}

		public MyDriver getBrowserObject(DesiredCapabilities capabilities) {
			WebDriver webDriver = new SafariDriver(capabilities);
			return new MyDriver(webDriver);
		}
	},
	OPERA {
		public DesiredCapabilities getDesiredCapabilities() {
			DesiredCapabilities capabilities = DesiredCapabilities.operaBlink();
			return capabilities;
		}

		public MyDriver getBrowserObject(DesiredCapabilities capabilities) {
			WebDriver webDriver = new OperaDriver(capabilities);
			return new MyDriver(webDriver);
		}
	};
}
