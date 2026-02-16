/*
 * #%L
 * de.metas.issue.tracking.everhour
     
 * #L%
 */

package de.metas.issue.tracking.everhour.api.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@ToString(exclude = "apiKey")
public class GetTeamTimeRecordsRequest
{
	@NonNull
	String apiKey;

	@NonNull
	LocalDate from;

	@NonNull
	LocalDate to;

	public boolean isSingleDate()
	{
		return from.equals(to);
	}
}
