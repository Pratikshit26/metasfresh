package org.adempiere.ad.dao.impl;

 


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_OrderLine;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.IPOReferenceAware;

public class ReferencingPOFilterTest
{

	IPOReferenceAware referencing1;
	I_C_OrderLine ol1;

	IPOReferenceAware referencing2;
	I_C_OrderLine ol2;

	/**
	 * Sets up two POJOs with different AD_Client_IDs.
	 */
	@Before
	public void beforeTest()
	{
		Adempiere.enableUnitTestMode();

		final Properties ctx = new Properties();

		ol1 = InterfaceWrapperHelper.create(ctx, I_C_OrderLine.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(ol1);
		
		final I_AD_Note note1 = InterfaceWrapperHelper.create(ctx, I_AD_Note.class, ITrx.TRXNAME_None);
		note1.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_OrderLine.class));
		note1.setRecord_ID(ol1.getC_OrderLine_ID());
		InterfaceWrapperHelper.save(note1);
		referencing1 = InterfaceWrapperHelper.create(note1, IPOReferenceAware.class);
		
		ol2 = InterfaceWrapperHelper.create(ctx, I_C_OrderLine.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(ol2);

		final I_AD_Note note2 = InterfaceWrapperHelper.create(ctx, I_AD_Note.class, ITrx.TRXNAME_None);
		note2.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_OrderLine.class));
		note2.setRecord_ID(ol2.getC_OrderLine_ID());
		InterfaceWrapperHelper.save(note2);
		referencing2 = InterfaceWrapperHelper.create(note2, IPOReferenceAware.class);
		
		POJOWrapper.setDefaultStrictValues(false);
	}

	/**
	 * Tests for {@link ReferencingPOFilter#accept(Object)} that a given model is only accepted if it references the model that was given as constructor argument.
	 */
	@Test
	public void accept()
	{
		final ReferencingPOFilter<IPOReferenceAware> filter = new ReferencingPOFilter<IPOReferenceAware>(ol1);
		assertThat("note1 references ol1", filter.accept(referencing1), is(true));
		assertThat("note2 does not reference ol1", filter.accept(referencing2), is(false));
	}
}
