/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.external.reference;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.model.I_S_Milestone;
import de.metas.serviceprovider.model.I_S_TimeBooking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

import static de.metas.externalreference.model.X_S_ExternalReference.TYPE_IssueID;
import static de.metas.externalreference.model.X_S_ExternalReference.TYPE_MilestoneId;
import static de.metas.externalreference.model.X_S_ExternalReference.TYPE_TimeBookingID;

@AllArgsConstructor
@Getter
public enum ExternalServiceReferenceType implements IExternalReferenceType
{
	ISSUE_ID(TYPE_IssueID, I_S_Issue.Table_Name),
	TIME_BOOKING_ID(TYPE_TimeBookingID, I_S_TimeBooking.Table_Name),
	MILESTONE_ID(TYPE_MilestoneId, I_S_Milestone.Table_Name);

	private final String code;
	private final String tableName;

	public static ExternalServiceReferenceType cast(final IExternalReferenceType externalReferenceType)
	{
		return (ExternalServiceReferenceType)externalReferenceType;
	}

	private static final ImmutableMap<String, ExternalServiceReferenceType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), ExternalServiceReferenceType::getCode);

	public static ExternalServiceReferenceType ofCode(@NonNull final String code)
	{
		final ExternalServiceReferenceType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ExternalServiceReferenceType.class + " found for code: " + code);
		}
		return type;
	}

}
