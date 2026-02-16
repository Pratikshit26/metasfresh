package de.metas.security.permissions.record_access;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Role_Record_Access_Config;
import org.compiere.model.X_AD_Role_Record_Access_Config;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.security.RoleId;
import de.metas.security.permissions.record_access.handlers.CompositeRecordAccessHandler;
import de.metas.security.permissions.record_access.handlers.ManualRecordAccessHandler;
import de.metas.security.permissions.record_access.handlers.RecordAccessHandler;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Service
public class RecordAccessConfigService
{
	private static final Logger logger = LogManager.getLogger(RecordAccessConfigService.class);

	private final CCache<RoleId, RecordAccessConfig> configs = CCache.<RoleId, RecordAccessConfig> builder()
			.tableName(I_AD_Role_Record_Access_Config.Table_Name)
			.build();

	private final CCache<Integer, ImmutableSet<RecordAccessFeature>> allEnabledFeaturesCache = CCache.<Integer, ImmutableSet<RecordAccessFeature>> builder()
			.tableName(I_AD_Role_Record_Access_Config.Table_Name)
			.build();

	private CompositeRecordAccessHandler allHandlers;

	public RecordAccessConfigService(
			@NonNull final Optional<List<RecordAccessHandler>> handlers)
	{
		this.allHandlers = CompositeRecordAccessHandler.of(handlers);
		logger.info("All handlers: {}", handlers);
	}

	public CompositeRecordAccessHandler getAllHandlers()
	{
		return allHandlers;
	}

	@VisibleForTesting
	public void setAllHandlers(@NonNull final List<RecordAccessHandler> handlers)
	{
		if (!Adempiere.isUnitTestMode())
		{
			throw new AdempiereException("Not JUnit testing mode");
		}

		this.allHandlers = CompositeRecordAccessHandler.of(handlers);
	}

	public RecordAccessConfig getByRoleId(@NonNull final RoleId roleId)
	{
		return configs.getOrLoad(roleId, this::retrieveByRoleId);
	}

	private RecordAccessConfig retrieveByRoleId(@NonNull final RoleId roleId)
	{
		final List<I_AD_Role_Record_Access_Config> records = queryActiveConfigs()
				.addEqualsFilter(I_AD_Role_Record_Access_Config.COLUMN_AD_Role_ID, roleId)
				.create()
				.list();

		if (records.isEmpty())
		{
			return RecordAccessConfig.EMPTY;
		}

		final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);

		//
		//
		final Set<String> manualHandledTableNames = new HashSet<>();
		final Set<RecordAccessFeature> features = new HashSet<>();
		for (final I_AD_Role_Record_Access_Config record : records)
		{
			final String type = record.getType();
			if (X_AD_Role_Record_Access_Config.TYPE_Table.contentEquals(type))
			{
				final int adTableId = record.getAD_Table_ID();
				if (adTableId <= 0)
				{
					logger.warn("Invalid config record because type=Table but AD_Table_ID is not set: {}", record);
					continue;
				}

				final String tableName = adTablesRepo.retrieveTableName(adTableId);
				if (tableName == null)
				{
					logger.warn("No table name found for AD_Table_ID={}", adTableId);
					continue;
				}

				manualHandledTableNames.add(tableName);
			}
			else
			{
				features.add(RecordAccessFeature.of(type));
			}
		}

		//
		//
		final Set<RecordAccessHandler> handlers = new HashSet<>();
		handlers.addAll(allHandlers.handlingFeatureSet(features));
		if (!manualHandledTableNames.isEmpty())
		{
			handlers.add(ManualRecordAccessHandler.ofTableNames(manualHandledTableNames));
		}

		//
		//
		if (handlers.isEmpty())
		{
			return RecordAccessConfig.EMPTY;
		}

		return RecordAccessConfig.builder()
				.handlers(CompositeRecordAccessHandler.of(handlers))
				.build();
	}

	private IQueryBuilder<I_AD_Role_Record_Access_Config> queryActiveConfigs()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Role_Record_Access_Config.class)
				.addOnlyActiveRecordsFilter();
	}

	public boolean isFeatureEnabled(@NonNull final RecordAccessFeature feature)
	{
		return getAllEnabledFeatures()
				.contains(feature);
	}

	private ImmutableSet<RecordAccessFeature> getAllEnabledFeatures()
	{
		return allEnabledFeaturesCache.getOrLoad(0, this::retrieveAllEnabledFeatures);
	}

	private ImmutableSet<RecordAccessFeature> retrieveAllEnabledFeatures()
	{
		final ImmutableSet<RecordAccessFeature> allEnabledFeatures = queryActiveConfigs()
				.create()
				.listDistinct(I_AD_Role_Record_Access_Config.COLUMNNAME_Type, String.class)
				.stream()
				.map(RecordAccessFeature::of)
				.collect(ImmutableSet.toImmutableSet());
		logger.info("Loaded all enabled features: {}", allEnabledFeatures);

		return allEnabledFeatures;
	}
}
