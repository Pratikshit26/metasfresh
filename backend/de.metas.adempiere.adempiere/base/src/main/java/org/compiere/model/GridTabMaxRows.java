package org.compiere.model;

 


/**
 * {@link GridTab} maximum rows restriction definition.
 * 
 * To create a new instance, please use {@link #of(int)}.
 * 
 * Technically speaking, this is an implementation of flyweight design pattern.
 * 
 * @author tsa
 */
public final class GridTabMaxRows
{
	private static final int DEFAULT_MaxRows = -1000;
	public static final GridTabMaxRows DEFAULT = new GridTabMaxRows(DEFAULT_MaxRows);
	public static final GridTabMaxRows NO_RESTRICTION = new GridTabMaxRows(0);

	/**
	 * Creates a new restrictiction.
	 * 
	 * Based on given <code>maxRows</code> this method can return a new restriction or {@link #DEFAULT}, {@link #NO_RESTRICTION}.
	 * 
	 * @param maxRows
	 * @return max rows restriction.
	 */
	public static final GridTabMaxRows of(final int maxRows)
	{
		if (maxRows == DEFAULT_MaxRows)
		{
			return DEFAULT;
		}
		if (maxRows <= 0)
		{
			return NO_RESTRICTION;
		}

		return new GridTabMaxRows(maxRows);
	}

	private final int maxRows;

	private GridTabMaxRows(final int maxRows)
	{
		super();
		this.maxRows = maxRows;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder(getClass().getName()).append("[");
		if (isNoRestriction())
		{
			sb.append("NO_RESTRICTION");
		}
		else if (isDefault())
		{
			sb.append("DEFAULT");
		}
		else
		{
			sb.append(maxRows);
		}

		sb.append("]");

		return sb.toString();
	}

	/**
	 * Gets the maximum rows allowed.
	 * 
	 * The returned number makes sense only if it's not {@link #isDefault()} or {@link #isNoRestriction()}.
	 * 
	 * @return max rows allowed.
	 */
	public int getMaxRows()
	{
		return maxRows;
	}

	/**
	 * @return true if this is a "no restrictions".
	 */
	public boolean isNoRestriction()
	{
		return this == NO_RESTRICTION;
	}

	/**
	 * @return true if this restriction asks that context defaults (i.e. defined on role level, tab level etc) to be applied.
	 */
	public boolean isDefault()
	{
		return this == DEFAULT;
	}
}
