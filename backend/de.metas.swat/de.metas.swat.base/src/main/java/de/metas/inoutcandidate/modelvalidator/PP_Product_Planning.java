package de.metas.inoutcandidate.modelvalidator;

import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.impl.ProductPlanningDAO;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Interceptor(I_PP_Product_Planning.class)
@Component
public class PP_Product_Planning
{
	private final PickingBOMService pickingBOMService;

	public PP_Product_Planning(@NonNull final PickingBOMService pickingBOMService)
	{
		this.pickingBOMService = pickingBOMService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(@NonNull final I_PP_Product_Planning record)
	{
		final ProductPlanning productPlanning = ProductPlanningDAO.fromRecord(record);

		//
		// Make sure the picking order configuration, if any, is valid
		// IMPORTANT: we have to call it after save because ProductPlanningId is required
		if (productPlanning.isPickingOrder())
		{
			pickingBOMService.extractPickingOrderConfig(productPlanning); // extract it just to validate
		}
	}

}
