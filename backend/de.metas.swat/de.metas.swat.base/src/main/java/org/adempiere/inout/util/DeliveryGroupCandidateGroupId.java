package org.adempiere.inout.util;

import org.adempiere.util.lang.impl.TableRecordReference;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@EqualsAndHashCode
public final class DeliveryGroupCandidateGroupId
{
	public static DeliveryGroupCandidateGroupId of(@NonNull final TableRecordReference recordRef)
	{
		return new DeliveryGroupCandidateGroupId(recordRef.getTableName() + "=" + recordRef.getRecord_ID());
	}

	private final String id;

	private DeliveryGroupCandidateGroupId(@NonNull final String id)
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return id;
	}
}
