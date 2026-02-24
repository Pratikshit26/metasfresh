package org.adempiere.ui.editor;

 


import javax.swing.Action;
import javax.swing.KeyStroke;

public final class NullCopyPasteSupportEditor implements ICopyPasteSupportEditor
{
	public static final transient NullCopyPasteSupportEditor instance = new NullCopyPasteSupportEditor();

	public static final boolean isNull(final ICopyPasteSupportEditor copyPasteSupport)
	{
		return copyPasteSupport == null || copyPasteSupport == instance;
	}

	private NullCopyPasteSupportEditor()
	{
		super();
	}

	@Override
	public void executeCopyPasteAction(CopyPasteActionType actionType)
	{
		// nothing
	}

	@Override
	public Action getCopyPasteAction(final CopyPasteActionType actionType)
	{
		return null;
	}

	/**
	 * @throws IllegalStateException always
	 */
	@Override
	public void putCopyPasteAction(final CopyPasteActionType actionType, final Action action, final KeyStroke keyStroke)
	{
		throw new IllegalStateException("Setting copy/paste action not supported");
	}

	/**
	 * @return false
	 */
	@Override
	public boolean isCopyPasteActionAllowed(CopyPasteActionType actionType)
	{
		return false;
	}
}
