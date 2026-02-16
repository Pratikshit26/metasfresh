package de.metas.inout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Value
public class QualityNoteId implements RepoIdAware
{
	@JsonCreator
	public static QualityNoteId ofRepoId(final int repoId)
	{
		return new QualityNoteId(repoId);
	}

	public static QualityNoteId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new QualityNoteId(repoId) : null;
	}

	public static int toRepoId(@Nullable final QualityNoteId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private QualityNoteId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_QualityNote_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
