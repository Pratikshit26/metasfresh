package org.adempiere.archive.spi.impl;

 


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

import lombok.NonNull;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Archive;

import de.metas.util.Services;

/**
 * Abstract implementation of {@link IArchiveStorage}. At this level there is no reference to a particular storage support.
 * 
 * @author tsa
 * 
 */
public abstract class AbstractArchiveStorage implements IArchiveStorage
{
	@Override
	public void init(@NonNull final ClientId adClientId)
	{
		// nothing at this level
	}

	@Override
	public I_AD_Archive newArchive(final Properties ctx, final String trxName)
	{
		return InterfaceWrapperHelper.create(ctx, I_AD_Archive.class, trxName);
	}

	@Override
	public InputStream getBinaryDataAsStream(final I_AD_Archive archive)
	{
		final byte[] inflatedData = getBinaryData(archive);
		if (inflatedData == null)
		{
			return null;
		}
		return new ByteArrayInputStream(inflatedData);
	}
}
