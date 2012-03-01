package org.craftedsw.domain;

import java.text.DecimalFormat;

public class Amount {

	private DecimalFormat decimalFormat = new DecimalFormat("#.00");	
	
	private int value;

	public Amount(int value) {
		this.value = value;
	}

	public static Amount amountOf(int value) {
		return new Amount(value);
	}
	
	public Amount plus(Amount otherAmount) {
		return amountOf(this.value + otherAmount.value);
	}
	
	public boolean isGreaterThan(Amount otherAmount) {
		return this.value > otherAmount.value;
	}
	
	public Amount absoluteValue() {
		return amountOf(Math.abs(value));
	}
	
	public String moneyRepresentation() {
		return decimalFormat.format(value);
	}
	
	public Amount negative() {
		return amountOf(-value);
	}
	
	@Override
	public boolean equals(Object obj) {
		Amount other = (Amount) obj;
		if (value != other.value)
			return false;
		return true;
	}

}
