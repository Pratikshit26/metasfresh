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

@Value
@Builder
@ToString(exclude = "oAuthToken")
public class FetchIssueByIdRequest
{
	@NonNull
	String repositoryId;

	@NonNull
	String repositoryOwner;

	@NonNull
	String issueNumber;

	@NonNull
	String oAuthToken;
}
