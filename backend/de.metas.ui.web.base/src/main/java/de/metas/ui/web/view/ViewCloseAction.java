package de.metas.ui.web.view;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

/**
 * @implNote If you want to add a new action, e.g. MY_CLOSE, then you shall
 * <ul>
 * <li>add the MY_CLOSE enum member here</li>
 * <li>add the `webui.modal.actions.my_close` to AD_Message (remark: `webui.modal.actions.` prefix plus enum name lowe-case) </li>
 * </ul>
 */
public enum ViewCloseAction
{
	BACK,
	CANCEL,
	DONE,
	CLOSE,
	;

	public static ViewCloseAction fromJsonOr(final String json, final ViewCloseAction defaultValue)
	{
		if (json == null || json.isEmpty())
		{
			return defaultValue;
		}

		return valueOf(json);
	}

	public boolean isDone()
	{
		return this.equals(DONE);
	}
}
