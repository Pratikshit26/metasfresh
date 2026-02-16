package de.metas.payment.paypal.client;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.paypalplus
     
 * #L%
 */

/** External PayPal Order ID (which is known by paypal.com). */
@EqualsAndHashCode
public final class PayPalOrderExternalId
{
	@JsonCreator
	public static PayPalOrderExternalId ofString(@NonNull final String orderId)
	{
		return new PayPalOrderExternalId(orderId);
	}

	public static PayPalOrderExternalId ofNullableString(@Nullable final String orderId)
	{
		return !Check.isEmpty(orderId, true) ? new PayPalOrderExternalId(orderId) : null;
	}

	private final String id;

	private PayPalOrderExternalId(final String id)
	{
		Check.assumeNotEmpty(id, "id is not empty");
		this.id = id;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return id;
	}

	public static String toString(@Nullable final PayPalOrderExternalId id)
	{
		return id != null ? id.getAsString() : null;
	}
}
