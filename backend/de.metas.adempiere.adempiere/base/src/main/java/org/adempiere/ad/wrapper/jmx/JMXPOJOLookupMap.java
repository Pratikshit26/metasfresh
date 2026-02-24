package org.adempiere.ad.wrapper.jmx;

 


import java.lang.ref.WeakReference;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;

import de.metas.util.Check;

public class JMXPOJOLookupMap implements JMXPOJOLookupMapMBean
{
	private final WeakReference<POJOLookupMap> databaseRef;
	private final String jmxName;

	public JMXPOJOLookupMap(final POJOLookupMap database)
	{
		super();
		Check.assumeNotNull(database, "database not null");
		this.databaseRef = new WeakReference<POJOLookupMap>(database);

		this.jmxName = POJOLookupMap.class.getName() + ":type=" + database.getDatabaseName();
	}

	private POJOLookupMap getDatabase()
	{
		final POJOLookupMap database = databaseRef.get();
		if (database == null)
		{
			throw new AdempiereException("Database expired");
		}

		return database;
	}

	@Override
	public void dump()
	{
		final POJOLookupMap database = getDatabase();
		database.dumpStatus();
	}

	public String getJMXName()
	{
		return jmxName;
	}
}
