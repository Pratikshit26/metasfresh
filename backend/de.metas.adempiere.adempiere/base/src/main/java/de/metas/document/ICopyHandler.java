package de.metas.document;

 


/**
 * @author al
 * 
 * @param <T>
 */
public interface ICopyHandler<T extends Object>
{
	/**
	 * <b>NOTE: Called by API.</b> You should <b>ONLY</b> attempt to call <b>AT THE BEGINNING</b> of an allocation algorithm.<br>
	 * <br>
	 * Copy original values (expected to copy everything, but may be customized according to needed implementation)
	 * 
	 * @param from
	 * @param to
	 */
	void copyPreliminaryValues(T from, T to);

	/**
	 * <b>NOTE: Called by API.</b> You should <b>ONLY</b> attempt to call <b>AT THE END</b> of an allocation algorithm.<br>
	 * <br>
	 * Copy values from FROM object to TO object <i>(yeah, I'm busting your balls trying to be funny)</i>
	 * 
	 * @param from
	 * @param to
	 */
	void copyValues(T from, T to);

	/**
	 * Gets the class of our generic <code>T</code> parameter. We need this method to prepare our to-be-copied parameters with {@link org.adempiere.model.InterfaceWrapperHelper#create(Object, Class)}
	 * when we call the copy handler implementation from the copy methods of {@link ICopyHandlerBL}.
	 * 
	 * @return
	 */
	Class<T> getSupportedItemsClass();
}
