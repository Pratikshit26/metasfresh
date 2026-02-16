package de.metas.inoutcandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Interceptor(I_C_UOM_Conversion.class)
@Component
public class C_UOM_Conversion
{
	private static final String SYSCONFIG_SHIPMENT_SCHEDULE_CATCH_UOM_ID_UPDATE_DELAY_MS = "de.metas.inoutcandidate.M_ShipmentSchedule.CatchUOM_ID_update_delay_ms";
	private static final int SYSCONFIG_SHIPMENT_SCHEDULE_CATCH_UOM_ID_UPDATE_DELAY_MS_DEFAULT = 0;

	@ModelChange(//
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE }, //
			ifColumnsChanged = { I_C_UOM_Conversion.COLUMNNAME_IsCatchUOMForProduct, I_C_UOM_Conversion.COLUMNNAME_C_UOM_To_ID, I_C_UOM_Conversion.COLUMNNAME_IsActive })
	public void invalidSchedsAfterCatchUomChange(@NonNull final I_C_UOM_Conversion uomConversionRecord)
	{
		if (uomConversionRecord.getM_Product_ID() <= 0)
		{
			return; // nothing to do
		}
		final ProductId productId = ProductId.ofRepoId(uomConversionRecord.getM_Product_ID());

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		final int delayMs = sysConfigBL
				.getIntValue(SYSCONFIG_SHIPMENT_SCHEDULE_CATCH_UOM_ID_UPDATE_DELAY_MS,
						SYSCONFIG_SHIPMENT_SCHEDULE_CATCH_UOM_ID_UPDATE_DELAY_MS_DEFAULT,
						uomConversionRecord.getAD_Client_ID(),
						uomConversionRecord.getAD_Org_ID());
		shipmentScheduleBL.updateCatchUoms(productId, delayMs);
	}
}
