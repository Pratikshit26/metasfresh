package de.metas.security.permissions;

 


import javax.annotation.concurrent.Immutable;

/**
 * Defines constraints that shall be applied when querying records in a window.
 * 
 * @author tsa
 *
 */
@Immutable
public final class WindowMaxQueryRecordsConstraint extends Constraint
{
	public static WindowMaxQueryRecordsConstraint of(final int maxQueryRecordsPerRole, final int confirmQueryRecords)
	{
		return new WindowMaxQueryRecordsConstraint(maxQueryRecordsPerRole, confirmQueryRecords);
	}

	private static final int DEFAULT_MaxQueryRecordsPerTab = 0; // i.e. infinite
	private static final int DEFAULT_ConfirmQueryRecords = 500;
	public static final WindowMaxQueryRecordsConstraint DEFAULT = new WindowMaxQueryRecordsConstraint(DEFAULT_MaxQueryRecordsPerTab, DEFAULT_ConfirmQueryRecords);

	private final int maxQueryRecordsPerRole;
	private final int confirmQueryRecords;

	private WindowMaxQueryRecordsConstraint(final int maxQueryRecordsPerRole, final int confirmQueryRecords)
	{
		this.maxQueryRecordsPerRole = Math.max(maxQueryRecordsPerRole, 0);

		// NOTE: instead of throw exception it's better to fallback to default. Else, all our roles on will fail now.
		// Before changing this, please make sure u check AD_Role.ConfirmQueryRecords.
		// Check.assume(confirmQueryRecords > 0, "confirmQueryRecords > 0 but it was {}", confirmQueryRecords);
		this.confirmQueryRecords = confirmQueryRecords <= 0 ? DEFAULT_ConfirmQueryRecords : confirmQueryRecords;
	}

	@Override
	public String toString()
	{
		// NOTE: we are making it translatable friendly because it's displayed in Preferences->Info->Role
		final int queryRecordsPerRole = getMaxQueryRecordsPerRole();
		final int confirmQueryRecords = getConfirmQueryRecords();
		return "WindowMaxQueryRecords["
				+ "@MaxQueryRecords@: " + queryRecordsPerRole
				+ ", @ConfirmQueryRecords@: " + confirmQueryRecords
				+ "]";
	}

	/** @return false, i.e. never inherit this constraint because it shall be defined by current role itself */
	@Override
	public boolean isInheritable()
	{
		return false;
	}

	/**
	 * @return maximum allowed rows to be presented to user in a window or ZERO if no restriction.
	 */
	public int getMaxQueryRecordsPerRole()
	{
		return maxQueryRecordsPerRole;
	}

	/**
	 * Gets the maximum allowed records to be presented to user, without asking him to confirm/refine the initial query.
	 * 
	 * @return maximum allowed records to be presented to user, without asking him to confirm/refine the initial query; always returns greater than zero.
	 */
	public int getConfirmQueryRecords()
	{
		return confirmQueryRecords;
	}

}
