package de.metas.bpartner.product.stats;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;

import de.metas.common.util.time.SystemTime;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.product.stats.InvoiceChangedEvent.ProductPrice;
import de.metas.event.SimpleObjectSerializer;
import de.metas.invoice.InvoiceId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

public class EventsTest
{
	@Test
	public void test_InOutChangedEvent()
	{
		testSerializeDeserialize(InOutChangedEvent.builder()
				.bpartnerId(BPartnerId.ofRepoId(123))
				.movementDate(SystemTime.asInstant())
				.soTrx(SOTrx.SALES)
				.reversal(true)
				.productIds(ImmutableSet.of(ProductId.ofRepoId(1), ProductId.ofRepoId(2)))
				.build());
	}

	@Test
	public void test_InvoiceChangedEvent()
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(102);

		testSerializeDeserialize(InvoiceChangedEvent.builder()
				.invoiceId(InvoiceId.ofRepoId(123))
				.invoiceDate(LocalDate.of(2019, Month.FEBRUARY, 24))
				.bpartnerId(BPartnerId.ofRepoId(444))
				.soTrx(SOTrx.SALES)
				.reversal(false)
				.productPrice(ProductPrice.builder()
						.productId(ProductId.ofRepoId(1001))
						.price(Money.of("11001.01", currencyId))
						.build())
				.productPrice(ProductPrice.builder()
						.productId(ProductId.ofRepoId(1002))
						.price(Money.of("11002.02", currencyId))
						.build())
				.productPrice(ProductPrice.builder()
						.productId(ProductId.ofRepoId(1003))
						.price(Money.of("11003.03", currencyId))
						.build())
				.build());
	}

	private void testSerializeDeserialize(final Object event)
	{
		final SimpleObjectSerializer serializer = SimpleObjectSerializer.get();

		final String json = serializer.serialize(event);
		// System.out.println("JSON\n" + json);

		final Object eventDeserialized = serializer.deserialize(json, event.getClass());

		assertThat(eventDeserialized).isEqualTo(event);
	}
}
