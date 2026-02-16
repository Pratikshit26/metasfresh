package de.metas.shipment.repo;

import java.util.Collection;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Shipment_Declaration_Config;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;

import de.metas.cache.CCache;
import de.metas.document.DocTypeId;
import de.metas.shipment.ShipmentDeclarationConfig;
import de.metas.shipment.ShipmentDeclarationConfigId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */
@Repository
public class ShipmentDeclarationConfigRepository
{
	private final CCache<Integer, ImmutableMap<ShipmentDeclarationConfigId, ShipmentDeclarationConfig>> //
	cache = CCache.<Integer, ImmutableMap<ShipmentDeclarationConfigId, ShipmentDeclarationConfig>> builder()
			.tableName(I_M_Shipment_Declaration_Config.Table_Name)
			.build();

	public Collection<ShipmentDeclarationConfig> getAll()
	{
		return getAllIndexedById().values();
	}

	public ShipmentDeclarationConfig getConfigById(@NonNull final ShipmentDeclarationConfigId configId)
	{
		final ShipmentDeclarationConfig config = getAllIndexedById().get(configId);
		if (config == null)
		{
			throw new AdempiereException("@NotFound@ " + configId);
		}
		return config;
	}

	private ImmutableMap<ShipmentDeclarationConfigId, ShipmentDeclarationConfig> getAllIndexedById()
	{
		return cache.getOrLoad(0, () -> retrieveAll());
	}

	private ImmutableMap<ShipmentDeclarationConfigId, ShipmentDeclarationConfig> retrieveAll()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Shipment_Declaration_Config.class)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(record -> toShipmentDeclarationConfig(record))
				.collect(GuavaCollectors.toImmutableMapByKey(ShipmentDeclarationConfig::getId));
	}

	private static ShipmentDeclarationConfig toShipmentDeclarationConfig(final I_M_Shipment_Declaration_Config record)
	{
		return ShipmentDeclarationConfig.builder()
				.id(ShipmentDeclarationConfigId.ofRepoId(record.getM_Shipment_Declaration_Config_ID()))
				.docTypeId(DocTypeId.ofRepoId(record.getC_DocType_ID()))
				.docTypeCorrectionId(DocTypeId.ofRepoIdOrNull(record.getC_DocType_Correction_ID()))
				.documentLinesNumber(record.getDocumentLinesNumber())
				.name(record.getName())
				.build();
	}
}
