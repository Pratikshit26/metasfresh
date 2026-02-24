package org.compiere.util;

 


import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;

@Ignore
public class MockedEvaluatee2 extends MockedEvaluatee implements Evaluatee2
{
	private final Map<String, String> mapOld = new HashMap<String, String>();

	public boolean hasVariableReturn = true;

	@Override
	public boolean has_Variable(String variableName)
	{
		return hasVariableReturn;
	}

	@Override
	public String get_ValueOldAsString(String variableName)
	{
		return mapOld.get(variableName);
	}

	public void put(String variableName, String value, String valueOld)
	{
		this.put(variableName, value);
		mapOld.put(variableName, valueOld);
	}
}
