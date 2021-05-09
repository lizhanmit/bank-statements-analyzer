package com.zhandev;

@FunctionalInterface
public interface BankTransactionSummarizer {

	double summarize(double accumulator, BankTransaction bankTransaction);
}
