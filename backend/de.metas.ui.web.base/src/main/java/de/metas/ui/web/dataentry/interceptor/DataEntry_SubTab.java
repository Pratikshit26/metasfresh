package de.metas.ui.web.dataentry.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.dataentry.model.I_DataEntry_SubTab;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Component
@Interceptor(I_DataEntry_SubTab.class)
public class DataEntry_SubTab
{
	private final DataEntryInterceptorUtil dataEntryInterceptorUtil;

	public DataEntry_SubTab(
			@NonNull final DataEntryInterceptorUtil dataEntryInterceptorUtil)
	{
		this.dataEntryInterceptorUtil = dataEntryInterceptorUtil;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void invalidateDocumentDescriptorCache(@NonNull final I_DataEntry_SubTab dataEntrySubTabRecord)
	{
		dataEntryInterceptorUtil.resetCacheFor(dataEntrySubTabRecord);
	}
}
