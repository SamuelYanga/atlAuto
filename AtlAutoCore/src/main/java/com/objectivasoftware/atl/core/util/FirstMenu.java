package com.objectivasoftware.atl.core.util;

import java.util.HashSet;
import java.util.Set;

public enum FirstMenu {

	購物專區(0), 直銷商專區(1), 營養保健(2), 美容保養(3), 個人保養(4), 家用產品(5), 其他產品(6), 開啟事業(7), 關於安麗(8);

	private static Set<FirstMenu> productCategorySet = new HashSet<>();

	static {
		productCategorySet.add(營養保健);
		productCategorySet.add(美容保養);
		productCategorySet.add(個人保養);
		productCategorySet.add(家用產品);
		productCategorySet.add(其他產品);
	}

	private int num;

	public int getNum() {
		return num;
	}

	FirstMenu(int num) {
		this.num = num;
	}

	public static boolean isProductCategory(FirstMenu first) {
		return productCategorySet.contains(first);
	}

}
