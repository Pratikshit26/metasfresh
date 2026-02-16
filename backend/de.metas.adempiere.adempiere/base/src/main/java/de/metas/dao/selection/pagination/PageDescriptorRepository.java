package de.metas.dao.selection.pagination;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.dao.selection.model.I_T_Query_Selection_Pagination;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Repository
public class PageDescriptorRepository
{
	public PageDescriptor getBy(@NonNull final String completePageId)
	{
		final PageIdentifier pageIdentifier = PageIdentifier.ofCombinedString(completePageId);

		final I_T_Query_Selection_Pagination pageDescriptorRecord = Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_T_Query_Selection_Pagination.class)
				.addEqualsFilter(I_T_Query_Selection_Pagination.COLUMN_UUID, pageIdentifier.getSelectionUid())
				.addEqualsFilter(I_T_Query_Selection_Pagination.COLUMN_Page_Identifier, pageIdentifier.getPageUid())
				.create()
				.firstOnly(I_T_Query_Selection_Pagination.class);
		if (pageDescriptorRecord == null)
		{
			throw new PageNotFoundException(completePageId);
		}
		return PageDescriptor.builder()
				.selectionUid(pageDescriptorRecord.getUUID())
				.selectionTime(TimeUtil.asInstant(pageDescriptorRecord.getResult_Time()))
				.totalSize(pageDescriptorRecord.getTotal_Size())
				.pageSize(pageDescriptorRecord.getPage_Size())
				.offset(pageDescriptorRecord.getPage_Offset())
				.pageUid(pageDescriptorRecord.getPage_Identifier())
				.build();
	}

	public void save(@NonNull final PageDescriptor pageDescriptor)
	{
		final PageIdentifier pageIdentifier = pageDescriptor.getPageIdentifier();

		final I_T_Query_Selection_Pagination record = newInstance(I_T_Query_Selection_Pagination.class);
		record.setUUID(pageIdentifier.getSelectionUid());
		record.setResult_Time(TimeUtil.asTimestamp(pageDescriptor.getSelectionTime()));
		record.setTotal_Size(pageDescriptor.getTotalSize());
		record.setPage_Size(pageDescriptor.getPageSize());
		record.setPage_Offset(pageDescriptor.getOffset());
		record.setPage_Identifier(pageIdentifier.getPageUid());
		saveRecord(record);
	}
}
