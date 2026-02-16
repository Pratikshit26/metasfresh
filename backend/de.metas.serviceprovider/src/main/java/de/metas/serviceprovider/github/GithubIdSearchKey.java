/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.github;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class GithubIdSearchKey
{
	@NonNull
	String repository;

	@NonNull
	String repositoryOwner;

	@NonNull
	String issueNo;
}
