package com.jc.callbustask.domain.enums;

public enum AccountType {
	REALTOR("공인중개사"), LESSOR("임대인"), LESSEE("임차인");

	private String typeName;
	AccountType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}
}
