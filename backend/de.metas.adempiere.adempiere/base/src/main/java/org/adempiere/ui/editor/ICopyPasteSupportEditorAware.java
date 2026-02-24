package org.adempiere.ui.editor;

 


/**
 * Implemented by those UI editors/components which can provide an {@link ICopyPasteSupportEditor} instance.
 * 
 * @author tsa
 *
 */
public interface ICopyPasteSupportEditorAware
{
	public ICopyPasteSupportEditor getCopyPasteSupport();
}
