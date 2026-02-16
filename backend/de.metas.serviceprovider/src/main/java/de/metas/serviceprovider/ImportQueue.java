/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ImportQueue<T>
{
	private static final Logger log = LogManager.getLogger(ImportQueue.class);

	private final LinkedBlockingQueue<T> queue;
	private final String logPrefix;

	public ImportQueue(final int queueCapacity, final String logPrefix)
	{
		this.queue = new LinkedBlockingQueue<>(queueCapacity);
		this.logPrefix = logPrefix;
	}

	@NonNull
	public ImmutableList<T> drainAll()
	{
		final ArrayList<T> objectList = new ArrayList<>();

		try
		{
			final Optional<T> object = Optional.ofNullable(queue.poll(2, TimeUnit.SECONDS));
			object.ifPresent(objectList::add);

			queue.drainTo(objectList);

			log.debug(" {} drained {} objects for processing!", logPrefix, objectList.size());
		}
		catch (final InterruptedException e)
		{
			Loggables.withLogger(log, Level.ERROR).addLog(logPrefix + e.getMessage(),e);
		}

		return ImmutableList.copyOf(objectList);
	}

	public boolean isEmpty()
	{
		return queue.isEmpty();
	}

	public void add(@NonNull final T object)
	{
		try
		{
			queue.put(object);
		}
		catch (final InterruptedException e)
		{
			Loggables.withLogger(log, Level.ERROR).addLog(logPrefix + e.getMessage(),e);
		}
	}
}
