package com.bangmodteam.workshop.constant;

public enum RoleType {
	
	USER("ROLE_USER"),
	SUPER_ADMIN("ROLE_SADMIN");
	
	private String value;

    private RoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
