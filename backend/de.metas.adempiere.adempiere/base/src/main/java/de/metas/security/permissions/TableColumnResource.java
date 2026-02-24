package de.metas.security.permissions;

 


import javax.annotation.concurrent.Immutable;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import de.metas.util.Check;

/**
 * Table Column resource.
 * 
 * @author tsa
 *
 */
@Immutable
public final class TableColumnResource implements Resource
{
	/** Any table column */
	public static final TableColumnResource ANY = new TableColumnResource();

	public static final TableColumnResource of(final int adTableId, final int adColumnId)
	{
		return new TableColumnResource(adTableId, adColumnId);
	}

	private final int AD_Table_ID;
	private final int AD_Column_ID;
	private int hashcode = 0;

	private TableColumnResource(final int AD_Table_ID, final int AD_Column_ID)
	{
		super();

		Check.assume(AD_Table_ID > 0, "AD_Table_ID > 0");
		this.AD_Table_ID = AD_Table_ID;

		Check.assume(AD_Column_ID > 0, "AD_Column_ID > 0");
		this.AD_Column_ID = AD_Column_ID;
	}

	/** Any table column constructor */
	private TableColumnResource()
	{
		super();
		this.AD_Table_ID = -1;
		this.AD_Column_ID = -1;
	}

	@Override
	public String toString()
	{
		return "@AD_Column_ID@="
				+ (this == ANY ? "*" : AD_Table_ID + "/" + AD_Column_ID);
	}

	@Override
	public int hashCode()
	{
		if (hashcode == 0)
		{
			hashcode = new HashcodeBuilder()
					.append(31) // seed
					.append(AD_Table_ID)
					.append(AD_Column_ID)
					.toHashcode();
		}
		return hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final TableColumnResource other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(AD_Table_ID, other.AD_Table_ID)
				.append(AD_Column_ID, other.AD_Column_ID)
				.isEqual();
	}

	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	public int getAD_Column_ID()
	{
		return AD_Column_ID;
	}

}
