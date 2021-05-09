package com.zhandev;

@FunctionalInterface
public interface BankTransactionFilter {

	boolean test(BankTransaction bankTransaction);
}
