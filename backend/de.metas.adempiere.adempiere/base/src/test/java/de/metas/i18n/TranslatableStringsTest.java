package de.metas.i18n;

import com.google.common.collect.ImmutableList;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class TranslatableStringsTest
{
	@Test
	public void joinObjects_WithCollections()
	{
		final ITranslatableString actual = TranslatableStrings.join(" ",
				TranslatableStrings.constant("1"),
				ImmutableList.of(
						TranslatableStrings.constant("2"),
						ImmutableList.of(
								TranslatableStrings.constant("3")),
						TranslatableStrings.constant("4")),
				TranslatableStrings.constant("5"));

		final ITranslatableString expected = TranslatableStrings.joinList(" ", ImmutableList.of(
				TranslatableStrings.constant("1"),
				TranslatableStrings.constant("2"),
				TranslatableStrings.constant("3"),
				TranslatableStrings.constant("4"),
				TranslatableStrings.constant("5")));

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void amount()
	{
		final Amount amount = Amount.of(new BigDecimal("12345.67"), CurrencyCode.EUR);
		final ITranslatableString amountTrl = TranslatableStrings.amount(amount);

		assertThat(amountTrl.translate("en_US")).isEqualTo("12,345.67 EUR");
		assertThat(amountTrl.translate("de_DE")).isEqualTo("12.345,67 EUR");
		assertThat(amountTrl.translate("de_CH")).isEqualTo("12’345.67 EUR");
	}

	@Nested
	public class constant
	{
		@Test
		void emptyIsEmpty()
		{
			assertThat(ConstantTranslatableString.EMPTY.isEmpty()).isTrue();
		}
	}

	@Nested
	public class parse
	{
		@Test
		void test()
		{
			final ITranslatableString result = TranslatableStrings.parse("we use @var1@ and @var2@ bla bla.");
			assertThat(result)
					.usingRecursiveComparison()
					.isEqualTo(new CompositeTranslatableString(
							ImmutableList.of(
									ConstantTranslatableString.of("we use "),
									TranslatableStrings.adElementOrMessage("var1"),
									ConstantTranslatableString.of(" and "),
									TranslatableStrings.adElementOrMessage("var2"),
									ConstantTranslatableString.of(" bla bla.")
							),
							""));

		}
	}
}
