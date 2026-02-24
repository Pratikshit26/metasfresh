package org.compiere.util;

 


import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;

@Ignore
public class MockedEvaluatee implements Evaluatee
{
	private final Map<String, String> map = new HashMap<String, String>();

	@Override
	public String get_ValueAsString(String variableName)
	{
		return map.get(variableName);
	}

	public void put(String variableName, String value)
	{
		map.put(variableName, value);
	}
}
