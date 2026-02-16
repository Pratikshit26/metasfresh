/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.attachments.listener;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.i18n.AdMessageId;
import de.metas.javaclasses.JavaClassId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_Table_AttachmentListener;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

@Repository
public class TableAttachmentListenerRepository
{
	private final CCache<AdTableId, ImmutableList<AttachmentListenerSettings>> cache = CCache.<AdTableId, ImmutableList<AttachmentListenerSettings>> builder()
			.cacheName("listenersByAdTableId")
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(100)
			.tableName(I_AD_Table_AttachmentListener.Table_Name)
			.build();

	public ImmutableList<AttachmentListenerSettings> getById(@NonNull final AdTableId adTableId)
	{
		return cache.getOrLoad(adTableId, this::retrieveAttachmentListenerSettings);
	}

	/**
	 * Queries {@link I_AD_Table_AttachmentListener} for listeners linked to the given {@link AdTableId}.
	 *
	 * @param adTableId DB identifier of the table.
	 * @return list of {@link AttachmentListenerSettings} ordered by {@link I_AD_Table_AttachmentListener#getSeqNo()}.
	 */
	private ImmutableList<AttachmentListenerSettings> retrieveAttachmentListenerSettings(final AdTableId adTableId )
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Table_AttachmentListener.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Table_AttachmentListener.COLUMNNAME_AD_Table_ID, adTableId.getRepoId())
				.orderBy(I_AD_Table_AttachmentListener.COLUMNNAME_SeqNo)
				.create()
				.list()
				.stream()
				.map(this::buildAttachmentListenerSettings)
				.collect(ImmutableList.toImmutableList());
	}

	private AttachmentListenerSettings buildAttachmentListenerSettings( final I_AD_Table_AttachmentListener record )
	{
		return AttachmentListenerSettings.builder()
				.isSendNotification(record.isSendNotification())
				.listenerJavaClassId(JavaClassId.ofRepoId(record.getAD_JavaClass_ID()))
				.adMessageId(AdMessageId.ofRepoIdOrNull(record.getAD_Message_ID()))
				.build();
	}
}
