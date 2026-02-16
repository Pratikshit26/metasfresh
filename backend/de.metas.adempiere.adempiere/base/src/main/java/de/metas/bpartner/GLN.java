package de.metas.bpartner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

/**
 * Global Location Number.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Global_Location_Number">GLN</a>
 */
@Value
public class GLN
{
	public static final int LENGTH = 13;

	@JsonCreator
	public static GLN ofString(@NonNull final String code)
	{
		return new GLN(code);
	}

	public static GLN ofNullableString(@Nullable final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		return codeNorm != null ? ofString(code) : null;
	}

	@NonNull String code;

	private GLN(@NonNull final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		if (codeNorm == null)
		{
			throw new AdempiereException("GLN code cannot be blank");
		}
		this.code = codeNorm;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getCode();
	}

	@NonNull
	@JsonValue
	public String getCode()
	{
		return code;
	}

	public static Set<String> toStringSet(@Nullable final Collection<GLN> glns)
	{
		if (glns == null || glns.isEmpty())
		{
			return ImmutableSet.of();
		}

		return glns.stream().map(GLN::getCode).collect(ImmutableSet.toImmutableSet());
	}

	public static String toCode(@Nullable final GLN gln)
	{
		return gln != null ? gln.getCode() : null;
	}

	public static boolean equals(@Nullable final GLN gln1, @Nullable final GLN gln2)
	{
		return Objects.equals(gln1, gln2);
	}
}
