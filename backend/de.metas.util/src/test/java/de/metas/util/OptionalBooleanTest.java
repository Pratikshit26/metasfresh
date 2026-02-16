package de.metas.util;

import com.google.common.collect.ImmutableSet;
import org.adempiere.util.lang.Mutable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.util
     
 * #L%
 */

public class OptionalBooleanTest
{
	@Test
	public void only_true_false_and_unknown()
	{
		assertThat(ImmutableSet.copyOf(OptionalBoolean.values()))
				.containsExactly(OptionalBoolean.TRUE, OptionalBoolean.FALSE, OptionalBoolean.UNKNOWN);
	}

	@Test
	public void checkFlags_for_TRUE()
	{
		assertThat(OptionalBoolean.TRUE.isPresent()).isTrue();
		assertThat(OptionalBoolean.TRUE.isUnknown()).isFalse();
		assertThat(OptionalBoolean.TRUE.isTrue()).isTrue();
		assertThat(OptionalBoolean.TRUE.isFalse()).isFalse();
	}

	@Test
	public void checkFlags_for_FALSE()
	{
		assertThat(OptionalBoolean.FALSE.isPresent()).isTrue();
		assertThat(OptionalBoolean.FALSE.isUnknown()).isFalse();
		assertThat(OptionalBoolean.FALSE.isTrue()).isFalse();
		assertThat(OptionalBoolean.FALSE.isFalse()).isTrue();
	}

	@Test
	public void checkFlags_for_UNKNOWN()
	{
		assertThat(OptionalBoolean.UNKNOWN.isPresent()).isFalse();
		assertThat(OptionalBoolean.UNKNOWN.isUnknown()).isTrue();
		assertThat(OptionalBoolean.UNKNOWN.isTrue()).isFalse();
		assertThat(OptionalBoolean.UNKNOWN.isFalse()).isFalse();
	}

	@Test
	public void test_ofBoolean()
	{
		assertThat(OptionalBoolean.ofBoolean(true)).isSameAs(OptionalBoolean.TRUE);
		assertThat(OptionalBoolean.ofBoolean(false)).isSameAs(OptionalBoolean.FALSE);
	}

	@Test
	public void test_ofNullableString()
	{
		assertThat(OptionalBoolean.ofNullableString("Y")).isSameAs(OptionalBoolean.TRUE);
		assertThat(OptionalBoolean.ofNullableString("y")).isSameAs(OptionalBoolean.TRUE);
		assertThat(OptionalBoolean.ofNullableString("true")).isSameAs(OptionalBoolean.TRUE);

		assertThat(OptionalBoolean.ofNullableString("N")).isSameAs(OptionalBoolean.FALSE);
		assertThat(OptionalBoolean.ofNullableString("n")).isSameAs(OptionalBoolean.FALSE);
		assertThat(OptionalBoolean.ofNullableString("false")).isSameAs(OptionalBoolean.FALSE);

		assertThat(OptionalBoolean.ofNullableString("wrongValue")).isSameAs(OptionalBoolean.FALSE);

		assertThat(OptionalBoolean.ofNullableString(null)).isSameAs(OptionalBoolean.UNKNOWN);
		assertThat(OptionalBoolean.ofNullableString("")).isSameAs(OptionalBoolean.UNKNOWN);
		assertThat(OptionalBoolean.ofNullableString("      ")).isSameAs(OptionalBoolean.UNKNOWN);
	}

	@Test
	public void test_orElse()
	{
		assertThat(OptionalBoolean.TRUE.orElseTrue()).isTrue();
		assertThat(OptionalBoolean.TRUE.orElseFalse()).isTrue();

		assertThat(OptionalBoolean.FALSE.orElseTrue()).isFalse();
		assertThat(OptionalBoolean.FALSE.orElseFalse()).isFalse();

		assertThat(OptionalBoolean.UNKNOWN.orElseTrue()).isTrue();
		assertThat(OptionalBoolean.UNKNOWN.orElseFalse()).isFalse();
	}

	@Nested
	class ifPresent
	{
		@Test
		void TRUE()
		{
			final Mutable<Boolean> captor = new Mutable<>(null);
			OptionalBoolean.TRUE.ifPresent(captor::setValue);
			assertThat(captor.getValue()).isTrue();
		}

		@Test
		void FALSE()
		{
			final Mutable<Boolean> captor = new Mutable<>(null);
			OptionalBoolean.FALSE.ifPresent(captor::setValue);
			assertThat(captor.getValue()).isFalse();
		}

		@Test
		void UNKNOWN()
		{
			final Mutable<Boolean> captor = new Mutable<>(null);
			OptionalBoolean.UNKNOWN.ifPresent(captor::setValue);
			assertThat(captor.getValue()).isNull();
		}
	}
}
