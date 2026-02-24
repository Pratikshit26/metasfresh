package org.compiere.swing.table;

 


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Method;

import org.adempiere.util.lang.ObjectUtils;

import de.metas.util.Check;

/**
 * {@link AnnotatedTableModel}'s column meta info.
 * 
 * @author tsa
 *
 */
public class TableColumnInfo implements ITableColumnInfo
{
	private final String columnName;
	private Class<?> columnClass;
	private String displayName;

	public static final String PROPERTY_Editable = "Editable";
	private boolean editable = false;

	public static final String PROPERTY_Visible = "Visible";
	private boolean visible = true;

	private final Method readMethod;
	private Method writeMethod;
	private int seqNo = Integer.MAX_VALUE;
	private String lookupTableName = null;
	private String lookupColumnName = null;
	private String prototypeValue = null;
	private int displayType = -1;
	private boolean selectionColumn = false;

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	TableColumnInfo(final String columnName, final Class<?> columnClass, final Method readMethod)
	{
		super();

		Check.assumeNotEmpty(columnName, "columnName not empty");
		this.columnName = columnName;

		this.columnClass = columnClass;

		Check.assumeNotNull(readMethod, "readMethod not null");
		this.readMethod = readMethod;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public final void addPropertyChangeListener(final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(listener);
		pcs.addPropertyChangeListener(listener);
	}

	public final void removePropertyChangeListener(final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(listener);
	}

	@Override
	public String getColumnName()
	{
		return columnName;
	}

	@Override
	public Class<?> getColumnClass()
	{
		return columnClass;
	}

	void setColumnClass(final Class<?> columnClass)
	{
		Check.assumeNotNull(columnClass, "columnClass not null");
		this.columnClass = columnClass;
	}

	@Override
	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(final String displayName)
	{
		this.displayName = displayName;
	}

	@Override
	public boolean isEditable()
	{
		return editable;
	}

	public void setEditable(final boolean editable)
	{
		if (this.editable == editable)
		{
			return;
		}

		final boolean editableOld = this.editable;
		this.editable = editable;
		pcs.firePropertyChange(PROPERTY_Editable, editableOld, editable);
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		if (this.visible == visible)
		{
			return;
		}

		final boolean visibleOld = this.visible;
		this.visible = visible;
		pcs.firePropertyChange(PROPERTY_Visible, visibleOld, visible);
	}

	@Override
	public Method getReadMethod()
	{
		return readMethod;
	}

	@Override
	public Method getWriteMethod()
	{
		return writeMethod;
	}

	void setWriteMethod(final Method writeMethod)
	{
		this.writeMethod = writeMethod;
	}

	void setSeqNo(final int seqNo)
	{
		this.seqNo = seqNo;
	}

	@Override
	public int getSeqNo()
	{
		return seqNo;
	}

	@Override
	public String getLookupTableName()
	{
		return lookupTableName;
	}

	void setLookupTableName(final String lookupTableName)
	{
		this.lookupTableName = lookupTableName;
	}

	@Override
	public String getLookupColumnName()
	{
		return lookupColumnName;
	}

	void setLookupColumnName(final String lookupColumnName)
	{
		this.lookupColumnName = lookupColumnName;
	}

	@Override
	public String getPrototypeValue()
	{
		return prototypeValue;
	}

	void setPrototypeValue(final String prototypeValue)
	{
		this.prototypeValue = prototypeValue;
	}

	public int getDisplayType(final int defaultDisplayType)
	{
		return displayType > 0 ? displayType : defaultDisplayType;
	}

	public int getDisplayType()
	{
		return displayType;
	}

	void setDisplayType(int displayType)
	{
		this.displayType = displayType;
	}

	public boolean isSelectionColumn()
	{
		return selectionColumn;
	}

	public void setSelectionColumn(boolean selectionColumn)
	{
		this.selectionColumn = selectionColumn;
	}
}
