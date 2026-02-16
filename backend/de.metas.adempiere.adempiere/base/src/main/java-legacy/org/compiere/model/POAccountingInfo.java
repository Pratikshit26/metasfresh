package org.compiere.model;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
final class POAccountingInfo
{
	String acctTableName;
	ImmutableSet<String> acctColumnNames;

	@Builder
	private POAccountingInfo(
			@NonNull final String acctTableName,
			@NonNull final ImmutableSet<String> acctColumnNames)
	{
		Check.assumeNotEmpty(acctColumnNames, "acctColumnNames is not empty");

		this.acctTableName = acctTableName;
		this.acctColumnNames = acctColumnNames;
	}
}
