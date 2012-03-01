package org.craftedsw.acceptancetests.steps;

import static org.mockito.Mockito.mock;

import java.io.PrintStream;
import java.util.Date;

import org.craftedsw.domain.Account;
import org.craftedsw.domain.Amount;
import org.craftedsw.domain.Statement;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class StatementPrintingSteps {

	private Account account;
	private PrintStream printer = mock(PrintStream.class);

	@BeforeStory
	public void beforeStoryDo() {
		account = new Account(new Statement());
	}
	
	@Given("a client makes a deposit of $value on $date")
	public void givenAClientMakesADepositOf(Amount value, Date date){
		account.deposit(value, date);
	}

	@Given("a deposit of $value on $date")
	public void givenADepositOf(Amount value, Date date){
		account.deposit(value, date);
	}

	@Given("a withdrawal of $value on $date")
	public void givenAWithdrawalOf(Amount value, Date date){
		account.withdrawal(value, date);
	}

	@When("she prints her bank statement")
	public void whenSheChecksHerBankStatement(){
		account.printStatement(printer);
	}

	@Then("she would see $statement")
	public void thenSheWouldSee(String statement){
		InOrder inOrder = Mockito.inOrder(printer);
		inOrder.verify(printer).println("date       | credit   | debit    | balance");
		inOrder.verify(printer).println("14/01/2012 |          | 500.00   | 2500.00");
		inOrder.verify(printer).println("13/01/2012 | 2000.00  |          | 3000.00");
		inOrder.verify(printer).println("10/01/2012 | 1000.00  |          | 1000.00");
	}
	
}
