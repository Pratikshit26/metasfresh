package de.metas.translation.interceptor;

import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Element_Trl;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Interceptor(I_AD_Element_Trl.class)
@Component
public class AD_Element_Trl
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void beforeElementTrlChanged(final I_AD_Element_Trl adElementTrl)
	{
		assertNotChangingRegularAndCustomizationFields(adElementTrl);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void afterElementTrlChanged(final I_AD_Element_Trl adElementTrl)
	{
		final AdElementId adElementId = AdElementId.ofRepoId(adElementTrl.getAD_Element_ID());
		final String adLanguage = adElementTrl.getAD_Language();

		final IElementTranslationBL elementTranslationBL = Services.get(IElementTranslationBL.class);
		elementTranslationBL.updateElementFromElementTrlIfBaseLanguage(adElementId, adLanguage);
		elementTranslationBL.propagateElementTrls(adElementId, adLanguage);
	}

	private static void assertNotChangingRegularAndCustomizationFields(final I_AD_Element_Trl adElementTrl)
	{
		final Set<String> changedRegularFields = new HashSet<>();
		final Set<String> changedCustomizationFields = new HashSet<>();

		if (isValueChanged(adElementTrl, I_AD_Element_Trl.COLUMNNAME_IsUseCustomization))
		{
			changedCustomizationFields.add(I_AD_Element_Trl.COLUMNNAME_IsUseCustomization);
		}

		//
		for (final ADElementTranslatedColumn field : ADElementTranslatedColumn.values())
		{
			if (field.hasCustomizedField() && isValueChanged(adElementTrl, field.getCustomizationColumnName()))
			{
				changedCustomizationFields.add(field.getCustomizationColumnName());
			}

			if (isValueChanged(adElementTrl, field.getColumnName()))
			{
				changedRegularFields.add(field.getColumnName());
			}
		}

		//
		if (!changedRegularFields.isEmpty() && !changedCustomizationFields.isEmpty())
		{
			throw new AdempiereException("Changing regular fields and customization fields is not allowed."
					+ "\n Regular fields changed: " + changedRegularFields
					+ "\n Customization fields changed: " + changedCustomizationFields)
					.markAsUserValidationError();
		}
	}

	private static boolean isValueChanged(final I_AD_Element_Trl adElementTrl, final String columnName)
	{
		return InterfaceWrapperHelper.isValueChanged(adElementTrl, columnName);
	}

	private enum ADElementTranslatedColumn
	{
		Name(I_AD_Element_Trl.COLUMNNAME_Name, I_AD_Element_Trl.COLUMNNAME_Name_Customized), //
		Description(I_AD_Element_Trl.COLUMNNAME_Description, I_AD_Element_Trl.COLUMNNAME_Description_Customized), //
		Help(I_AD_Element_Trl.COLUMNNAME_Help, I_AD_Element_Trl.COLUMNNAME_Help_Customized), //
		PrintName(I_AD_Element.COLUMNNAME_PrintName), //
		PO_Description(I_AD_Element.COLUMNNAME_PO_Description), //
		PO_Help(I_AD_Element.COLUMNNAME_PO_Help), //
		PO_Name(I_AD_Element.COLUMNNAME_PO_Name), //
		PO_PrintName(I_AD_Element.COLUMNNAME_PO_PrintName), //
		CommitWarning(I_AD_Element.COLUMNNAME_CommitWarning), //
		WebuiNameBrowse(I_AD_Element.COLUMNNAME_WEBUI_NameBrowse), //
		WebuiNameNew(I_AD_Element.COLUMNNAME_WEBUI_NameNew), //
		WebuiNameNewBreadcrumb(I_AD_Element.COLUMNNAME_WEBUI_NameNewBreadcrumb) //
		;

		@Getter
		private final String columnName;
		@Getter
		private final String customizationColumnName;

		ADElementTranslatedColumn(@NonNull final String columnName)
		{
			this.columnName = columnName;
			this.customizationColumnName = null;
		}

		ADElementTranslatedColumn(
				@NonNull final String columnName,
				@Nullable final String customizationColumnName)
		{
			this.columnName = columnName;
			this.customizationColumnName = customizationColumnName;
		}

		public boolean hasCustomizedField()
		{
			return getCustomizationColumnName() != null;
		}
	}

}
