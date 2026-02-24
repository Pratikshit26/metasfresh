package org.adempiere.server.rpl.api.impl;

 


import static org.adempiere.server.rpl.api.impl.ReplicationHelper.setReplicationCtx;

import java.util.Properties;

import org.adempiere.server.rpl.exceptions.ReplicationException;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Test;

public class ImportHelperTest
{
	@Test
	public void test_setReplicationCtx() throws Exception
	{
		final Properties initialCtx = Env.newTemporaryCtx();
		Env.setContext(initialCtx, Env.CTXNAME_AD_Client_ID, 12345);

		final Properties ctx = Env.deriveCtx(initialCtx);
		Assert.assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 12345);

		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, 1, false); // overwrite = false
		Assert.assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 1);

		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, 1, true); // overwrite = true, but same value
		Assert.assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 1);

		// Test remove
		// We expect that AD_Client_ID to be set to ZERO
		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, null, true); // overwrite = true
		Assert.assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 0);
	}

	@Test(expected = ReplicationException.class)
	public void test_setReplicationCtx_overwrite_fails() throws Exception
	{
		final Properties initialCtx = new Properties();
		Env.setContext(initialCtx, Env.CTXNAME_AD_Client_ID, 12345);

		final Properties ctx = new Properties(initialCtx);

		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, 1, false); // overwrite = false
		Assert.assertEquals(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Client_ID), 1);

		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, 2, false); // overwrite = false, not same value, shall throw exception
	}
}
