package org.adempiere.ad.validationRule.impl;

 

import java.util.HashMap;
import java.util.Map;

import org.adempiere.ad.validationRule.IValidationContext;

/**
 * Simple validation context. Can be used for testing.
 * 
 *
 */
public class PlainValidationContext implements IValidationContext
{
	private String contextTableName;
	private String tableName;
	private final Map<String, String> values = new HashMap<String, String>();

	@Override
	public String get_ValueAsString(String variableName)
	{
		return values.get(variableName);
	}

	public void setValue(String variableName, String value)
	{
		values.put(variableName, value);
	}

	public void setContextTableName(String contextTableName)
	{
		values.put(PARAMETER_ContextTableName, contextTableName);
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	@Override
	public String toString()
	{
		return String.format("PlainValidationContext [contextTableName=%s, tableName=%s, values=%s]", contextTableName, tableName, values);
	}

}
