package org.adempiere.ad.callout.api;

 


/**
 * Implementations of this class are wrapping actual callout implementations
 * 
 * @author tsa
 * 
 */
public interface ICalloutInstance
{
	/**
	 * Gets an unique identifier of this callout instance.
	 * 
	 * @return callout unique identifier
	 */
	String getId();

	void execute(ICalloutExecutor executor, ICalloutField field) throws Exception;
}
