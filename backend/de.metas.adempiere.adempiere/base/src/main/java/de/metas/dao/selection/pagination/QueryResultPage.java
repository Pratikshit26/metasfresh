package de.metas.dao.selection.pagination;

import java.time.Instant;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class QueryResultPage<T>
{
	@NonNull
	PageDescriptor currentPageDescriptor;

	@Nullable
	PageDescriptor nextPageDescriptor;

	/** Number of all items that are part of the selection which this page is a part of. */
	int totalSize;

	/** Point in time when the selection was done */
	@NonNull
	Instant resultTimestamp;

	@NonNull
	ImmutableList<T> items;

	public <R> QueryResultPage<R> mapTo(@NonNull final Function<T, R> mapper)
	{
		final ImmutableList<R> mappedItems = items.stream()
				.map(mapper)
				.collect(ImmutableList.toImmutableList());

		return new QueryResultPage<>(currentPageDescriptor, nextPageDescriptor, totalSize, resultTimestamp, mappedItems);
	}

	public <R> QueryResultPage<R> mapAllTo(@NonNull final Function<ImmutableList<T>, ImmutableList<R>> mapper)
	{
		return withItems(mapper.apply(getItems()));
	}

	public <R> QueryResultPage<R> withItems(@NonNull final ImmutableList<R> replacementItems)
	{
		return new QueryResultPage<>(
				currentPageDescriptor.withSize(replacementItems.size()),
				nextPageDescriptor,
				totalSize,
				resultTimestamp,
				replacementItems);
	}
}
