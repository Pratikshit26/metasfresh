package de.metas.bpartner.composite;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.Optional;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Data
@JsonPropertyOrder(alphabetic = true/* we want the serialized json to be less flaky in our snapshot files */)
public class BPartnerLocationType
{
	public static final String BILL_TO = "billTo";
	public static final String BILL_TO_DEFAULT = "billToDefault";
	public static final String SHIP_TO = "shipTo";
	public static final String SHIP_TO_DEFAULT = "shipToDefault";

	@JsonInclude(Include.NON_ABSENT)
	private final Optional<Boolean> billTo;

	@JsonInclude(Include.NON_ABSENT)
	private Optional<Boolean> billToDefault;

	@JsonInclude(Include.NON_ABSENT)
	private final Optional<Boolean> shipTo;

	@JsonInclude(Include.NON_ABSENT)
	private Optional<Boolean> shipToDefault;

	@JsonInclude(Include.NON_ABSENT)
	private Optional<Boolean> visitorsAddress;

	@Builder
	public BPartnerLocationType(
			@Nullable final Boolean billTo,
			@Nullable final Boolean billToDefault,
			@Nullable final Boolean shipTo,
			@Nullable final Boolean shipToDefault,
			@Nullable final Boolean visitorsAddress)
	{
		this.billToDefault = Optional.ofNullable(billToDefault);
		if (this.billToDefault.orElse(false) && billTo == null)
		{
			this.billTo = Optional.of(true); // billToDefault always implies billTo
		}
		else
		{
			this.billTo = Optional.ofNullable(billTo);
		}

		this.shipToDefault = Optional.ofNullable(shipToDefault);
		if (this.shipToDefault.orElse(false) && shipTo == null)
		{
			this.shipTo = Optional.of(true); // shipToDefault always implies shipTo
		}
		else
		{
			this.shipTo = Optional.ofNullable(shipTo);
		}

		this.visitorsAddress = Optional.ofNullable(visitorsAddress);
	}

	/** copy constructor, see {@link #deepCopy()}. */
	private BPartnerLocationType(
			@Nullable final Optional<Boolean> billTo,
			@Nullable final Optional<Boolean> billToDefault,
			@Nullable final Optional<Boolean> shipTo,
			@Nullable final Optional<Boolean> shipToDefault,
			@Nullable final Optional<Boolean> visitorsAddress)
	{
		this.billTo = billTo;
		this.billToDefault = billToDefault;
		this.shipTo = shipTo;
		this.shipToDefault = shipToDefault;
		this.visitorsAddress = visitorsAddress;
	}

	public BPartnerLocationType deepCopy()
	{
		return new BPartnerLocationType(billTo, billToDefault, shipTo, shipToDefault, visitorsAddress);
	}

	public boolean getIsShipToOr(final boolean defaultValue)
	{
		return shipTo.orElse(defaultValue);
	}

	public boolean getIsShipToDefaultOr(final boolean defaultValue)
	{
		return shipToDefault.orElse(defaultValue);
	}

	public boolean getIsBillToOr(final boolean defaultValue)
	{
		return billTo.orElse(defaultValue);
	}

	public boolean getIsBillToDefaultOr(final boolean defaultValue)
	{
		return billToDefault.orElse(defaultValue);
	}

	public boolean getIsVisitorsAddressOr(final boolean defaultValue)
	{
		return visitorsAddress.orElse(defaultValue);
	}

	public void setBillToDefault(final boolean billToDefault)
	{
		this.billToDefault = Optional.of(billToDefault);
	}

	public void setShipToDefault(final boolean shipToDefault)
	{
		this.shipToDefault = Optional.of(shipToDefault);
	}
}
