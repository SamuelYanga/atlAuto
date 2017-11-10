package com.objectivasoftware.atl.core.util;

public enum Location {

	L1("住宅"), L2("MO 體驗中心"), L3("IEC體驗店1"), L4("IEC體驗店2");

	private String name;

	public String getName() {
		return name;
	}

	Location(String name) {
		this.name = name;
	}
	
	public static Location getLocationByName(String name) {
		for (Location location : Location.values()) {
			if (name.equals(location.getName())) {
				return location;
			}
		}
		return null;
	}

}
