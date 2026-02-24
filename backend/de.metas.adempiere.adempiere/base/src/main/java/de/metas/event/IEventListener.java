package de.metas.event;

 


/**
 * {@link IEventBus} listener.
 *
 * @author tsa
 *
 */
@FunctionalInterface
public interface IEventListener
{
	/**
	 * Called when a new event was received.
	 *
	 * @param eventBus event bus on which the event was received.
	 * @param event received event.
	 */
	void onEvent(final IEventBus eventBus, final Event event);
}
