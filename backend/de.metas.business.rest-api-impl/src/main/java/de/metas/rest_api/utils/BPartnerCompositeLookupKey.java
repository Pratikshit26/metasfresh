package de.metas.rest_api.utils;

import de.metas.bpartner.GLN;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.util.lang.ExternalId;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import static de.metas.util.Check.assumeNotEmpty;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

/**
 * Key used in conjunction with an orgId to do caching.
 */
@Value
public class BPartnerCompositeLookupKey
{
	public static BPartnerCompositeLookupKey ofMetasfreshId(@NonNull final MetasfreshId metasfreshId)
	{
		return new BPartnerCompositeLookupKey(metasfreshId, null, null, null);
	}

	public static <T extends RepoIdAware> BPartnerCompositeLookupKey ofMetasfreshId(@NonNull final T id)
	{
		return ofMetasfreshId(MetasfreshId.of(id));
	}

	public static BPartnerCompositeLookupKey ofJsonExternalId(@NonNull final JsonExternalId jsonExternalId)
	{
		return new BPartnerCompositeLookupKey(null, jsonExternalId, null, null);
	}

	public static BPartnerCompositeLookupKey ofExternalId(@NonNull final ExternalId externalId)
	{
		return ofJsonExternalId(JsonExternalIds.of(externalId));
	}

	public static BPartnerCompositeLookupKey ofCode(@NonNull final String code)
	{
		assumeNotEmpty(code, "Given parameter 'code' may not be empty");
		return new BPartnerCompositeLookupKey(null, null, code.trim(), null);
	}

	public static BPartnerCompositeLookupKey ofGln(@NonNull final GLN gln)
	{
		return new BPartnerCompositeLookupKey(null, null, null, gln);
	}

	public static BPartnerCompositeLookupKey ofIdentifierString(@NonNull final IdentifierString bpartnerIdentifier)
	{
		switch (bpartnerIdentifier.getType())
		{
			case EXTERNAL_ID:
				return BPartnerCompositeLookupKey.ofJsonExternalId(bpartnerIdentifier.asJsonExternalId());
			case VALUE:
				return BPartnerCompositeLookupKey.ofCode(bpartnerIdentifier.asValue());
			case GLN:
				return BPartnerCompositeLookupKey.ofGln(bpartnerIdentifier.asGLN());
			case METASFRESH_ID:
				return BPartnerCompositeLookupKey.ofMetasfreshId(bpartnerIdentifier.asMetasfreshId());
			default:
				throw new AdempiereException("Unexpected type=" + bpartnerIdentifier.getType());
		}
	}

	MetasfreshId metasfreshId;
	JsonExternalId jsonExternalId;
	String code;
	GLN gln;

	private BPartnerCompositeLookupKey(
			final MetasfreshId metasfreshId,
			final JsonExternalId jsonExternalId,
			final String code,
			final GLN gln)
	{
		this.metasfreshId = metasfreshId;
		this.jsonExternalId = jsonExternalId;
		this.code = code;
		this.gln = gln;
	}
}
