package com.vitor.minispring.test.bean;

import java.time.LocalDate;

public class Husband {
	private Wife wife;
	private String wifeName;
	private LocalDate marriageDate;

	public String queryWife() {
		return "Husband.wife";
	}

	public Wife getWife() {
		return wife;
	}

	public void setWife(Wife wife) {
		this.wife = wife;
	}

	public String getWifeName() {
		return wifeName;
	}

	public void setWifeName(String wifeName) {
		this.wifeName = wifeName;
	}

	public LocalDate getMarriageDate() {
		return marriageDate;
	}

	public void setMarriageDate(LocalDate marriageDate) {
		this.marriageDate = marriageDate;
	}

	@Override
	public String toString() {
		return "Husband{" + "wifeName='" + wifeName + '\'' + ", marriageDate=" + marriageDate + '}';
	}
}
