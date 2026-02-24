package de.metas.testsupport;

 


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.document.engine.DocStatus;

public class AbstractTestSupportTests
{
	/**
	 * ts-2014-01-12: Currently doesn't work. I pinged teo
	 */
	@Test
	@Ignore 
	public void testOrder()
	{
		AdempiereTestHelper.get().staticInit();
		AdempiereTestHelper.get().init();
		
		final AbstractTestSupport testee = new AbstractTestSupport();
		final I_C_Order order1 = testee.order("1");
		
		// call the same method again, should be the same
		assertThat(testee.order("1"), is(order1)); 
		assertThat(testee.order("1"), sameInstance(order1)); // fails as of now
		
		// this is to clarify the practical problem we have with the order being not the same
		testee.order("1").setDocStatus(DocStatus.InProgress.getCode());
		InterfaceWrapperHelper.save(testee.order("1"));
		assertThat(testee.order("1").getDocStatus(), equalTo(DocStatus.InProgress.getCode()));
	}
}
