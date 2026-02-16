package de.metas.currency;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

/**
 * Three letter ISO 4217 Code of the Currency
 */
@EqualsAndHashCode
public final class CurrencyCode implements Comparable<CurrencyCode>
{
	@JsonCreator
	public static CurrencyCode ofThreeLetterCode(@NonNull final String threeLetterCode)
	{
		return interner.intern(new CurrencyCode(threeLetterCode));
	}

	private static final Interner<CurrencyCode> interner = Interners.newStrongInterner();
	public static final CurrencyCode EUR = interner.intern(new CurrencyCode("EUR"));
	public static final CurrencyCode USD = interner.intern(new CurrencyCode("USD"));
	public static final CurrencyCode CHF = interner.intern(new CurrencyCode("CHF"));

	private final String threeLetterCode;

	private CurrencyCode(@NonNull final String threeLetterCode)
	{
		if (threeLetterCode.length() != 3)
		{
			throw new AdempiereException("Invalid currency ISO 4217 code: " + threeLetterCode);
		}

		this.threeLetterCode = threeLetterCode;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return threeLetterCode;
	}

	@JsonValue
	public String toThreeLetterCode()
	{
		return threeLetterCode;
	}

	public boolean isEuro()
	{
		return this.equals(EUR);
	}

	public boolean isCHF()
	{
		return this.equals(CHF);
	}

	@Override
	public int compareTo(@NonNull final CurrencyCode other) {return this.threeLetterCode.compareTo(other.threeLetterCode);}

	public static boolean equals(@Nullable CurrencyCode cc1, @Nullable CurrencyCode cc2) {return Objects.equals(cc1, cc2);}
}
