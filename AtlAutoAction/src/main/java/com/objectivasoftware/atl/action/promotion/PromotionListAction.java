package com.objectivasoftware.atl.action.promotion;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import com.objectivasoftware.atl.action.BaseParam;
import com.objectivasoftware.atl.core.page.ProductDetailPage;
import com.objectivasoftware.atl.core.page.PromotionListPage;
import com.objectivasoftware.atl.core.vo.ProductInfo;

import cucumber.api.java.en.And;

public class PromotionListAction {

	@And("根据活动编号，选择一个活动，点击立即参与。 promotionCode=\"(.*)\"")
	public void joinPromotion(String promotionCode) {
		PromotionListPage promotionListPage = new PromotionListPage();
		promotionListPage.joinPromotion(promotionCode);
	}

	public void setProductInfo() {
		ProductDetailPage productDetailPage = new ProductDetailPage();
		List<ProductInfo> productList = new ArrayList<ProductInfo>();
		productList.add(productDetailPage.getProductInfo());
		BaseParam.mapParam.put("ProductDetailAction", productList);
	}
}
