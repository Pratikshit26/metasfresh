package de.metas.adempiere.addon;

 

import java.util.Properties;

public interface IAddonStarter {

	void startAddons();
	
	Properties getAddonProperties();
}
