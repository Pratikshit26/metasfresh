package de.metas.security.permissions.record_access.handlers;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.security.permissions.record_access.RecordAccessFeature;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@ToString
public final class CompositeRecordAccessHandler implements RecordAccessHandler
{
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static CompositeRecordAccessHandler of(@NonNull final Optional<List<RecordAccessHandler>> handlers)
	{
		return handlers
				.map(CompositeRecordAccessHandler::of)
				.orElse(CompositeRecordAccessHandler.EMPTY);
	}

	public static CompositeRecordAccessHandler of(@NonNull final Collection<RecordAccessHandler> handlers)
	{
		if (handlers.isEmpty())
		{
			return CompositeRecordAccessHandler.EMPTY;
		}
		else
		{
			return new CompositeRecordAccessHandler(handlers);
		}
	}

	public static final CompositeRecordAccessHandler EMPTY = new CompositeRecordAccessHandler();

	private final ImmutableSet<RecordAccessHandler> handlers;
	@Getter
	private final ImmutableSet<RecordAccessFeature> handledFeatures;
	@Getter
	private final ImmutableSet<String> handledTableNames;

	private CompositeRecordAccessHandler()
	{
		handlers = ImmutableSet.of();
		handledFeatures = ImmutableSet.of();
		handledTableNames = ImmutableSet.of();
	}

	private CompositeRecordAccessHandler(@NonNull final Collection<RecordAccessHandler> handlers)
	{
		this.handlers = ImmutableSet.copyOf(handlers);

		handledFeatures = handlers.stream()
				.flatMap(handler -> handler.getHandledFeatures().stream())
				.collect(ImmutableSet.toImmutableSet());

		handledTableNames = handlers.stream()
				.flatMap(handler -> handler.getHandledTableNames().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean isEmpty()
	{
		return handlers.isEmpty();
	}

	public ImmutableSet<RecordAccessHandler> handlingFeatureSet(final Set<RecordAccessFeature> features)
	{
		if (features.isEmpty())
		{
			return ImmutableSet.of();
		}

		return handlers.stream()
				.filter(handler -> isAnyFeatureHandled(handler, features))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static boolean isAnyFeatureHandled(final RecordAccessHandler handler, final Set<RecordAccessFeature> features)
	{
		return !Sets.intersection(handler.getHandledFeatures(), features).isEmpty();
	}

	public boolean isTableHandled(@NonNull final String tableName)
	{
		return getHandledTableNames().contains(tableName);
	}
}
