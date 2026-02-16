package de.metas.contracts.commission;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import de.metas.bpartner.BPartnerId;
import lombok.Value;

/*
 * #%L
 * de.metas.commission
     
 * #L%
 */

/** Receives money from the commission system */
@Value
public class Beneficiary
{
	public static Beneficiary ofOrNull(@Nullable final BPartnerId bPartnerId)
	{
		if (bPartnerId == null)
		{
			return null;
		}
		return Beneficiary.of(bPartnerId);
	}

	@JsonCreator
	public static Beneficiary of(@JsonProperty("bPartnerId") @NonNull final BPartnerId bPartnerId)
	{
		return new Beneficiary(bPartnerId);
	}

	public static int toRepoId(@Nullable final Beneficiary salesRep)
	{
		if (salesRep == null)
		{
			return 0;
		}
		return salesRep.getBPartnerId().getRepoId();
	}

	BPartnerId bPartnerId;

	private Beneficiary(@NonNull final BPartnerId bPartnerId)
	{
		this.bPartnerId = bPartnerId;
	}

	@JsonProperty("bPartnerId")
	public BPartnerId getBPartnerId()
	{
		return bPartnerId;
	}

}
