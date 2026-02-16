package de.metas.currency;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class CurrencyCodeTest
{
	@Nested
	public class hardCodedCurrencies
	{
		@Test
		public void euro()
		{
			assertThat(CurrencyCode.ofThreeLetterCode(CurrencyCode.EUR.toThreeLetterCode()))
					.isSameAs(CurrencyCode.EUR);
			assertThat(CurrencyCode.EUR.isEuro()).isTrue();
		}

		@Test
		public void chf()
		{
			assertThat(CurrencyCode.ofThreeLetterCode(CurrencyCode.CHF.toThreeLetterCode()))
					.isSameAs(CurrencyCode.CHF);

			assertThat(CurrencyCode.CHF.isEuro()).isFalse();
			assertThat(CurrencyCode.CHF.isCHF()).isTrue();
		}

		@Test
		public void usd()
		{
			assertThat(CurrencyCode.ofThreeLetterCode(CurrencyCode.USD.toThreeLetterCode()))
					.isSameAs(CurrencyCode.USD);

			assertThat(CurrencyCode.USD.isEuro()).isFalse();
			assertThat(CurrencyCode.USD.isCHF()).isFalse();
		}
	}

	@Test
	public void ofThreeLetterCode_uses_interner()
	{
		final CurrencyCode ron1 = CurrencyCode.ofThreeLetterCode("RON");
		final CurrencyCode ron2 = CurrencyCode.ofThreeLetterCode("RON");
		assertThat(ron1).isSameAs(ron2);
	}
}
