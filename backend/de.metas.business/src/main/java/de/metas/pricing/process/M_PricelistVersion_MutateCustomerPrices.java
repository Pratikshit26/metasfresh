package de.metas.pricing.process;

import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.process.JavaProcess;
import de.metas.user.UserId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

public class M_PricelistVersion_MutateCustomerPrices extends JavaProcess
{
	final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(getRecord_ID());

		priceListsRepo.mutateCustomerPrices(priceListVersionId, UserId.ofRepoId(getAD_User_ID()));

		return MSG_OK;
	}

}
