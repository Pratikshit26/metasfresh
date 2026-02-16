package de.metas.contracts.commission;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.bpartner.BPartnerId;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.commission
     
 * #L%
 */

/** The entity that buys something and therefore injects the money into the commission system that is distributed to the {@link Beneficiary Beneficiaries} */
@Value
public class Customer
{
	public static Customer ofOrNull(@Nullable final BPartnerId bPartnerId)
	{
		if (bPartnerId == null)
		{
			return null;
		}
		return of(bPartnerId);
	}

	@JsonCreator
	public static Customer of(@JsonProperty("bPartnerId") @NonNull final BPartnerId bPartnerId)
	{
		return new Customer(bPartnerId);
	}

	BPartnerId bPartnerId;

	private Customer(BPartnerId bPartnerId)
	{
		this.bPartnerId = bPartnerId;
	}

	@JsonProperty("bPartnerId")
	public BPartnerId getBPartnerId()
	{
		return bPartnerId;
	}
}
