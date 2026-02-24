package org.compiere.swing;

 


import java.util.List;

import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * {@link CTable}'s view level row sorter.
 * 
 * @author tsa
 *
 */
class CTableViewRowSorter extends TableRowSorter<TableModel>
{
	private CTableModelRowSorter modelRowSorter = null;

	public CTableViewRowSorter()
	{
		super();
		setMaxSortKeys(1);
	}

	public CTableViewRowSorter setModelRowSorter(final CTableModelRowSorter modelRowSorter)
	{
		this.modelRowSorter = modelRowSorter;
		return this;
	}

	@Override
	public void toggleSortOrder(final int modelColumnIndex)
	{
		super.toggleSortOrder(modelColumnIndex);

		// Update our internal map, because that's the place where renderer is checking
		if (modelRowSorter != null)
		{
			modelRowSorter.setSortKeys(getSortKeys());
		}
	}

	public final SortOrder getSortOrder(final int modelColumnIndex)
	{
		final List<? extends SortKey> sortKeys = getSortKeys();
		for (final SortKey sortKey : sortKeys)
		{
			if (sortKey.getColumn() == modelColumnIndex)
			{
				return sortKey.getSortOrder();
			}
		}
		return SortOrder.UNSORTED;
	}
}
