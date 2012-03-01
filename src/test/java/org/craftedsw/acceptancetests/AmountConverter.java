package org.craftedsw.acceptancetests;

import java.lang.reflect.Type;

import org.craftedsw.domain.Amount;
import org.jbehave.core.steps.ParameterConverters.ParameterConverter;

public class AmountConverter implements ParameterConverter {

	@Override
	public boolean accept(Type type) {
		if (type instanceof Class<?>) {
			return Amount.class.isAssignableFrom((Class<?>) type);
		}
		return false;
	}

	@Override
	public Object convertValue(String value, Type type) {
		return Amount.amountOf(Integer.valueOf(value));
	}

}
