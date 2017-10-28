package com.objectivasoftware.atl.action.homepage.header;

import static org.hamcrest.Matchers.*;

import org.junit.Assert;

import com.objectivasoftware.atl.core.component.HeaderComponent;
import com.objectivasoftware.atl.core.page.HomePage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class LoginAction {

	@Given("Open home page.")
	public void openHomePage() {
		HomePage.open();
	}

	@And("Login with ABO user. username=\"(.*)\" password=\"(.*)\"")
	public void aboLoginSuccess(String userName, String password) {
		HeaderComponent headerComponent = new HeaderComponent();
		headerComponent.login(userName, password);
		String loginUserName = headerComponent.getLoginUserName();
		Assert.assertThat(loginUserName, equalTo("夥伴您好!"));
	}
}
