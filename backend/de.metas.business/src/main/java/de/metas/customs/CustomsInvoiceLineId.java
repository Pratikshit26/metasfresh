package de.metas.customs;

import javax.annotation.Nullable;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */
@Value
public class CustomsInvoiceLineId implements RepoIdAware
{
	int repoId;

	@NonNull
	CustomsInvoiceId customsInvoiceId;

	public static CustomsInvoiceLineId ofRepoId(@NonNull final CustomsInvoiceId customsInvoiceId, final int customsInvoiceLineid)
	{
		return new CustomsInvoiceLineId(customsInvoiceId, customsInvoiceLineid);
	}

	public static CustomsInvoiceLineId ofRepoId(final int customsInvoiceId, final int customsInvoiceLineId)
	{
		return new CustomsInvoiceLineId(CustomsInvoiceId.ofRepoId(customsInvoiceId), customsInvoiceLineId);
	}

	public static CustomsInvoiceLineId ofRepoIdOrNull(
			@Nullable final CustomsInvoiceId customsInvoiceId,
			final int customsInvoiceLineId)
	{
		return customsInvoiceId != null && customsInvoiceLineId > 0 ? ofRepoId(customsInvoiceId, customsInvoiceLineId) : null;
	}

	private CustomsInvoiceLineId(@NonNull final CustomsInvoiceId customsInvoiceId, final int customsInvoiceLineId)
	{
		this.repoId = Check.assumeGreaterThanZero(customsInvoiceLineId, "shipmentDeclarationLineId");
		this.customsInvoiceId = customsInvoiceId;
	}
}
