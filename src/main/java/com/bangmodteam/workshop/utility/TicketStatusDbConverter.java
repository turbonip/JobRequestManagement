package com.bangmodteam.workshop.utility;

import javax.persistence.AttributeConverter;

import com.bangmodteam.workshop.constant.TicketStatus;

public class TicketStatusDbConverter implements AttributeConverter<TicketStatus, String> {

	@Override
	public String convertToDatabaseColumn(TicketStatus ticketStatus) {

		return ticketStatus.getValue();

	}

	@Override
	public TicketStatus convertToEntityAttribute(String dbData) {

		TicketStatus status = TicketStatus.UNKNOWN;

		if (dbData != null && !dbData.trim().isEmpty()) {

			switch (dbData.toUpperCase()) {

			case "O":
				status = TicketStatus.OPEN;
				break;

			case "P":
				status = TicketStatus.PROCESS;
				break;

			case "C":
				status = TicketStatus.CLOSE;
				break;

			case "F":
				status = TicketStatus.FINISH;
				break;
				
			default:
				break;

			}

		}

		return status;

	}

}
