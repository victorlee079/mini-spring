package com.vitor.minispring.test.bean;

public class Wife {
	private Husband husband;
	private IMother mother;

	public String queryHusband() {
		return "Wife.husband, Mother.callMother: " + mother.callMother();
	}

	public Husband getHusband() {
		return husband;
	}

	public void setHusband(Husband husband) {
		this.husband = husband;
	}
}
