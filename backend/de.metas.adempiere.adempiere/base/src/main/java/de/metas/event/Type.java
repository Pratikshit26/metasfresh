package de.metas.event;

 


/**
 * The type of the topic or event bus.<br>
 * Notes:
 * <ul>
 * <li>Two topics with the same name but different type are different and will <b>always</b> have different event busses. Therefore, events posted to one of of those topics won't be forwarded
 * subscribers of the other one.
 * <li>Creating an {@link IEventBus} for a remote {@link Topic} might still result in a local eventBus if the remote-forwarding feature is configured to be "off" for the given topic name or
 * login-user, or in general.
 * </ul>
 * 
 * @author ts
 *
 */
public enum Type
{
	/**
	 * If events are posted to a local topic, only local subscribers will be notified.
	 */
	LOCAL,

	/**
	 * If events are posted to a "distributed" topic, not only subscribers on this machine, but also subscribers on other machines will be notified.
	 */
	DISTRIBUTED
}
