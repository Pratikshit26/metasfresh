/**
 * 
 */
package de.metas.letters.model;

 


/**
 * @author teo_sarca
 *
 */
public interface I_AD_Column extends org.compiere.model.I_AD_Column
{
    public static final String COLUMNNAME_IsAdvancedText = "IsAdvancedText";
	public void setIsAdvancedText (boolean IsAdvancedText);
	public boolean isAdvancedText();
}
