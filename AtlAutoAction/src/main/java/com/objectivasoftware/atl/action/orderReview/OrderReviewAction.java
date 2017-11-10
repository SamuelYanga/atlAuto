package com.objectivasoftware.atl.action.orderReview;

import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;

import com.objectivasoftware.atl.action.BaseParam;
import com.objectivasoftware.atl.core.page.CartPage;
import com.objectivasoftware.atl.core.page.CheckoutPage;
import com.objectivasoftware.atl.core.page.OrderConfirmationPage;
import com.objectivasoftware.atl.core.page.OrderReviewPage;
import com.objectivasoftware.atl.core.vo.ProductInfo;
import com.objectivasoftware.atl.core.vo.ProductTotalInfo;

import cucumber.api.java.en.And;

public class OrderReviewAction {

	List<ProductInfo> orderProductList = null;

	@And("验证核对订单页面的产品信息。")
	public void checkProductInfo() {
		OrderReviewPage orderReviewPage = new OrderReviewPage();
		if (orderProductList == null) {
			orderProductList = orderReviewPage.getProductInfo();
		}

		@SuppressWarnings("unchecked")
		List<ProductInfo> productList = (List<ProductInfo>)BaseParam.mapParam.get("ProductDetailAction");

		for (ProductInfo orProduct : orderProductList) {
			for (ProductInfo productDetailInfo : productList) {
				if (orProduct.getCode().equals(productDetailInfo.getCode())) {
					Assert.assertThat(productDetailInfo.getTitle(), equalTo(orProduct.getTitle()));
					if (productDetailInfo.getBv() != null) {
						int pdpBv = Integer.valueOf(productDetailInfo.getBv());
						int orpBv = Integer.valueOf(orProduct.getBv());
						int orpNum = Integer.valueOf(orProduct.getNum());
						Assert.assertThat(pdpBv, comparesEqualTo(orpBv/orpNum));
					}
					if (productDetailInfo.getPv() != null) {
						BigDecimal pdpPv = new BigDecimal(productDetailInfo.getPv());
						BigDecimal orpPv = new BigDecimal(orProduct.getPv());
						BigDecimal orpNum = new BigDecimal(orProduct.getNum());
						Assert.assertThat(pdpPv, comparesEqualTo(orpPv.divide(orpNum)));
					}
					
					String pdpPrice = productDetailInfo.getPrice().replace("$", "").replace(",", "");
					String orpPrice = orProduct.getPrice().replace("$", "").replace(",", "");
					
					Assert.assertThat(pdpPrice, equalTo(orpPrice));
				}
			}
		}
	}

	@And("验证优惠券使用情况。 產品抵用券=\"(.*)\" e化現金代用券=\"(.*)\" 現金抵用券=\"(.*)\"")
	public void checkTicketInfo(String ticket1, String ticket2, String ticket3) {
		OrderReviewPage orderReviewPage = new OrderReviewPage();
		String ticket1_ = orderReviewPage.getTicket1Used();
		String ticket2_ = orderReviewPage.getTicket2Used();
		String ticket3_ = orderReviewPage.getTicket3Used();

		Assert.assertThat(ticket1, equalTo(ticket1_));
		Assert.assertThat(ticket2, equalTo(ticket2_));
		Assert.assertThat(ticket3, equalTo(ticket3_));
	}

	@And("点击送出订单，然后生成订单。")
	public void useTicket1() {
		OrderReviewPage orderReviewPage = new OrderReviewPage();
		orderReviewPage.createOrder();

		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage();
		String orderId = orderConfirmationPage.getOrderId();
		Assert.assertNotNull(orderId);
	}
}
