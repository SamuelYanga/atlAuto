package com.objectivasoftware.atl.action.pdp;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import com.objectivasoftware.atl.action.BaseParam;
import com.objectivasoftware.atl.core.page.ProductDetailPage;
import com.objectivasoftware.atl.core.vo.ProductInfo;

import cucumber.api.java.en.And;

public class ProductDetailAction {

	@And("调整产品数量，然后加入购物车。 productNum=\"(.*)\"")
	public void updateNumAndAddToCart(int num) {
		setProductInfo();
		ProductDetailPage productDetailPage = new ProductDetailPage();
		productDetailPage.addToCart(num);
	}

	public void setProductInfo() {
		ProductDetailPage productDetailPage = new ProductDetailPage();
		List<ProductInfo> productList = new ArrayList<ProductInfo>();
		productList.add(productDetailPage.getProductInfo());
		BaseParam.mapParam.put("ProductDetailAction", productList);
	}
}
