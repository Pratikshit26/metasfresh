package de.metas.event.jmx;

 


import de.metas.event.IEventBus;

/**
 * JMX bean used to manage the {@link IEventBus}es.
 * 
 * @author tsa
 *
 */
public interface JMXEventBusManagerMBean
{
	boolean isEnabled();

	String getRemoteEndpointInfo();

	boolean isRemoteEndpointConnected();

	String getSenderId();
}
