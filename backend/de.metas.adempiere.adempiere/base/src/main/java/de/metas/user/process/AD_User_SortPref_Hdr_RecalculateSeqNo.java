package de.metas.user.process;

 


import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User_SortPref_Hdr;
import org.compiere.model.I_AD_User_SortPref_Line;
import org.compiere.model.I_AD_User_SortPref_Line_Product;
import org.compiere.util.TrxRunnable;

import de.metas.process.ProcessInfo;
import de.metas.user.api.IUserSortPrefDAO;
import de.metas.util.Services;
import de.metas.process.JavaProcess;

/**
 * Recalculate for {@link I_AD_User_SortPref_Line_Product}
 *
 * @author al
 */
public class AD_User_SortPref_Hdr_RecalculateSeqNo extends JavaProcess
{
	@Override
	protected void prepare()
	{
		// nothing to do
	}

	/**
	 * Recalculates SeqNo in AD_User_SortPref_Line in 10-steps for the current AD_User_SortPref_Hdr, keeping the order they already had
	 */
	@Override
	protected String doIt() throws Exception
	{
		//
		// Services
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final IUserSortPrefDAO userSortPrefDAO = Services.get(IUserSortPrefDAO.class);

		final Properties ctx = getCtx();

		trxManager.runInNewTrx(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				int seqNumber = 10;

				final ProcessInfo processInfo = getProcessInfo();
				final int recordId = processInfo.getRecord_ID();
				final I_AD_User_SortPref_Hdr hdr = InterfaceWrapperHelper.create(ctx, recordId, I_AD_User_SortPref_Hdr.class, localTrxName);

				final Iterator<I_AD_User_SortPref_Line> sortPreferenceLines = userSortPrefDAO.retrieveSortPreferenceLines(hdr).iterator();
				while (sortPreferenceLines.hasNext())
				{
					final I_AD_User_SortPref_Line sortPreferenceLine = sortPreferenceLines.next();
					sortPreferenceLine.setSeqNo(seqNumber);
					InterfaceWrapperHelper.save(sortPreferenceLine);

					seqNumber = seqNumber + 10;
				}
			}
		});

		return "@SeqNoRecalculated@";
	}
}
