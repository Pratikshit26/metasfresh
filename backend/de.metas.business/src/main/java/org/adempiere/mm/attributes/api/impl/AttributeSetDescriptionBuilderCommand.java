package org.adempiere.mm.attributes.api.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

public class AttributeSetDescriptionBuilderCommand
{
	private final IAttributesBL attributesBL = Services.get(IAttributesBL.class);
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	private final ImmutableAttributeSet attributeSet;
	private final String adLanguage;

	public AttributeSetDescriptionBuilderCommand(
			@NonNull final ImmutableAttributeSet attributeSet,
			@NonNull final String adLanguage)
	{
		this.attributeSet = attributeSet;
		this.adLanguage = adLanguage;
	}

	public ITranslatableString execute()
	{
		final TranslatableStringBuilder description = TranslatableStrings.builder();

		for (final I_M_Attribute attribute : attributeSet.getAttributes())
		{
			final ITranslatableString attributeDescription = buildAttributeDescription(attribute);
			if (TranslatableStrings.isBlank(attributeDescription))
			{
				continue;
			}

			if (!description.isEmpty())
			{
				description.append(ASIDescriptionBuilderCommand.SEPARATOR);
			}

			description.append(attributeDescription);
		}

		return description.build();
	}

	private ITranslatableString buildAttributeDescription(@NonNull final I_M_Attribute attribute)
	{
		final IStringExpression descriptionPattern = extractDescriptionPatternOrNull(attribute);
		if (descriptionPattern == null)
		{
			return getAttributeDisplayValue(attribute);
		}
		else
		{
			final @NonNull AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());

			final AttributeDescriptionPatternEvalCtx ctx = AttributeDescriptionPatternEvalCtx.builder()
					.attributesRepo(attributesRepo)
					.attributesBL(attributesBL)
					.uomsRepo(uomsRepo)
					.attribute(attribute)
					.attributeValue(attributeSet.getValue(attributeCode))
					.attributeValueId(attributeSet.getAttributeValueIdOrNull(attributeCode))
					.adLanguage(adLanguage)
					.verboseDescription(true)
					.build();

			final String description = descriptionPattern.evaluate(ctx, OnVariableNotFound.Preserve);
			return TranslatableStrings.anyLanguage(description);
		}
	}

	private static IStringExpression extractDescriptionPatternOrNull(final I_M_Attribute attribute)
	{
		final String descriptionPatternStr = attribute.getDescriptionPattern();
		return !Check.isEmpty(descriptionPatternStr, true)
				? StringExpressionCompiler.instance.compileOrDefault(descriptionPatternStr, null)
				: null;
	}

	private ITranslatableString getAttributeDisplayValue(@NonNull final I_M_Attribute attribute)
	{
		final AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());
		final String attributeValueType = attribute.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType))
		{
			final String valueStr = attributeSet.getValueAsString(attributeCode);
			return ASIDescriptionBuilderCommand.formatStringValue(valueStr);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			final BigDecimal valueBD = attributeSet.getValueAsBigDecimal(attributeCode);
			if (valueBD != null)
			{
				final int displayType = attributesBL.getNumberDisplayType(attribute);
				return ASIDescriptionBuilderCommand.formatNumber(valueBD, displayType);
			}
			else
			{
				return null;
			}
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			final LocalDate valueDate = attributeSet.getValueAsLocalDate(attributeCode);
			return valueDate != null
					? ASIDescriptionBuilderCommand.formatDateValue(valueDate)
					: null;
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
		{
			final AttributeValueId attributeValueId = attributeSet.getAttributeValueIdOrNull(attributeCode);
			final AttributeListValue attributeValue = attributeValueId != null
					? attributesRepo.retrieveAttributeValueOrNull(attribute, attributeValueId)
					: null;
			if (attributeValue != null)
			{
				return attributeValue.getNameTrl();
			}
			else
			{
				final String valueStr = attributeSet.getValueAsString(attributeCode);
				return ASIDescriptionBuilderCommand.formatStringValue(valueStr);
			}
		}
		else
		{
			// Unknown attributeValueType
			final String valueStr = attributeSet.getValueAsString(attributeCode);
			return ASIDescriptionBuilderCommand.formatStringValue(valueStr);
		}
	}
}
