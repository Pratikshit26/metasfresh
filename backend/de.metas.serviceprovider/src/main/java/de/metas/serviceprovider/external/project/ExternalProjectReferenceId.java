/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.external.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ExternalProjectReferenceId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ExternalProjectReferenceId ofRepoId(final int repoId)
	{
		return new ExternalProjectReferenceId(repoId);
	}

	public static ExternalProjectReferenceId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ExternalProjectReferenceId(repoId) : null;
	}

	private ExternalProjectReferenceId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "S_ExternalProjectReference_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
