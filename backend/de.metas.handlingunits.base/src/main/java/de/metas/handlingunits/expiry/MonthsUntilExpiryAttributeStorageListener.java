package de.metas.handlingunits.expiry;

import java.time.LocalDate;
import java.util.OptionalInt;

import javax.annotation.PostConstruct;

import de.metas.common.util.time.SystemTime;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.springframework.stereotype.Component;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.attribute.storage.impl.AbstractHUAttributeStorage;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

@Component
public class MonthsUntilExpiryAttributeStorageListener implements IAttributeStorageListener
{
	@PostConstruct
	public void postConstruct()
	{
		Services.get(IAttributeStorageFactoryService.class).addAttributeStorageListener(this);
	}

	@Override
	public void onAttributeValueChanged(
			@NonNull final IAttributeValueContext attributeValueContext,
			@NonNull final IAttributeStorage storage,
			final IAttributeValue attributeValue,
			final Object valueOld_NOTUSED)
	{
		final AbstractHUAttributeStorage huAttributeStorage = AbstractHUAttributeStorage.castOrNull(storage);
		final boolean storageIsAboutHUs = huAttributeStorage != null;
		if (!storageIsAboutHUs)
		{
			return;
		}

		final boolean relevantAttributesArePresent = storage.hasAttribute(AttributeConstants.ATTR_MonthsUntilExpiry)
				&& storage.hasAttribute(AttributeConstants.ATTR_BestBeforeDate);
		if (!relevantAttributesArePresent)
		{
			return;
		}

		final AttributeCode attributeCode = attributeValue.getAttributeCode();
		final boolean relevantAttributeHasChanged = AttributeConstants.ATTR_BestBeforeDate.equals(attributeCode);
		if (!relevantAttributeHasChanged)
		{
			return;
		}

		final LocalDate today = SystemTime.asLocalDate();
		final OptionalInt monthsUntilExpiry = UpdateMonthsUntilExpiryCommand.computeMonthsUntilExpiry(storage, today);
		if (monthsUntilExpiry.isPresent())
		{
			storage.setValue(AttributeConstants.ATTR_MonthsUntilExpiry, monthsUntilExpiry.getAsInt());
		}
		else
		{
			storage.setValue(AttributeConstants.ATTR_MonthsUntilExpiry, null);
		}
	}
}
