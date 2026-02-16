/**
 *
 */
package de.metas.impexp.processing;

import de.metas.impexp.DataImportRunId;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class DBFunctionHelper
{
	private static final transient Logger logger = LogManager.getLogger(DBFunctionHelper.class);

	/**
	 * Used to call import DB functions using c_dataimport_id and record_id as parameters
	 */
	public static void doDBFunctionCall(
			@NonNull final DBFunction function,
			@Nullable final DataImportConfigId dataImportConfigId,
			final int recordId)
	{
		final String sql = new StringBuilder()
				.append("SELECT ")
				.append(function.getSchema())
				.append(".")
				.append(function.getName())
				.append("(?,?)")
				.toString();

		final Object[] sqlParams = new Object[] {
				DataImportConfigId.toRepoId(dataImportConfigId),
				recordId };

		DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited, sql, sqlParams);
		logger.debug("\nExecuted {} with params: {}", function, sqlParams);
	}

	/**
	 * Used to call import DB functions using c_dataimport_run_id
	 */
	public static void doDBFunctionCall(
			@NonNull final DBFunction function,
			@Nullable final DataImportRunId dataImportRunId)
	{
		final String sql = new StringBuilder()
				.append("SELECT ")
				.append(function.getSchema())
				.append(".")
				.append(function.getName())
				.append("(?)")
				.toString();

		final Object[] sqlParams = new Object[] {
				DataImportRunId.toRepoId(dataImportRunId) };

		DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited, sql, sqlParams);
		logger.debug("\nExecuted {} with params: {}", function, sqlParams);
	}
}
