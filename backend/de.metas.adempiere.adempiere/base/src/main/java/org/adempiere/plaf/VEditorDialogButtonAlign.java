package org.adempiere.plaf;

 


import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

/**
 * For editor components (e.g. VNumber, VDate etc) defines where the dialog triggering button shall be displayed (i.e. the dialog triggering button is that small button which opens the calculator for
 * VNumber, opens the Calendar for VDate etc).
 * 
 * @author tsa
 *
 */
public enum VEditorDialogButtonAlign
{
	/** Don't display it at all */
	Hide,
	/** Display it on the left side of the component */
	Left,
	/** Display it on the right side of the component */
	Right;

	public static final String DEFAULT_EditorUI = createUIKey("VEditor");
	public static final VEditorDialogButtonAlign DEFAULT_Value = Right;

	/**
	 * Adds given <code>editorButton</code> to given container using the {@link VEditorDialogButtonAlign} from {@link UIManager}.
	 * 
	 * NOTE: it is assumed that the container is using {@link BorderLayout}.
	 * 
	 * @param editorClass editor class (used to build the {@link UIDefaults} key)
	 * @param editorContainer container component
	 * @param editorButton editor button component (that will be added to container, if needed).
	 * @return true if the button was added, false if the button was not added (i.e. align was {@link #Hide}).
	 */
	public static final <T> boolean addVEditorButtonUsingBorderLayout(final Class<T> editorClass, final Container editorContainer, final JButton editorButton)
	{
		final VEditorDialogButtonAlign buttonAlign = getFromUIManager(editorClass);
		boolean added = false;
		if (buttonAlign == VEditorDialogButtonAlign.Hide || buttonAlign == null)
		{
			// don't add it
		}
		else if (buttonAlign == VEditorDialogButtonAlign.Left)
		{
			editorContainer.add(editorButton, BorderLayout.WEST);
			added = true;
		}
		else if (buttonAlign == VEditorDialogButtonAlign.Right)
		{
			editorContainer.add(editorButton, BorderLayout.EAST);
			added = true;
		}

		return added;
	}

	/**
	 * Creates the {@link UIDefaults} key for given <code>editorClass</code>.
	 * 
	 * @param editorClass
	 * @return UI defaults key
	 */
	public static final <T> String createUIKey(final Class<T> editorClass)
	{
		return createUIKey(editorClass.getSimpleName());
	}

	/**
	 * Creates the {@link UIDefaults} key for given <code>editorClassKey</code>.
	 * 
	 * @param editorClass
	 * @return UI defaults key
	 */
	public static final String createUIKey(final String editorClassKey)
	{
		final String uiKey = editorClassKey + ".DialogButtonAlign";
		return uiKey;
	}

	/** @return {@link VEditorDialogButtonAlign} value of given <code>editorClass</code> from {@link UIManager}. */
	private static final <T> VEditorDialogButtonAlign getFromUIManager(final Class<T> editorClass)
	{
		//
		// Editor property
		{
			final String alignUIKey = createUIKey(editorClass);
			final VEditorDialogButtonAlign align = (VEditorDialogButtonAlign)UIManager.get(alignUIKey);
			if (align != null)
			{
				return align;
			}
		}

		//
		// Default property
		{
			final VEditorDialogButtonAlign align = (VEditorDialogButtonAlign)UIManager.get(DEFAULT_EditorUI);
			if (align != null)
			{
				return align;
			}
		}

		//
		// Fallback to default value
		return DEFAULT_Value;
	}
}
