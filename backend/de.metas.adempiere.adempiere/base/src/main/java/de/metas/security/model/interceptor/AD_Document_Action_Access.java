/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.security.model.interceptor;

import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Document_Action_Access;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.ModelValidator;

@Interceptor(I_AD_Document_Action_Access.class)
public class AD_Document_Action_Access
{
	public static final AD_Document_Action_Access instance = new AD_Document_Action_Access();

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void afterNewChangedOrDeleted(final I_AD_Document_Action_Access documentActionAccess, final ModelChangeType changeType)
	{
		final ModelCacheInvalidationService modelCacheInvalidationService = ModelCacheInvalidationService.get();
		//Calling with CHANGE as NEW only resets the local cache.
		modelCacheInvalidationService.invalidate(
				CacheInvalidateMultiRequest.allRecordsForTable(I_AD_Ref_List.Table_Name),
				ModelCacheInvalidationTiming.ofModelChangeType(ModelChangeType.AFTER_CHANGE, ModelCacheInvalidationTiming.AFTER_CHANGE));
	}
}
