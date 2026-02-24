package de.metas.adempiere.docline.sort.api.impl;

 


import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.compiere.model.I_C_DocLine_Sort;
import org.compiere.model.I_C_DocLine_Sort_Item;

import de.metas.adempiere.docline.sort.api.IDocLineSortDAO;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Comparator for {@link I_C_DocLine_Sort} header which compares product IDs against {@link I_C_DocLine_Sort_Item} seqNos.
 *
 * @author al
 */
/* package */final class DocLineSortProductIdsComparator implements Comparator<Integer>
{
	private final Map<Integer, Integer> productId2seqNo = new HashMap<>();
	private final int notFoundSeqNo = Integer.MAX_VALUE;

	public DocLineSortProductIdsComparator(final I_C_DocLine_Sort docLineSort)
	{
		super();

		Check.assumeNotNull(docLineSort, "docLineSort not null");
		final List<I_C_DocLine_Sort_Item> items = Services.get(IDocLineSortDAO.class).retrieveItems(docLineSort);

		for (final I_C_DocLine_Sort_Item item : items)
		{
			final int productId = item.getM_Product_ID();
			final int seqNo = item.getSeqNo();
			productId2seqNo.put(productId, seqNo);
		}
	}

	@Override
	public int compare(final Integer productId1, final Integer productId2)
	{
		final int seqNo1 = getSeqNo(productId1);
		final int seqNo2 = getSeqNo(productId2);
		return seqNo1 - seqNo2;
	}

	private int getSeqNo(final Integer productId)
	{
		if (productId == null || productId <= 0)
		{
			return notFoundSeqNo;
		}

		final Integer seqNo = productId2seqNo.get(productId);
		if (seqNo == null)
		{
			return notFoundSeqNo;
		}

		return seqNo;
	}
}
