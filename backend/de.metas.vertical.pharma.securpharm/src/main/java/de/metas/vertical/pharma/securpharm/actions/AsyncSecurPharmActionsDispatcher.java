package de.metas.vertical.pharma.securpharm.actions;

import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 * #%L
 * metasfresh-pharma.securpharm
     
 * #L%
 */

@Component
@Primary
public class AsyncSecurPharmActionsDispatcher implements SecurPharmActionsDispatcher
{
	private static final Topic TOPIC = Topic.distributed("de.metas.vertical.pharma.securpharm.actions");

	private final IEventBus eventBus;
	private final Executor executor;

	public AsyncSecurPharmActionsDispatcher(@NonNull final IEventBusFactory eventBusFactory)
	{
		eventBus = eventBusFactory.getEventBus(TOPIC);
		executor = createAsyncExecutor();
	}

	private static Executor createAsyncExecutor()
	{
		final CustomizableThreadFactory asyncThreadFactory = new CustomizableThreadFactory(SecurPharmActionProcessor.class.getSimpleName());
		asyncThreadFactory.setDaemon(true);

		return Executors.newSingleThreadExecutor(asyncThreadFactory);
	}

	@Override
	public void subscribe(@NonNull final SecurPharmActionsHandler handler)
	{
		final AsyncSecurPharmActionsHandler asyncHandler = new AsyncSecurPharmActionsHandler(handler, executor);
		eventBus.subscribeOn(SecurPharmaActionRequest.class, asyncHandler::handleActionRequest);
	}

	@Override
	public void post(@NonNull final SecurPharmaActionRequest request)
	{
		eventBus.enqueueObject(request);
	}

	@ToString
	private static class AsyncSecurPharmActionsHandler implements SecurPharmActionsHandler
	{
		private final SecurPharmActionsHandler delegate;
		private final Executor executor;

		private AsyncSecurPharmActionsHandler(
				@NonNull final SecurPharmActionsHandler delegate,
				@NonNull final Executor executor)
		{
			this.delegate = delegate;
			this.executor = executor;
		}

		@Override
		public void handleActionRequest(@NonNull final SecurPharmaActionRequest request)
		{
			executor.execute(() -> delegate.handleActionRequest(request));
		}

	}
}
