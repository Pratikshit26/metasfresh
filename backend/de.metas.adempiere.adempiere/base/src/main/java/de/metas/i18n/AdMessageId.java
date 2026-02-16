/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.i18n;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class AdMessageId implements RepoIdAware
{
	int repoId;

	private AdMessageId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_Message_Id");
	}

	@NonNull
	@JsonCreator
	public static AdMessageId ofRepoId(final int repoId)
	{
		return new AdMessageId(repoId);
	}

	@Nullable
	public static AdMessageId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final AdMessageId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final AdMessageId id1, @Nullable final AdMessageId id2) {return Objects.equals(id1, id2);}
}
