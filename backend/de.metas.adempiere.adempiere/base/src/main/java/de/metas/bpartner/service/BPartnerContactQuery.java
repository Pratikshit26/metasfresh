package de.metas.bpartner.service;

import static de.metas.util.Check.assume;
import static de.metas.util.Check.isEmpty;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerId;
import de.metas.user.UserId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class BPartnerContactQuery
{
	/** If set, it overrides the other query parameters */
	UserId userId;

	/** If set, it overrides the {@code value} parameter */
	ExternalId externalId;

	String value;

	/** If set, it is ANDed to the rest */
	BPartnerId bPartnerId;

	@Builder
	private BPartnerContactQuery(
			@Nullable final UserId userId,
			@Nullable final ExternalId externalId,
			@Nullable final String value,
			@Nullable final BPartnerId bPartnerId)
	{
		this.userId = userId;
		this.externalId = externalId;
		this.value = value;
		this.bPartnerId = bPartnerId;

		assume(userId != null || externalId != null || !isEmpty(value, true),
				"At least one of the parameters 'userId, externalId and value needs to be non-null/non-empty");
	}
}
