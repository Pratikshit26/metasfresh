package de.metas.util.reducers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.util
     
 * #L%
 */

@ToString
final class SingleValueReducer<T> implements BinaryOperator<T>
{
	static final transient SingleValueReducer<Object> DEFAULT = new SingleValueReducer<>();

	private final Function<List<T>, ? extends RuntimeException> exceptionFactory;

	private SingleValueReducer()
	{
		this.exceptionFactory = values -> Check.mkEx("Distinct values are not allowed: " + values);
	}

	SingleValueReducer(@NonNull final Function<List<T>, ? extends RuntimeException> exceptionFactory)
	{
		this.exceptionFactory = exceptionFactory;
	}

	@Override
	public T apply(@NonNull final T value1, @NonNull final T value2)
	{
		if (Objects.equals(value1, value2))
		{
			return value1;
		}
		else
		{
			throw exceptionFactory.apply(Arrays.asList(value1, value2));
		}
	}
}
