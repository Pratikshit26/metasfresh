package de.metas.security;

import org.adempiere.ad.session.MFSession;

 


/**
 * If you want to be notified when user logs in/out, let your model validator implement this interface.
 * 
 * @author tsa
 * 
 */
public interface IUserLoginListener
{
	/**
	 * Called after user login.
	 * 
	 * @param AD_Org_ID
	 * @param AD_Role_ID
	 * @param AD_User_ID
	 */
	// NOTE: method signature shall be the same as org.adempiere.ad.modelvalidator.IModelInterceptor.onUserLogin(int, int, int)
	void onUserLogin(int AD_Org_ID, int AD_Role_ID, int AD_User_ID);

	/**
	 * Called before Logout
	 * 
	 * @param session
	 */
	void beforeLogout(final MFSession session);

	/**
	 * Called after Logout (note, at this moment session is closed)
	 * 
	 * @param session
	 */
	void afterLogout(final MFSession session);
}
