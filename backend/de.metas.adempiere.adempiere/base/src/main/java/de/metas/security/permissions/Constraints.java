package de.metas.security.permissions;

 


import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.compiere.util.Env;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;

import de.metas.util.Check;

/**
 * {@link Constraint}s collections.
 * 
 * @author tsa
 *
 */
public final class Constraints
{
	public static Builder builder()
	{
		return new Builder();
	}

	private final ImmutableMap<Class<? extends Constraint>, Constraint> constraints;

	private Constraints(final Builder builder)
	{
		constraints = builder.getConstraints();
	}

	@Override
	public String toString()
	{
		// NOTE: we are making it translatable friendly because it's displayed in Prefereces->Info->Rollen

		final String constraintsName = getClass().getSimpleName();
		final Collection<Constraint> constraintsList = constraints.values();

		final StringBuilder sb = new StringBuilder();
		sb.append(constraintsName).append(": ");
		if (constraintsList.isEmpty())
		{
			sb.append("@None@");
		}
		else
		{
			sb.append(Env.NL);
		}

		Joiner.on(Env.NL)
				.skipNulls()
				.appendTo(sb, constraintsList);

		return sb.toString();
	}

	public <T extends Constraint> Optional<T> getConstraint(final Class<T> constraintType)
	{
		@SuppressWarnings("unchecked")
		final T constraint = (T)constraints.get(constraintType);
		return Optional.ofNullable(constraint);
	}

	public static final class Builder
	{
		private final LinkedHashMap<Class<? extends Constraint>, Constraint> constraints = new LinkedHashMap<>();

		private Builder()
		{
			super();
		}

		public Constraints build()
		{
			return new Constraints(this);
		}

		private ImmutableMap<Class<? extends Constraint>, Constraint> getConstraints()
		{
			return ImmutableMap.copyOf(constraints);
		}

		public Builder addConstraint(final Constraint constraint)
		{
			Check.assumeNotNull(constraint, "constraint not null");
			constraints.put(constraint.getClass(), constraint);
			return this;
		}

		public Builder addConstraintIfNotEquals(final Constraint constraint, final Constraint constraintToExclude)
		{
			Check.assumeNotNull(constraint, "constraint not null");
			Check.assumeNotNull(constraintToExclude, "constraintToExclude not null");

			if (constraint.equals(constraintToExclude))
			{
				return this;
			}

			constraints.put(constraint.getClass(), constraint);
			return this;
		}

	}
}
