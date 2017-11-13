package com.objectivasoftware.atl.action.promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.objectivasoftware.atl.core.page.PromotionPage;
import com.objectivasoftware.atl.core.vo.ProductInfo;

import cucumber.api.java.en.And;

public class PromotionAction {

	List<ProductInfo> productInfoList = null;
	
	@And("选择活动相关产品，活动类型选择两种类型产品，可获赠礼品。 productId1=\"(.*)\" num1=\"(.*)\" productId2=\"(.*)\" num2=\"(.*)\"")
	public void selectPromotionProduct(String productId1, int num1, String productId2, int num2) {
		PromotionPage promotionPage = new PromotionPage();
		Map<String, Integer> map = new HashMap<>();
		map.put(productId1, num1);
		map.put(productId2, num2);
		productInfoList = promotionPage.selectProduct(map);
	}

	@And("验证满足条件后，活动礼品出现，并且选择产品的图片和数量正确显示。")
	public void checkPromotion() {
		PromotionPage promotionPage = new PromotionPage();
		Assert.assertTrue(promotionPage.isGiftDisplayed());
		Assert.assertTrue(promotionPage.checkChooseProduct(productInfoList));
	}
}
