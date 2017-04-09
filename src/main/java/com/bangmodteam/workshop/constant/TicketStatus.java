package com.bangmodteam.workshop.constant;

public enum TicketStatus {

	OPEN("O"),
	PROCESS("P"),
	CLOSE("C"),
	UNKNOWN("U");
	
	
	private String value;
	
	private TicketStatus(String value)
	{
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
