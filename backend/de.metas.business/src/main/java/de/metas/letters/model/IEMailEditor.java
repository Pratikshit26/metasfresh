/**
 * 
 */
package de.metas.letters.model;

 


import org.compiere.model.I_AD_User;

import de.metas.email.EMail;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;

/**
 * @author teo_sarca
 *
 */
public interface IEMailEditor
{
	/**
	 * @return Base/Source Object (PO or GridTab)
	 */
	Object getBaseObject();
	int getAD_Table_ID();
	int getRecord_ID();
	EMail sendEMail(I_AD_User from, String toEmail, String subject, BoilerPlateContext attributes);
}
