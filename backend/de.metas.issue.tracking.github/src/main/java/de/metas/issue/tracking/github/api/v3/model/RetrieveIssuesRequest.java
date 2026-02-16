/*
 * #%L
 * de.metas.issue.tracking.github
     
 * #L%
 */

package de.metas.issue.tracking.github.api.v3.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder
@ToString(exclude = "oAuthToken")
public class RetrieveIssuesRequest
{
	@NonNull
	String repositoryId;

	@NonNull
	String repositoryOwner;

	int pageSize;

	int pageIndex;

	@NonNull
	String oAuthToken;

	@Nullable
	LocalDate dateFrom;
}
