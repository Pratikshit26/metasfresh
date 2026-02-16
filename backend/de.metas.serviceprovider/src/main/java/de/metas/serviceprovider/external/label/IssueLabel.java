/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.external.label;

import de.metas.organization.OrgId;
import de.metas.serviceprovider.github.GithubImporterConstants;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.regex.Matcher;

@Builder
@Value
public class IssueLabel
{
	@NonNull
	OrgId orgId;

	@NonNull
	String value;

	public boolean matchesType(@NonNull final GithubImporterConstants.LabelType labelType)
	{
		final Matcher matcher = labelType.getPattern().matcher(value);

		return matcher.matches();
	}

	@NonNull
	public String getValueForType(@NonNull final GithubImporterConstants.LabelType labelType)
	{
		final Matcher matcher = labelType.getPattern().matcher(value);

		if (!matcher.matches())
		{
			throw new AdempiereException("Value doesn't match the label patten!")
					.appendParametersToMessage()
					.setParameter("labelType", labelType.name())
					.setParameter("value", value);
		}

		return matcher.group(labelType.getGroupName());
	}
}
