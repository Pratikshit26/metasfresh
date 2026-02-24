package org.adempiere.ad.model.util;

 


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_Test;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelByIdComparatorTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testStandard_AscendingOrder()
	{
		final I_Test record1 = createRecord();
		final I_Test record2 = createRecord();
		final I_Test record3 = createRecord();
		final I_Test record4 = createRecord();
		final I_Test record5 = createRecord();

		final List<I_Test> listToSort = Arrays.asList(record1, record5, record2, record4, record3);
		Collections.sort(listToSort, ModelByIdComparator.getInstance());

		Assert.assertEquals(
				Arrays.asList(record1, record2, record3, record4, record5),
				listToSort);
	}

	@Test
	public void testStandard_ReverseOrder()
	{
		final I_Test record1 = createRecord();
		final I_Test record2 = createRecord();
		final I_Test record3 = createRecord();
		final I_Test record4 = createRecord();
		final I_Test record5 = createRecord();

		final List<I_Test> listToSort = Arrays.asList(record1, record5, record2, record4, record3);
		Collections.sort(listToSort, ModelByIdComparator.getInstance().reversed());

		Assert.assertEquals(
				Arrays.asList(record5, record4, record3, record2, record1),
				listToSort);
	}
	
	private final I_Test createRecord()
	{
		final I_Test record = InterfaceWrapperHelper.create(Env.getCtx(), I_Test.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(record);
		return record;
	}
}
