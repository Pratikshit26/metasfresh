package de.metas.material.planning.pporder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-material-planning
     
 * #L%
 */

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class PPRoutingActivityTemplateId implements RepoIdAware
{
	@JsonCreator
	public static PPRoutingActivityTemplateId ofRepoId(final int repoId)
	{
		return new PPRoutingActivityTemplateId(repoId);
	}

	@Nullable
	public static PPRoutingActivityTemplateId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(@Nullable final PPRoutingActivityTemplateId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private PPRoutingActivityTemplateId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_WF_Node_Template_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
