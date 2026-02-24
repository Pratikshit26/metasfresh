package org.adempiere.ad.wrapper;

 


import java.lang.reflect.Method;
import java.util.Set;

import org.adempiere.ad.persistence.IModelInternalAccessor;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.google.common.collect.ImmutableSet;

/**
 * Adapts {@link GridTabWrapper} to {@link IModelInternalAccessor}.
 * 
 * @author tsa
 *
 */
public class GridTabModelInternalAccessor implements IModelInternalAccessor
{
	private final GridTabWrapper gridTabWrapper;

	public GridTabModelInternalAccessor(final GridTabWrapper gridTabWrapper)
	{
		super();

		this.gridTabWrapper = gridTabWrapper;
	}

	private final GridTab getGridTab()
	{
		return gridTabWrapper.getGridTab();
	}

	private final GridField getGridField(final String columnName)
	{
		return getGridTab().getField(columnName);
	}

	@Override
	public Set<String> getColumnNames()
	{
		final ImmutableSet.Builder<String> columnNames = ImmutableSet.builder();
		for (final GridField gridField : getGridTab().getFields())
		{
			columnNames.add(gridField.getColumnName());
		}
		return columnNames.build();
	}

	@Override
	public int getColumnIndex(final String columnName)
	{
		throw new UnsupportedOperationException("GridTabWrapper has no supported for column indexes");
	}

	@Override
	public boolean isVirtualColumn(final String columnName)
	{
		final GridField field = getGridField(columnName);
		return field != null && field.isVirtualColumn();
	}

	@Override
	public boolean isKeyColumnName(final String columnName)
	{
		final GridField field = getGridField(columnName);
		return field != null && field.isKey();
	}

	@Override
	public boolean isCalculated(final String columnName)
	{
		final GridField field = getGridField(columnName);
		return field != null && field.getVO().isCalculated();
	}

	@Override
	public boolean hasColumnName(final String columnName)
	{
		return gridTabWrapper.hasColumnName(columnName);
	}

	@Override
	public Object getValue(final String columnName, final int columnIndex, final Class<?> returnType)
	{
		return gridTabWrapper.getValue(columnName, returnType);
	}

	@Override
	public Object getValue(final String columnName, final Class<?> returnType)
	{
		return gridTabWrapper.getValue(columnName, returnType);
	}

	@Override
	public boolean setValue(final String columnName, final Object value)
	{
		gridTabWrapper.setValue(columnName, value);
		return true;
	}

	@Override
	public boolean setValueNoCheck(final String columnName, final Object value)
	{
		gridTabWrapper.setValue(columnName, value);
		return true;
	}

	@Override
	public Object getReferencedObject(final String columnName, final Method interfaceMethod) throws Exception
	{
		// TODO: implement
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValueFromPO(final String idColumnName, final Class<?> parameterType, final Object value)
	{
		// TODO: implement
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean invokeEquals(final Object[] methodArgs)
	{
		// TODO: implement
		throw new UnsupportedOperationException();
	}

	@Override
	public Object invokeParent(final Method method, final Object[] methodArgs) throws Exception
	{
		// TODO: implement
		throw new UnsupportedOperationException();
	}
}
