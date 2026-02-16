package de.metas.rest_api.utils;

import static de.metas.util.Check.isEmpty;

import java.util.Collection;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.BPartnerQuery.BPartnerQueryBuilder;
import de.metas.organization.OrgId;
import de.metas.common.rest_api.common.JsonExternalId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

@Service
public class BPartnerQueryService
{
	public BPartnerQuery createQuery(@NonNull final OrgAndBPartnerCompositeLookupKeyList queryLookupKeys)
	{
		return createBPartnerQuery(queryLookupKeys.getCompositeLookupKeys(), queryLookupKeys.getOrgId());
	}

	public BPartnerQuery createQuery(@NonNull final Collection<BPartnerCompositeLookupKey> queryLookupKeys, @NonNull final OrgId onlyOrgId)
	{
		return createBPartnerQuery(queryLookupKeys, onlyOrgId);
	}

	/** Creates a query that advises the repo to fail if no matching bpartner is found. */
	public BPartnerQuery createQueryFailIfNotExists(@NonNull final BPartnerCompositeLookupKey queryLookupKey)
	{
		return createQueryFailIfNotExists(queryLookupKey, null/* orgId */);
	}

	public BPartnerQuery createQueryFailIfNotExists(
			@NonNull final BPartnerCompositeLookupKey queryLookupKey,
			@Nullable final OrgId orgId)
	{
		final BPartnerQueryBuilder queryBuilder = BPartnerQuery.builder()
				.failIfNotExists(true);
		if (orgId != null)
		{
			queryBuilder.onlyOrgId(orgId);
		}

		addKeyToQueryBuilder(queryLookupKey, queryBuilder);

		return queryBuilder.build();
	}

	private static BPartnerQuery createBPartnerQuery(
			@NonNull final Collection<BPartnerCompositeLookupKey> bpartnerLookupKeys,
			@Nullable final OrgId onlyOrgId)
	{
		final BPartnerQueryBuilder query = BPartnerQuery.builder();
		if (onlyOrgId != null)
		{
			query.onlyOrgId(onlyOrgId)
					.onlyOrgId(OrgId.ANY);
		}

		for (final BPartnerCompositeLookupKey bpartnerLookupKey : bpartnerLookupKeys)
		{
			addKeyToQueryBuilder(bpartnerLookupKey, query);
		}

		return query.build();
	}

	private static void addKeyToQueryBuilder(final BPartnerCompositeLookupKey bpartnerLookupKey, final BPartnerQueryBuilder queryBuilder)
	{
		final JsonExternalId jsonExternalId = bpartnerLookupKey.getJsonExternalId();
		if (jsonExternalId != null)
		{
			queryBuilder.externalId(JsonConverters.fromJsonOrNull(jsonExternalId));
		}

		final String value = bpartnerLookupKey.getCode();
		if (!isEmpty(value, true))
		{
			queryBuilder.bpartnerValue(value);
		}

		final GLN gln = bpartnerLookupKey.getGln();
		if (gln != null)
		{
			queryBuilder.gln(gln);
		}

		final MetasfreshId metasfreshId = bpartnerLookupKey.getMetasfreshId();
		if (metasfreshId != null)
		{
			queryBuilder.bPartnerId(BPartnerId.ofRepoId(metasfreshId.getValue()));
		}
	}

}
