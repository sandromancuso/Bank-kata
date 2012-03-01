package org.craftedsw.domain.test;

import static org.craftedsw.domain.Amount.amountOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.craftedsw.domain.Amount;
import org.junit.Test;

public class AmountTest {
	
	@Test public void
	should_be_equal_to_another_instance_containing_same_amount() {
		Amount oneHundred = new Amount(100);
		Amount anotherOneHundred = new Amount(100);
		
		assertThat(oneHundred, is(equalTo(anotherOneHundred)));
	}

	@Test public void
	should_be_different_from_another_instance_containing_different_amount() {
		Amount ten = new Amount(10);
		Amount five = new Amount(5);
		
		assertThat(ten, is(not(equalTo(five))));
	}
	
	@Test public void
	should_statically_initialise_an_amount() {
		assertThat(new Amount(10), is(equalTo(amountOf(10))));
	}
	
	@Test public void
	should_sum_up_amounts() {
		Amount ten = amountOf(10);
		Amount five = amountOf(5);
		Amount fifteen = amountOf(15);
		
		assertThat(fifteen, is(equalTo(ten.plus(five))));
	}
	
	@Test public void
	should_indicate_when_it_is_greater_than_other_amount() {
		Amount ten = amountOf(10);
		Amount five = amountOf(5);
		
		assertThat(ten.isGreaterThan(five), is(true));
	}
	
	@Test public void
	should_indicate_when_it_is_not_greater_than_other_amount() {
		Amount ten = amountOf(10);
		Amount five = amountOf(5);
		
		assertThat(five.isGreaterThan(ten), is(false));
	}
	
	@Test public void
	should_return_the_absolute_value() {
		Amount minusFive = amountOf(-5);
		
		assertThat(amountOf(5), is(equalTo(minusFive.absoluteValue())));
	}
	
	@Test public void
	should_return_the_negative_value() {
		Amount five = amountOf(5);
		
		assertThat(amountOf(-5), is(equalTo(five.negative())));
	}
	
	@Test public void
	should_return_money_representation() {
		Amount oneThousand = amountOf(1000);
		
		assertThat("1000.00", is(equalTo(oneThousand.moneyRepresentation())));
	}
	
}
