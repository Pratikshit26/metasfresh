package org.adempiere.process;

 


import org.compiere.Adempiere;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import ch.qos.logback.classic.Level;

/**
 * @author al
 */
public class RunDatabaseMigration
{
	private static Logger logger = LogManager.getLogger(RunDatabaseMigration.class);

	public static void main(String[] args)
	{
		Adempiere.startupEnvironment(false);
		LogManager.setLevel(Level.DEBUG);

		logger.info("Running Database Migration...");

		if (!DB.isConnected())
		{
			logger.info("No DB Connection");
			System.exit(1);
		}

		// Migration Loader will be called via shell script.
		MigrationLoader loader = new MigrationLoader();
		loader.load(Env.getCtx());
	}
}
