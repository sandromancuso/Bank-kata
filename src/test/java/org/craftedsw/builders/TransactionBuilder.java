package org.craftedsw.builders;

import java.util.Date;

import org.craftedsw.domain.Amount;
import org.craftedsw.domain.Transaction;

public class TransactionBuilder {
	
	private Date date;
	private Amount value;

	public static TransactionBuilder aTransaction() {
		return new TransactionBuilder();
	}

	public TransactionBuilder with(Date date) {
		this.date = date;
		return this;
	}

	public TransactionBuilder with(Amount value) {
		this.value = value;
		return this;
	}

	public Transaction build() {
		Transaction transaction = new Transaction(value, date);
		return transaction;
	}
	
	

}
