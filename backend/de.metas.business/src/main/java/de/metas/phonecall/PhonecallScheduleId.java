package de.metas.phonecall;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class PhonecallScheduleId implements RepoIdAware
{
	@JsonCreator
	public static PhonecallScheduleId ofRepoId(final int repoId)
	{
		return new PhonecallScheduleId(repoId);
	}

	public static PhonecallScheduleId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	int repoId;

	private PhonecallScheduleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "PhonecallScheduleId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
