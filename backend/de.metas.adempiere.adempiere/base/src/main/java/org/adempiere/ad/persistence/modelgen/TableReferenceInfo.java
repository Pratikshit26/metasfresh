package org.adempiere.ad.persistence.modelgen;

 


import org.adempiere.util.lang.ObjectUtils;

/**
 * AD_Reference/AD_Ref_Table related meta data.
 * 
 * @author tsa
 */
class TableReferenceInfo
{
	private final String refTableName;
	private final int refDisplayType;
	private final String entityType;
	private final boolean isKey;
	private final int keyReferenceValueId;

	public TableReferenceInfo(final String refTableName
			, final int refDisplayType
			, final String entityType
			, final boolean isKey
			, final int keyReferenceValueId)
	{
		super();
		this.refTableName = refTableName;
		this.refDisplayType = refDisplayType;
		this.entityType = entityType;
		this.isKey = isKey;
		this.keyReferenceValueId = keyReferenceValueId;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 * @return AD_Ref_Table.AD_Table_ID.TableName
	 */
	public String getRefTableName()
	{
		return refTableName;
	}

	/**
	 * 
	 * @return AD_Ref_Table.AD_Key.AD_Reference_ID
	 */
	public int getRefDisplayType()
	{
		return refDisplayType;
	}

	/**
	 * 
	 * @return AD_Ref_Table.AD_Table_ID.EntityType
	 */
	public String getEntityType()
	{
		return entityType;
	}

	/**
	 * 
	 * @return AD_Ref_Table.AD_Key.IsKey
	 */
	public boolean isKey()
	{
		return isKey;
	}

	/**
	 * 
	 * @return AD_Ref_Table.AD_Key.AD_Reference_Value_ID
	 */
	public int getKeyReferenceValueId()
	{
		return keyReferenceValueId;
	}

}
