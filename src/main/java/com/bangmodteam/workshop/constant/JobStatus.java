package com.bangmodteam.workshop.constant;

public enum JobStatus {

	UNDIFIEND("U"),
	OPEN("O"),
	CLOSE("C"),
	PROCESS("P");
	
	private String value;

    private JobStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
