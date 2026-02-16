package de.metas.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.uom.UomId;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

public class ProductPriceTest
{
	@Test
	public void test_withValueAndUomId()
	{
		final ProductId productId = ProductId.ofRepoId(1);
		final CurrencyId currencyId = CurrencyId.ofRepoId(1);
		final UomId uomId1 = UomId.ofRepoId(1);
		final UomId uomId2 = UomId.ofRepoId(2);

		final ProductPrice price = ProductPrice.builder()
				.productId(productId)
				.money(Money.of(100, currencyId))
				.uomId(uomId1)
				.build();

		assertThat(price
				.withValueAndUomId(new BigDecimal(101), uomId2)
				.withValueAndUomId(new BigDecimal(100), uomId1))
						.isEqualTo(price);
	}
}
