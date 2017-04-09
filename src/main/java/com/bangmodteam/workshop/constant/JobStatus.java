package com.bangmodteam.workshop.constant;

public enum JobStatus {
	
	OPEN("O"),
	CLOSE("C"),
	PROCESS("P"),
	UNKNOWN("U");
	
	private String value;

    private JobStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
