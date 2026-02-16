/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.issue.importer.info;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.external.project.ExternalProjectType;
import de.metas.serviceprovider.github.link.GithubIssueLinkMatcher;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder
@ToString(exclude = "oAuthToken")
public class ImportIssuesRequest
{
	@NonNull
	String oAuthToken;

	@NonNull
	ExternalProjectReferenceId externalProjectReferenceId;

	@NonNull
	String repoId;

	@NonNull
	String repoOwner;

	@NonNull
	ExternalProjectType externalProjectType;

	@NonNull
	OrgId orgId;

	@Nullable
	ProjectId projectId;

	@Nullable
	GithubIssueLinkMatcher githubIssueLinkMatcher;

	@Nullable
	LocalDate dateFrom;

	@Nullable
	ImmutableList<String> issueNoList;

	public boolean importByIds()
	{
		return !Check.isEmpty(issueNoList);
	}

}
