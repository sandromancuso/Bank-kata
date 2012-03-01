package org.craftedsw.matchers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class DayMonthYearMatcher extends BaseMatcher<Date> {
	
	private int day;
	private int month;
	private int year;
	
	public static DayMonthYearMatcher hasDayMonthYear(int day, int month, int year) {
		return new DayMonthYearMatcher(day, month, year);
	}

	public DayMonthYearMatcher(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	@Override
	public boolean matches(Object dateObject) {
		return expectedDate().equals((Date) dateObject);
	}

	@Override
	public void describeTo(Description description) {
		description.appendValue(expectedDate());
	}

	private Date expectedDate() {
		Calendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTime();
	}
	
}