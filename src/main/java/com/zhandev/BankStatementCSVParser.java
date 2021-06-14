package com.zhandev;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class BankStatementCSVParser implements BankStatementParser {

	private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyy");
	private static final int EXPECTED_ATTRIBUTES_LENGTH = 3;
	
	@Override
	public BankTransaction parseFrom(final String line) throws Exception {
		final Notification notification = validate(line);
		
		if (!notification.hasErrors()) {
			final String[] columns = line.split(",");
			
			final LocalDate date = LocalDate.parse(columns[0], DATE_PATTERN);
			final double amount = Double.parseDouble(columns[1]);
			final String description = columns[2];
			
			return new BankTransaction(date, amount, description);
		} else {
			throw new Exception("CSV syntax error");
		}
	}
	
	@Override
	public List<BankTransaction> parseLinesFrom(final List<String> lines) throws Exception {
		final List<BankTransaction> bankTransactions = new ArrayList<>();
		
		for (final String line : lines) {
			bankTransactions.add(parseFrom(line));
		}
		
		return bankTransactions;
	}
	
	public Notification validate(final String line) {
		final String[] columns = line.split(",");
		
		final Notification notification = new Notification();
		
		if (columns.length < EXPECTED_ATTRIBUTES_LENGTH) {
			notification.addError("CSV syntax error");
		}
		
		final String date = columns[0];
		final String amount = columns[1];
		final String description = columns[2];
		
		if (description.length() > 100) {
			notification.addError("The description is too long");
		}
		
		final LocalDate parsedDate; 
		
		try {
			parsedDate = LocalDate.parse(date, DATE_PATTERN);
			if (parsedDate.isAfter(LocalDate.now())) {
				notification.addError("Date cannot be in the future");
			}
		} catch (DateTimeParseException e) {
			notification.addError("Invalid format for date");
		}
		
		final double parsedAmount;
		
		try {
			parsedAmount = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			notification.addError("Invalid format for amount");
		}
		
		return notification;
	}
}
