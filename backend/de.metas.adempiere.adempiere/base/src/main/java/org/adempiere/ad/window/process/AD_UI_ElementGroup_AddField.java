package org.adempiere.ad.window.process;

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdUIElementGroupId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_UI_Element;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class AD_UI_ElementGroup_AddField extends JavaProcess
{
	@Param(parameterName = "AD_Field_ID", mandatory = true)
	private AdFieldId adFieldId;

	// @Param(parameterName = "After_UI_Element_ID", mandatory = false)
	// private UIElementId afterUIElementId;

	private AdUIElementGroupId getAdElementGroupId()
	{
		return AdUIElementGroupId.ofRepoId(getRecord_ID());
	}

	@Override
	protected String doIt()
	{
		final I_AD_Field adField = loadOutOfTrx(adFieldId, I_AD_Field.class);
		final AdTabId adTabId = AdTabId.ofRepoId(adField.getAD_Tab_ID());

		//
		//
		final AdUIElementGroupId uiElementGroupId = getAdElementGroupId();
		I_AD_UI_Element uiElement = getUIElementByTabAndFieldId(adTabId, adFieldId);
		if (uiElement == null)
		{
			uiElement = createUIElement(adField, uiElementGroupId);
		}
		else
		{
			uiElement.setAD_UI_ElementGroup_ID(uiElementGroupId.getRepoId());
		}

		final int seqNo = Services.get(IADWindowDAO.class).getUIElementNextSeqNo(uiElementGroupId);
		uiElement.setIsDisplayed(true);
		uiElement.setSeqNo(seqNo);

		// if (afterUIElementId != null)
		// {
		// 	// TODO implement
		// 	addLog("WARNING: Adding after a given element not yet implemented, so your element will be added last.");
		// }

		saveRecord(uiElement);

		return MSG_OK;
	}

	private I_AD_UI_Element getUIElementByTabAndFieldId(final AdTabId adTabId, final AdFieldId adFieldId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_UI_Element.class)
				.addEqualsFilter(I_AD_UI_Element.COLUMN_AD_Tab_ID, adTabId)
				.addEqualsFilter(I_AD_UI_Element.COLUMN_AD_Field_ID, adFieldId)
				.create()
				.firstOnly(I_AD_UI_Element.class);
	}

	private I_AD_UI_Element createUIElement(
			@NonNull final I_AD_Field adField,
			@NonNull final AdUIElementGroupId uiElementGroupId)
	{
		final I_AD_UI_Element uiElement = WindowUIElementsGenerator.createUIElementNoSave(uiElementGroupId, adField);
		uiElement.setIsDisplayed(true);
		uiElement.setSeqNo(10);
		return uiElement;
	}

}
