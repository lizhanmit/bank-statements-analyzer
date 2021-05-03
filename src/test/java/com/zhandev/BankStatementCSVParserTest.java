package com.zhandev;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Assert;
import org.junit.Test;

public class BankStatementCSVParserTest {

	private final BankStatementParser bankStatementParser = new BankStatementCSVParser();
	
	@Test
	public void shouldParseOneCorrectLine() throws Exception {
		final String line = "30-01-2017,-50,Tesco";
		
		final BankTransaction result = bankStatementParser.parseFrom(line);
		
		final BankTransaction expected = new BankTransaction(LocalDate.of(2017, Month.JANUARY, 30), -50, "Tesco");
		
		final double tolerance = 0.0d;
		
		assertEquals(expected.getDate(), result.getDate());
		assertEquals(expected.getAmount(), result.getAmount(), tolerance);
		assertEquals(expected.getDescription(), result.getDescription());
		
	}
}
