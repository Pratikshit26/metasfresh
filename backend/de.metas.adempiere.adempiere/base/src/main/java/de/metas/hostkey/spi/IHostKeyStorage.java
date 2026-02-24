package de.metas.hostkey.spi;

 

import javax.annotation.Nullable;

/**
 * HostKey Storage
 * 
 * @author tsa
 * 
 */
public interface IHostKeyStorage
{
	void setHostKey(String hostkey);

	@Nullable
	String getHostKey();
}
