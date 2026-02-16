/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

package de.metas.mpackage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

@Value
public class PackageId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static PackageId ofRepoId(final int repoId)
	{
		return new PackageId(repoId);
	}

	public static PackageId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PackageId(repoId) : null;
	}

	public static int toRepoId(final PackageId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	private PackageId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Package_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
