package de.metas.bpartner;

import de.metas.bpartner.model.interceptor.C_BPartner;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.security.permissions.record_access.RecordAccessConfigService;
import de.metas.security.permissions.record_access.RecordAccessRepository;
import de.metas.security.permissions.record_access.RecordAccessService;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class BPartnerSalesRepTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(RecordAccessConfigService.class, new RecordAccessConfigService(Optional.empty()));
		SpringContextHolder.registerJUnitBean(RecordAccessService.class, new RecordAccessService(new RecordAccessRepository(),
																								 SpringContextHolder.instance.getBean(RecordAccessConfigService.class),
																								 new UserGroupRepository()));

		SpringContextHolder.registerJUnitBean(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_BPartner());
	}

	@Test
	public void testSalesRepSameIdAsBPartner()
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setC_BPartner_ID(11111);
		bpartner.setC_BPartner_SalesRep_ID(11111);
		Assertions.assertThrows(AdempiereException.class, () -> save(bpartner));
	}

}
