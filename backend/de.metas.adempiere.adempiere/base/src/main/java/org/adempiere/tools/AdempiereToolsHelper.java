package org.adempiere.tools;

 


import org.compiere.Adempiere.RunMode;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.Env;

import de.metas.adempiere.addon.impl.AddonStarter;
import de.metas.event.EventBusConfig;
import de.metas.logging.LogManager;

/**
 * Helper class used by tools build on top of ADempiere, which require only a minimal set of functionalities.
 * 
 * @author tsa
 *
 */
public final class AdempiereToolsHelper
{
	public static final transient AdempiereToolsHelper instance = new AdempiereToolsHelper();
	
	public static final AdempiereToolsHelper getInstance()
	{
		return instance;
	}
	
	/**
	 * starts up in backend mode. Suitable for the little server-site-tools that we run during rollout
	 */
	public void startupMinimal()
	{
		startupMinimal(RunMode.BACKEND);
	}
	
	/**
	 * Minimal adempiere system startup.
	 */
	public void startupMinimal(RunMode runMode)
	{
		// Disable distributed events because we don't want to broadcast events to network.
		EventBusConfig.disableDistributedEvents();
		
		AddonStarter.warnIfPropertiesFileMissing = false; // don't warn because it we know it's missing.
		
		//
		// Adempiere system shall be started with a minimal set of entity types.
		// In particular, we don't want async, btw, because it doesn't stop when this process is already finished
		ModelValidationEngine.setInitEntityTypes(ModelValidationEngine.INITENTITYTYPE_Minimal);
		ModelValidationEngine.setFailOnMissingModelInteceptors(false);
		
		//
		// Initialize logging
		LogManager.initialize(true); // running it here to make sure we get the client side config

		//
		// Start Adempiere system
		Env.getSingleAdempiereInstance(null).startup(runMode);
		System.out.println("ADempiere system started in tools minimal mode.");
	}
	
	private AdempiereToolsHelper()
	{
		super();
	}
}
