package com.zhandev;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class BankStatementProcessor {

	private final List<BankTransaction> bankTransactions;
	
	public BankStatementProcessor(final List<BankTransaction> bankTransactions) {
		this.bankTransactions = bankTransactions;
	}

	public double summarizeTransactions(final BankTransactionSummarizer bankTransactionSummarizer) {
		double result = 0d;
		
		for (final BankTransaction bankTransaction : bankTransactions) {
			result = bankTransactionSummarizer.summarize(result, bankTransaction);
		}
		
		return result;
	}

	// use the below one that invokes the above method with open/close principle and lambda expression
//	public double calculateTotalInMonth(final Month month) {
//		double total = 0d;
//		
//		for (final BankTransaction bankTransaction : bankTransactions) {
//			if (bankTransaction.getDate().getMonth() == month) {
//				total += bankTransaction.getAmount();
//			}
//		}
//		
//		return total;
//	}
	
	public double calculateTotalInMonth(final Month month) {
		return summarizeTransactions((acc, bankTransaction) -> 
		bankTransaction.getDate().getMonth() == month ? acc + bankTransaction.getAmount() : acc);
	}
	
	
	public List<BankTransaction> findTransactions(final BankTransactionFilter bankTransactionFilter) {
		final List<BankTransaction> result = new ArrayList<>();
		
		for (final BankTransaction bankTransaction : bankTransactions) {
			if (bankTransactionFilter.test(bankTransaction)) {
				result.add(bankTransaction);
			}
		}
		
		return result;
	}
	
	// if this is a very common operation, 
	// it makes sense to extract it into an explicit API to make it easier for users to understand and use
	public List<BankTransaction> findTransactionsGreaterThanEqual(final double amount) {
		return findTransactions(bankTransaction -> bankTransaction.getAmount() >= amount);
	}
	

	
	
}
