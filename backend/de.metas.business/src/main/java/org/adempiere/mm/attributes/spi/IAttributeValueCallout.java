package org.adempiere.mm.attributes.spi;

 


import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_M_Attribute;

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IAttributeValueCallout
{
	/**
	 * Fired when attribute value was changed in given context.
	 * 
	 * @param attributeValueContext
	 * @param attributeSet
	 * @param attribute
	 * @param valueOld
	 * @param valueNew
	 */
	void onValueChanged(IAttributeValueContext attributeValueContext, IAttributeSet attributeSet, I_M_Attribute attribute, Object valueOld, Object valueNew);

	/**
	 * Generates an initial value for the current attribute.
	 * 
	 * @param attributeSet
	 * @param attribute
	 * @param valueInitialDefault default suggested by default or <code>null</code> if there is no suggestion
	 * @return seed value
	 */
	Object generateSeedValue(IAttributeSet attributeSet, I_M_Attribute attribute, Object valueInitialDefault);

	/**
	 * @param ctx evaluation context
	 * @param attributeSet
	 * @param attribute
	 * @return true if given attribute is readonly for user
	 */
	boolean isReadonlyUI(final IAttributeValueContext ctx, IAttributeSet attributeSet, I_M_Attribute attribute);

	boolean isAlwaysEditableUI(IAttributeValueContext ctx, IAttributeSet attributeSet, I_M_Attribute attribute);
	
	default boolean isDisplayedUI(IAttributeSet attributeSet, I_M_Attribute attribute)
	{
		return true;
	}
}
