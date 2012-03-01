package org.craftedsw;

import static org.craftedsw.builders.DateCreator.date;
import static org.craftedsw.domain.Amount.amountOf;

import org.craftedsw.domain.Account;
import org.craftedsw.domain.Statement;

public class StartApp {
	
	public static void main(String[] args) {
		Account account = new Account(new Statement());
		
		account.deposit(amountOf(1000), date("10/01/2012"));
		account.deposit(amountOf(2000), date("13/01/2012"));
		account.withdrawal(amountOf(500), date("14/01/2012"));
		
		account.printStatement(System.out);
	}

}
