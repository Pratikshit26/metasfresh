package de.metas.product;

import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.quantity.Quantitys;
import de.metas.quantity.UOMConversionRateProvider;
import de.metas.uom.UOMConversionRate;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UomId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.function.Function;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

/**
 * Product Price / UOM
 */
@Value
@Builder(toBuilder = true)
public class ProductPrice
{
	@NonNull ProductId productId;
	@NonNull UomId uomId;
	@NonNull @Getter(AccessLevel.NONE) Money money;

	public Money toMoney()
	{
		return money;
	}

	@NonNull
	public BigDecimal toBigDecimal()
	{
		return money.toBigDecimal();
	}

	@NonNull
	public CurrencyId getCurrencyId()
	{
		return money.getCurrencyId();
	}

	public ProductPrice withValueAndUomId(@NonNull final BigDecimal moneyAmount, @NonNull final UomId uomId)
	{
		return toBuilder()
				.money(Money.of(moneyAmount, getCurrencyId()))
				.uomId(uomId)
				.build();
	}

	public ProductPrice negate()
	{
		return this.toBuilder().money(money.negate()).build();
	}

	public ProductPrice negateIf(final boolean condition)
	{
		return condition ? negate() : this;
	}

	public boolean isEqualByComparingTo(@Nullable final ProductPrice other)
	{
		if (other == null)
		{
			return false;
		}

		return other.uomId.equals(uomId) && other.money.isEqualByComparingTo(money);
	}

	public <T> T transform(@NonNull final Function<ProductPrice, T> mapper)
	{
		return mapper.apply(this);
	}

	@NonNull
	public Money computeAmount(@NonNull final Quantity quantity)
	{
		final BigDecimal amount = quantity
				.toBigDecimalAssumingUOM(uomId)
				.multiply(money.toBigDecimal());

		return Money.of(amount, money.getCurrencyId());
	}

	@NonNull
	public Money computeAmount(@NonNull final Quantity quantity, @NonNull final QuantityUOMConverter uomConverter)
	{
		final Quantity quantityInPriceUOM = uomConverter.convertQuantityTo(quantity, productId, uomId);
		final BigDecimal amount = quantityInPriceUOM.toBigDecimal()
				.multiply(money.toBigDecimal());

		return Money.of(amount, money.getCurrencyId());
	}

	public ProductPrice convertToUom(
			@NonNull final UomId toUomId,
			@NonNull final CurrencyPrecision pricePrecision,
			@NonNull final UOMConversionRateProvider uomConversionProvider)
	{
		if (this.uomId.equals(toUomId))
		{
			return this;
		}

		final UOMConversionRate rate = uomConversionProvider.getRate(productId, toUomId, uomId);
		final BigDecimal priceConv = pricePrecision.round(rate.convert(money.toBigDecimal(), UOMPrecision.TWELVE));

		return withValueAndUomId(priceConv, toUomId);
	}

	public ProductPrice toZero()
	{
		return toBuilder()
				.money(money.toZero())
				.build();
	}

	public Quantity computeQtyInTotalAmt(@NonNull final Money totalAmt, @NonNull final CurrencyPrecision currencyPrecision)
	{
		return Quantitys.of(totalAmt.divide(money, currencyPrecision).toBigDecimal(), uomId);
	}

	public ProductPrice round(@NonNull final CurrencyPrecision precision)
	{
		return toBuilder()
				.money(money.round(precision))
				.build();
	}
}
