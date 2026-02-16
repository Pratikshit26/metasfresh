package de.metas.rest_api.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.GLN;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.rest_api.utils.IdentifierString.Type;
import de.metas.util.lang.ExternalId;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

class IdentifierStringTest
{
	@Test
	void of_ExternalId()
	{
		final IdentifierString testee = IdentifierString.of("ext-abcd");

		assertThat(testee.getType()).isEqualTo(Type.EXTERNAL_ID);
		assertThat(testee.asExternalId()).isEqualTo(ExternalId.of("abcd"));
	}

	@Test
	void invalid_ExternalId()
	{
		assertThatThrownBy(() -> IdentifierString.of("ext-"))
				.hasMessage("Invalid external ID: `ext-`");
		assertThatThrownBy(() -> IdentifierString.of("ext-      "))
				.hasMessage("Invalid external ID: `ext-      `");
	}

	@Test
	void of_Value()
	{
		final IdentifierString testee = IdentifierString.of("val-abcd");

		assertThat(testee.getType()).isEqualTo(Type.VALUE);
		assertThat(testee.asValue()).isEqualTo("abcd");
	}

	@Test
	void invalid_Value()
	{
		assertThatThrownBy(() -> IdentifierString.of("val-"))
				.hasMessage("Invalid value: `val-`");
		assertThatThrownBy(() -> IdentifierString.of("val-      "))
				.hasMessage("Invalid value: `val-      `");
	}

	@Test
	void of_GLN()
	{
		final IdentifierString testee = IdentifierString.of("gln-abcd");

		assertThat(testee.getType()).isEqualTo(Type.GLN);
		assertThat(testee.asGLN()).isEqualTo(GLN.ofString("abcd"));
	}

	@Test
	void invalid_GLN()
	{
		assertThatThrownBy(() -> IdentifierString.of("gln-"))
				.hasMessage("Invalid GLN: `gln-`");
		assertThatThrownBy(() -> IdentifierString.of("gln-      "))
				.hasMessage("Invalid GLN: `gln-      `");
	}

	@Test
	void of_Doc()
	{
		final IdentifierString testee = IdentifierString.of("doc-abcd");

		assertThat(testee.getType()).isEqualTo(Type.DOC);
		assertThat(testee.asDoc()).isEqualTo("abcd");
	}

	@Test
	void invalid_Doc()
	{
		assertThatThrownBy(() -> IdentifierString.of("doc-"))
				.hasMessage("Invalid documentId: `doc-`");
		assertThatThrownBy(() -> IdentifierString.of("doc-      "))
				.hasMessage("Invalid documentId: `doc-      `");
	}

	@Test
	void of_MetasfreshId()
	{
		final IdentifierString testee = IdentifierString.of("12345");

		assertThat(testee.getType()).isEqualTo(Type.METASFRESH_ID);
		assertThat(testee.asMetasfreshId()).isEqualTo(MetasfreshId.of(12345));
	}

	@Test
	void invalid_MetasfreshId()
	{
		assertThatThrownBy(() -> IdentifierString.of("12345x"))
				.isInstanceOf(InvalidIdentifierException.class)
				.hasMessageContaining("12345x");
	}

	@Test
	void testFromToJson()
	{
		final ImmutableMap<Type, String> testValues = ImmutableMap.<Type, String> builder()
				.put(Type.METASFRESH_ID, "12345")
				.put(Type.EXTERNAL_ID, "ext-someExternalId")
				.put(Type.VALUE, "val-someValue")
				.put(Type.GLN, "gln-someGLN")
				.put(Type.DOC, "doc-someDoc")
				.put(Type.INTERNALNAME, "int-someInternalName")
				.build();

		for (final Type type : Type.values())
		{
			final String testValue = testValues.get(type);
			if (testValue == null)
			{
				throw new AdempiereException("No test value defined for type=" + type);
			}

			final IdentifierString identifierString = IdentifierString.of(testValue);
			assertThat(identifierString.toJson()).isEqualTo(testValue);
		}
	}

}
