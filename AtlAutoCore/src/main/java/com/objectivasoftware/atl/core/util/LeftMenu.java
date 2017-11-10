package com.objectivasoftware.atl.core.util;

public enum LeftMenu {

	營養保健("Nutrition Health"), 美容保養("Beauty Maintenance"), 個人保養("PersonalCare"), 家用產品("Home Care"), 其他產品("Other Products");

	private String name;

	public String getName() {
		return name;
	}

	LeftMenu(String name) {
		this.name = name;
	}
}
