package org.adempiere.archive.spi.impl;

 


import java.util.Random;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DBArchiveStorageTest
{
	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	private DBArchiveStorage storage;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		storage = new DBArchiveStorage();
	}

	@Test
	public void test_set_getBinaryData()
	{
		final I_AD_Archive archive = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_Archive.class, ITrx.TRXNAME_None);
		final byte[] data = createTestDataBytes();
		storage.setBinaryData(archive, data);
		InterfaceWrapperHelper.save(archive);

		Assert.assertEquals("Invalid IsFileSystem flag", false, archive.isFileSystem());

		final byte[] dataActual = storage.getBinaryData(archive);
		Assert.assertArrayEquals("Invalid data", data, dataActual);
	}

	private final Random random = new Random();

	private byte[] createTestDataBytes()
	{
		final byte[] data = new byte[4096];
		random.nextBytes(data);
		return data;
	}

}
