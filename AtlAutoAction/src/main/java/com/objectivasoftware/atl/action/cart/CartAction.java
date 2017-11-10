package com.objectivasoftware.atl.action.cart;

import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.objectivasoftware.atl.action.BaseParam;
import com.objectivasoftware.atl.action.pdp.ProductDetailAction;
import com.objectivasoftware.atl.core.page.CartPage;
import com.objectivasoftware.atl.core.vo.ProductInfo;
import com.objectivasoftware.atl.core.vo.ProductTotalInfo;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class CartAction {

	List<ProductInfo> cartList = null;
	ProductTotalInfo productTotalInfo = null;

	@And("验证购物车页面的产品信息。")
	public void checkProductInfo() {
		CartPage cartPage = new CartPage();
		if (cartList == null) {
			cartList = cartPage.getProductInfo();
		}

		@SuppressWarnings("unchecked")
		List<ProductInfo> productList = (List<ProductInfo>)BaseParam.mapParam.get("ProductDetailAction");

		for (ProductInfo cart : cartList) {
			for (ProductInfo productDetailInfo : productList) {
				if (cart.getCode().equals(productDetailInfo.getCode())) {
					Assert.assertThat(productDetailInfo.getTitle(), equalTo(cart.getTitle()));
					if (productDetailInfo.getBv() != null) {
						int pdpBv = Integer.valueOf(productDetailInfo.getBv());
						int cartBv = Integer.valueOf(cart.getBv());
						int cartNum = Integer.valueOf(cart.getNum());
						Assert.assertThat(pdpBv, comparesEqualTo(cartBv/cartNum));
					}
					if (productDetailInfo.getPv() != null) {
						BigDecimal pdpPv = new BigDecimal(productDetailInfo.getPv());
						BigDecimal cartPv = new BigDecimal(cart.getPv());
						BigDecimal cartNum = new BigDecimal(cart.getNum());
						Assert.assertThat(pdpPv, comparesEqualTo(cartPv.divide(cartNum)));
					}
					
					String pdpPrice = productDetailInfo.getPrice().replace("$", "").replace(",", "");
					String cartPrice = cart.getPrice().replace("$", "").replace(",", "");
					
					Assert.assertThat(pdpPrice, equalTo(cartPrice));
				}
			}
		}
	}
	
	@And("验证购物车页面的合计信息。")
	public void checkTotalInfo() {
		CartPage cartPage = new CartPage();

		if (cartList == null) {
			cartList = cartPage.getProductInfo();
		}

		if (productTotalInfo == null) {
			productTotalInfo = cartPage.getTotalInfo();
		}

		int totalBv = 0;
		BigDecimal totalPv = new BigDecimal(0);
		BigDecimal totalPrice = new BigDecimal(0);
		for (ProductInfo cart : cartList) {
			totalBv += Integer.parseInt(cart.getBv());
			totalPv = new BigDecimal(cart.getPv()).add(totalPv);
			totalPrice = new BigDecimal(cart.getTotalPrice().replace("$", "").replace(",", "")).add(totalPrice);
		}

		Assert.assertThat(totalBv, comparesEqualTo(Integer.parseInt(productTotalInfo.getBv())));
		Assert.assertThat(totalPv, comparesEqualTo(new BigDecimal(productTotalInfo.getPv())));
		Assert.assertThat(totalPrice,
				comparesEqualTo(new BigDecimal(productTotalInfo.getAmount().replace("$", "").replace(",", ""))));
	}
	
	@And("点击结账按钮，进入结算页面。")
	public void checkout() {
		CartPage cartPage = new CartPage();
		cartPage.checkout();
	}
	
	@And("点击整批删除。")
	public void removeAll() {
		CartPage cartPage = new CartPage();
		cartPage.removeAll();
	}
	
	@Then("验证整批删除成功。")
	public void removeAllSuccess() {
		CartPage cartPage = new CartPage();
		Assert.assertTrue(cartPage.removeAllSuccess());
	}

	@And("选择部分产品，并删除。 productId1=\"(.*)\" productId2=\"(.*)\"")
	public void selectBatchDeleted(String productCode1, String productCode2) {
		CartPage cartPage = new CartPage();
		cartPage.removeBatch(productCode1, productCode2);
	}

	@Then("验证部分删除成功。 productId1=\"(.*)\" productId2=\"(.*)\"")
	public void removeBatchSuccess(String productCode1, String productCode2) {
		CartPage cartPage = new CartPage();
		Assert.assertTrue(cartPage.removeBatchSuccess(productCode1, productCode2));
	}

	@And("删除一个产品。 productId=\"(.*)\"")
	public void deleteProduct(String productCode) {
		CartPage cartPage = new CartPage();
		cartPage.removeProduct(productCode);
	}

	@Then("验证删除产品成功。 productId=\"(.*)\"")
	public void removeProductSuccess(String productCode) {
		CartPage cartPage = new CartPage();
		Assert.assertTrue(cartPage.removeProductSuccess(productCode));
	}

	@And("验证送货方式和指定送货方式是否一致。 location=\"(.*)\"")
	public void checkLocation(String newLocation) {
		CartPage cartPage = new CartPage();
		String location = cartPage.getLocation();
		Assert.assertThat(newLocation, equalTo(location));
	}
	
}
