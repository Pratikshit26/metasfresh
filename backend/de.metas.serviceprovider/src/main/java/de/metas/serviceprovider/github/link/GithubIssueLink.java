/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.github.link;

import de.metas.serviceprovider.github.GithubIdSearchKey;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GithubIssueLink
{
	@NonNull
	GithubIdSearchKey githubIdSearchKey;

	@NonNull
	String url;
}
