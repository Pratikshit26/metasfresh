package org.adempiere.ad.callout.api.impl;

 

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.callout.api.ICalloutFactory;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.callout.spi.impl.ProgramaticCalloutProvider;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.util.Services;

public class ProgramaticCalloutProviderTest
{
	private ProgramaticCalloutProvider provider;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		provider = (ProgramaticCalloutProvider)Services.get(IProgramaticCalloutProvider.class);
	}

	@Test
	public void test_StandardCase()
	{
		final MockedCalloutField field = MockedCalloutField.createNewField("MyTableName", "MyColumnName");

		final MockedCalloutInstance calloutInstance = new MockedCalloutInstance();
		provider.registerCallout("MyTableName", "MyColumnName", calloutInstance);

		final List<ICalloutInstance> calloutInstances = provider.getCallouts(Env.getCtx(), field.getTableName())
				.getColumnCallouts(field.getColumnName());
		Assert.assertEquals("Invalid callout instances retrieved",
				Collections.singletonList(calloutInstance),
				calloutInstances);
	}

	@Test
	public void test_StandardCase_CalloutNotFound()
	{
		final MockedCalloutField field = MockedCalloutField.createNewField("MyTableName", "MyColumnName" + "_NOT_FOUND");

		final MockedCalloutInstance calloutInstance = new MockedCalloutInstance();
		MockedCalloutField.createNewField("MyTableName", "MyColumnName"); // calling it just to have the AD_Table and AD_Column records
		provider.registerCallout("MyTableName", "MyColumnName", calloutInstance);

		final List<ICalloutInstance> calloutInstances = provider.getCallouts(Env.getCtx(), field.getTableName())
				.getColumnCallouts(field.getColumnName());
		Assert.assertEquals("Invalid callout instances retrieved",
				Collections.emptyList(),
				calloutInstances);
	}

	@Test
	public void test_CalloutNotRegisteredTwice()
	{
		final String calloutInstanceId = "MockedCalloutInstance-1";

		MockedCalloutField.createNewField("MyTableName", "MyColumnName"); // calling it just to have the AD_Table and AD_Column records

		final MockedCalloutInstance calloutInstance1 = new MockedCalloutInstance(calloutInstanceId);
		Assert.assertTrue("Callout " + calloutInstance1 + " shall be registered",
				provider.registerCallout("MyTableName", "MyColumnName", calloutInstance1));

		final MockedCalloutInstance calloutInstance2 = new MockedCalloutInstance(calloutInstanceId);
		Assert.assertFalse("Callout " + calloutInstance2 + " shall not be registered because it has the same ID as " + calloutInstance1,
				provider.registerCallout("MyTableName", "MyColumnName", calloutInstance2));

	}

	@Test
	public void test_integration_RegisterTo_CalloutFactory()
	{
		final CalloutFactory calloutFactory = (CalloutFactory)Services.get(ICalloutFactory.class);
		Assert.assertFalse("Provider " + provider + " shall not be registered at this moment",
				calloutFactory.getCalloutProvidersList().contains(provider));

		MockedCalloutField.createNewField("MyTableName", "MyColumnName"); // calling it just to have the AD_Table and AD_Column records

		// Register some column callouts and expected to have the provider registered to factory
		for (int i = 1; i <= 100; i++)
		{
			final MockedCalloutInstance calloutInstance = new MockedCalloutInstance();
			provider.registerCallout("MyTableName", "MyColumnName", calloutInstance);
			Assert.assertTrue("Provider " + provider + " shall not be registered at this moment",
					calloutFactory.getCalloutProvidersList().contains(provider));
		}

		// Make sure provider is registered only once
		final List<ICalloutProvider> programaticProviders = new ArrayList<ICalloutProvider>();
		for (final ICalloutProvider p : calloutFactory.getCalloutProvidersList())
		{
			if (p instanceof IProgramaticCalloutProvider)
			{
				programaticProviders.add(p);
			}
		}
		Assert.assertEquals("Provider " + provider + " shall be registered only once",
				Collections.singletonList(provider),
				programaticProviders);
	}
}
