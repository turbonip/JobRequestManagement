package com.bangmodteam.workshop.constant;

public enum TicketStatus {
	
	UNKNOWN("U"),
	OPEN("O"),
	PROCESS("P"),
	FINISH("F"),
	CLOSE("C");
	
	
	private String value;
	
	private TicketStatus(String value)
	{
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
