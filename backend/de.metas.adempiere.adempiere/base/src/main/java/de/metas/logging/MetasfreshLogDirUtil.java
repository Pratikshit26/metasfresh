package de.metas.logging;



/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */
import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;

import org.compiere.util.Ini;

import lombok.experimental.UtilityClass;


@UtilityClass
public class MetasfreshLogDirUtil
{
	public String getLogDir()
	{
		return coalesceSuppliers(
				() -> System.getProperty(LoggingConstants.SYSTEM_PROP_LogDir),
				() -> System.getenv(LoggingConstants.ENV_VAR_LogDir),
				() -> Ini.getMetasfreshHome());
	}
}
