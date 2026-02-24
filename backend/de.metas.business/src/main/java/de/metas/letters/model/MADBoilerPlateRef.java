/**
 * 
 */
package de.metas.letters.model;

 

import org.adempiere.exceptions.AdempiereException;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author teo_sarca
 *
 */
public class MADBoilerPlateRef extends X_AD_BoilerPlate_Ref
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 472075266306434709L;

	/**
	 * @param ctx
	 * @param AD_BoilerPlate_Ref_ID
	 * @param trxName
	 */
	public MADBoilerPlateRef(Properties ctx, int AD_BoilerPlate_Ref_ID, String trxName)
	{
		super(ctx, AD_BoilerPlate_Ref_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MADBoilerPlateRef(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	public MADBoilerPlateRef(MADBoilerPlate parent, String refName)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setAD_BoilerPlate_ID(parent.getAD_BoilerPlate_ID());
		
		final int Ref_BoilerPlate_ID = MADBoilerPlate.getIdByName(getCtx(), refName, get_TrxName());
		if (Ref_BoilerPlate_ID <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_BoilerPlate_ID@ (@Name@:"+refName+")");
		}
		setRef_BoilerPlate_ID(Ref_BoilerPlate_ID);
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getAD_BoilerPlate_ID() == getRef_BoilerPlate_ID())
		{
			throw new AdempiereException("@de.metas.letters.AD_BoilerPlate.SelfReferencingError@");
		}
		return true;
	}
}
