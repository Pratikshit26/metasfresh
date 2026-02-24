/**
 * 
 */
package de.metas.user.process;

 


import java.util.List;

import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_TreeBar;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import org.compiere.model.I_AD_User;

/**
 * @author cg
 * 
 */
public class AD_User_CopyFavoritesPanel extends JavaProcess
{

	private int p_AD_User_ID = -1;
	public static final String PARAM_AD_User_ID = "AD_User_ID";

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				continue;
			}
			else if (name.equals(PARAM_AD_User_ID))
			{
				p_AD_User_ID = para.getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt()
	{
		Check.assume(p_AD_User_ID > 0, "User should not be empty! ");
		
		final int targetUser_ID = getRecord_ID(); 
		
		Check.assume(targetUser_ID > 0, "There is no record selected! ");

		final I_AD_User targetUser = Services.get(IUserDAO.class).getById(targetUser_ID);
		Check.assume(targetUser.isSystemUser(), "Selected user is not system user! ");

		final String whereClause = I_AD_TreeBar.COLUMNNAME_AD_User_ID + " = ? ";

		final List<I_AD_TreeBar> treBars = new TypedSqlQuery<I_AD_TreeBar>(getCtx(), I_AD_TreeBar.class, whereClause, get_TrxName())
												.setOnlyActiveRecords(true)
												.setParameters(p_AD_User_ID)
												.list();
												
		int cnt = 0;

		for (final I_AD_TreeBar treeBar : treBars)
		{
			if (!existsAlready(targetUser_ID, treeBar.getNode_ID()))
			{
				final I_AD_TreeBar tb = InterfaceWrapperHelper.create(getCtx(), I_AD_TreeBar.class, get_TrxName());
				tb.setAD_Org_ID(treeBar.getAD_Org_ID());
				tb.setNode_ID(treeBar.getNode_ID());
				tb.setAD_User_ID(targetUser_ID);
				InterfaceWrapperHelper.save(tb);
				cnt++;
			}
		}
		

		return "Count: " + cnt;
	}

	/**
	 * check if the TreeBar already exists
	 */
	private boolean existsAlready(final int AD_User_ID, final int Node_ID)
	{
		final String whereClause = I_AD_TreeBar.COLUMNNAME_AD_User_ID + " = ? AND " 
								 + I_AD_TreeBar.COLUMNNAME_Node_ID + " = ?"; 
		
		return new TypedSqlQuery<I_AD_TreeBar>(getCtx(), I_AD_TreeBar.class, whereClause, get_TrxName())
				.setOnlyActiveRecords(true)
				.setParameters(AD_User_ID, Node_ID)
				.anyMatch();
	}

}
