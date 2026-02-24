package org.adempiere.ad.dao.impl;

 


import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.DB;

/**
 * Set the column in target model to a constant value.
 * 
 * @author tsa
 *
 */
class ConstantQueryInsertFromColumn implements IQueryInsertFromColumn
{
	private final Object constantValue;

	public ConstantQueryInsertFromColumn(final Object constantValue)
	{
		super();
		this.constantValue = constantValue;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public String getSql(List<Object> sqlParams)
	{
		// Case: we are not collecting parameters => render parameter inside the SQL query
		if (sqlParams == null)
		{
			return DB.TO_SQL(constantValue);
		}
		
		sqlParams.add(constantValue);
		return "?";
	}

	@Override
	public boolean update(final Object toModel, String toColumnName, final Object fromModel)
	{
		InterfaceWrapperHelper.setValue(toModel, toColumnName, constantValue);
		return true;
	}

}
