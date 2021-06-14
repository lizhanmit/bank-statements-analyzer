package com.zhandev;

import java.util.List;

public interface BankStatementParser {

	BankTransaction parseFrom(String line) throws Exception;
	
	List<BankTransaction> parseLinesFrom(List<String> lines) throws Exception;
}
