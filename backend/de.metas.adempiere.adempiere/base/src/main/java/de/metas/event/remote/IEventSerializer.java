package de.metas.event.remote;

import com.google.common.annotations.VisibleForTesting;

 


import de.metas.event.Event;

/**
 * Serialize/Deserialize {@link Event} objects.
 * 
 * @author tsa
 *
 */
@VisibleForTesting
public interface IEventSerializer
{
	String toString(Event event);

	Event fromString(String eventStr);
}
