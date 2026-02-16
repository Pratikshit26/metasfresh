package de.metas.impexp.processing;

import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class ImportGroupResult
{
	public static final ImportGroupResult ZERO = builder().build();
	public static final ImportGroupResult ONE_INSERTED = ImportGroupResult.builder().countInserted(1).build();
	public static final ImportGroupResult ONE_UPDATED = ImportGroupResult.builder().countUpdated(1).build();

	public static ImportGroupResult countInserted(final int countInserted)
	{
		return builder().countInserted(countInserted).build();
	}

	int countInserted;
	int countUpdated;

	@Builder
	private ImportGroupResult(
			final int countInserted,
			final int countUpdated)
	{
		Check.assumeGreaterOrEqualToZero(countInserted, "countInserted");
		Check.assumeGreaterOrEqualToZero(countUpdated, "countUpdated");

		this.countInserted = countInserted;
		this.countUpdated = countUpdated;
	}

}
