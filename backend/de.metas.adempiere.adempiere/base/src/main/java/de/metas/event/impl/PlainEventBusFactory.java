package de.metas.event.impl;

import com.google.common.collect.ImmutableList;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.log.EventLogService;
import de.metas.event.log.EventLogsRepository;
import de.metas.monitoring.adapter.MicrometerPerformanceMonitoringService;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.compiere.Adempiere;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

/**
 * Empty dummy factory for unit testing.
 */
public class PlainEventBusFactory implements IEventBusFactory
{
	public static PlainEventBusFactory newInstance()
	{
		return new PlainEventBusFactory();
	}

	private final HashMap<Topic, EventBus> eventBuses = new HashMap<>();

	public PlainEventBusFactory()
	{
		assertJUnitTestMode();
	}

	private static void assertJUnitTestMode()
	{
		if (!Adempiere.isUnitTestMode())
		{
			throw new IllegalStateException(PlainEventBusFactory.class.getName() + " shall be used only in JUnit test mode");
		}
	}

	@Override
	public IEventBus getEventBus(final Topic topic)
	{
		assertJUnitTestMode();
		return eventBuses.computeIfAbsent(topic, this::createEventBus);
	}

	private EventBus createEventBus(final Topic topic)
	{
		final MicrometerEventBusStatsCollector micrometerEventBusStatsCollector = EventBusFactory.createMicrometerEventBusStatsCollector(topic, new SimpleMeterRegistry());
		final EventBusMonitoringService eventBusMonitoringService = new EventBusMonitoringService(new MicrometerPerformanceMonitoringService(new SimpleMeterRegistry()));

		final ExecutorService executor = null;
		return new EventBus(topic, executor, micrometerEventBusStatsCollector, new PlainEventEnqueuer(), eventBusMonitoringService, new EventLogService(new EventLogsRepository()));
	}

	@Override
	public IEventBus getEventBusIfExists(final Topic topic)
	{
		assertJUnitTestMode();
		return eventBuses.get(topic);
	}

	@Override
	public List<IEventBus> getAllEventBusInstances()
	{
		assertJUnitTestMode();
		return ImmutableList.copyOf(eventBuses.values());
	}

	@Override
	public void initEventBussesWithGlobalListeners()
	{
		assertJUnitTestMode();
		// as of now, no unit test needs an implementation.
	}

	@Override
	public void destroyAllEventBusses()
	{
		assertJUnitTestMode();
		// as of now, no unit test needs an implementation.
	}

	@Override
	public void registerGlobalEventListener(final Topic topic, final IEventListener listener)
	{
		assertJUnitTestMode();
		// as of now, no unit test needs an implementation.
	}

	@Override
	public void addAvailableUserNotificationsTopic(final Topic topic)
	{
		assertJUnitTestMode();
		// as of now, no unit test needs an implementation.
	}

	@Override
	public void registerUserNotificationsListener(final IEventListener listener)
	{
		assertJUnitTestMode();
		// as of now, no unit test needs an implementation.
	}

	@Override
	public void unregisterUserNotificationsListener(final IEventListener listener)
	{
		assertJUnitTestMode();
	}

	@Override
	public boolean checkRemoteEndpointStatus()
	{
		assertJUnitTestMode();
		return false;
	}
}
