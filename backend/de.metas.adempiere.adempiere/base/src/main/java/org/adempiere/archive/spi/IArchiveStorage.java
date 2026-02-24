package org.adempiere.archive.spi;

 


import java.io.InputStream;
import java.util.Properties;

import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Archive;

import javax.annotation.Nullable;

/**
 * Archive Storage (e.g. database, filesystem etc)
 * 
 * @author tsa
 * 
 */
public interface IArchiveStorage
{
	/**
	 * Initialize storage handler.
	 * 
	 * NOTE: don't call it directly, it's called by API
	 */
	void init(ClientId adClientId);

	I_AD_Archive newArchive(final Properties ctx, final String trxName);

	@Nullable
	byte[] getBinaryData(I_AD_Archive archive);

	@Nullable
	InputStream getBinaryDataAsStream(I_AD_Archive archive);

	void setBinaryData(I_AD_Archive archive, byte[] data);
}
