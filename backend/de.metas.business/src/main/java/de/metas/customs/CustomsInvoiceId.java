package de.metas.customs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */
@Value
public class CustomsInvoiceId implements RepoIdAware
{
	@JsonCreator
	public static CustomsInvoiceId ofRepoId(final int repoId)
	{
		return new CustomsInvoiceId(repoId);
	}

	public static CustomsInvoiceId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	int repoId;

	private CustomsInvoiceId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Customs_Invoice_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(final CustomsInvoiceId id)
	{
		return id != null ? id.getRepoId() : -1;
	}
}
