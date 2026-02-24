package de.metas.process.impl;

 


import java.sql.Timestamp;

import de.metas.common.util.time.SystemTime;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Services;

public class ADPInstanceDAOTest
{
	private PlainContextAware context;
	private ADPInstanceDAO dao;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = new PlainContextAware(Env.getCtx());
		dao = (ADPInstanceDAO)Services.get(IADPInstanceDAO.class);
	}

	@Test
	public void test_createProcessInfoParameter_Timestamp()
	{
		final Timestamp date = SystemTime.asDayTimestamp();
		final I_AD_PInstance_Para adPInstancePara = InterfaceWrapperHelper.newInstance(I_AD_PInstance_Para.class, context);
		adPInstancePara.setP_Date(date);

		final ProcessInfoParameter para = dao.createProcessInfoParameter(adPInstancePara);
		Assert.assertEquals(date, para.getParameterAsTimestamp());
	}

	/**
	 * Test having a string parameter which is null.
	 */
	@Test
	public void test_createProcessInfoParameter_NullString()
	{
		final String stringParam = null;
		final I_AD_PInstance_Para adPInstancePara = InterfaceWrapperHelper.newInstance(I_AD_PInstance_Para.class, context);
		adPInstancePara.setP_String(null);

		final ProcessInfoParameter para = dao.createProcessInfoParameter(adPInstancePara);

		// NOTE: this is a common case in our processes
		final String paramStringActual = (String)para.getParameter();

		Assert.assertEquals(stringParam, paramStringActual);
	}
}
