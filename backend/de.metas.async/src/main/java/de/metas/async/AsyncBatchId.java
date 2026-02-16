package de.metas.async;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import java.util.function.Supplier;

/*
 * #%L
 * de.metas.async
     
 * #L%
 */

@Value
public class AsyncBatchId implements RepoIdAware
{
	public static final AsyncBatchId NONE_ASYNC_BATCH_ID = AsyncBatchId.ofRepoId(1);

	int repoId;

	@JsonCreator
	public static AsyncBatchId ofRepoId(final int repoId)
	{
		return new AsyncBatchId(repoId);
	}

	@Nullable
	public static AsyncBatchId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new AsyncBatchId(repoId) : null;
	}

	@NonNull
	public static AsyncBatchId ofRepoIdOrNone(final int repoId)
	{
		return repoId > 0 ? new AsyncBatchId(repoId) : NONE_ASYNC_BATCH_ID;
	}

	@NonNull
	public static AsyncBatchId ofRepoIdOr(final int repoId, @NonNull final Supplier<AsyncBatchId>supplier)
	{
		return repoId > 0 ? new AsyncBatchId(repoId) : supplier.get();
	}
	
	public static int toRepoId(@Nullable final AsyncBatchId asyncBatchId)
	{
		return asyncBatchId != null ? asyncBatchId.getRepoId() : -1;
	}

	@Nullable
	public static AsyncBatchId toAsyncBatchIdOrNull(@Nullable final AsyncBatchId asyncBatchId)
	{
		return asyncBatchId != null && !asyncBatchId.equals(NONE_ASYNC_BATCH_ID)
				? asyncBatchId
				: null;
	}

	private AsyncBatchId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "asyncBatchId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
