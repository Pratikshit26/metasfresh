package de.metas.greeting;

import de.metas.i18n.ITranslatableString;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
@Builder
public class Greeting
{
	@NonNull GreetingId id;
	@NonNull OrgId orgId;
	@NonNull String name;
	@NonNull ITranslatableString greeting;

	@Nullable String letterSalutation;

	@Nullable GreetingStandardType standardType;

	boolean active;

	public String getGreeting(@NonNull final String adLanguage)
	{
		return getGreeting().translate(adLanguage);
	}
}
