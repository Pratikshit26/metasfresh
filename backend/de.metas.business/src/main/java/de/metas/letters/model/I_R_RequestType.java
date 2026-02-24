/**
 * 
 */
package de.metas.letters.model;

 


/**
 * @author teo_sarca
 *
 */
public interface I_R_RequestType extends org.compiere.model.I_R_RequestType
{
	public static final String COLUMNNAME_IsDefaultForEMail = "IsDefaultForEMail";
	public void isDefaultForEMail();
	public boolean setIsDefaultForEMail(boolean IsDefaultEMail);
	
	public static final String COLUMNNAME_IsDefaultForLetter = "IsDefaultForLetter";
	public void isDefaultLetter();
	public boolean setIsDefaultForLetter(boolean IsDefaultLetter);
}
