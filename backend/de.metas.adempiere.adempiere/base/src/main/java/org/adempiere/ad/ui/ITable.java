package org.adempiere.ad.ui;

 



public interface ITable
{
    Object getValueAt(int rowIndexView, int columnIndexView);
    
	/**
	 * Same as {@link #getValueAt(int, int)} but it uses model coordinates instead of view coordinates.
	 * @param rowIndexModel
	 * @param columnIndexModel
	 * @return value
	 */
    Object getModelValueAt(int rowIndexModel, int columnIndexModel);

	void setColorProvider(ITableColorProvider colorProvider);

	ITableColorProvider getColorProvider();
}
