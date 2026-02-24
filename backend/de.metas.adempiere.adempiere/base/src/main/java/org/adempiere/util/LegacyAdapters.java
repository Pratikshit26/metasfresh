package org.adempiere.util;

 


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;

import de.metas.util.Check;

/**
 * Helper methods that can be used when moving business logic out of PO subclasses.
 * 
 */
public class LegacyAdapters
{
	public static <ST, TT extends PO> List<TT> convertToPOList(final List<ST> list)
	{
		if (list == null)
		{
			return null;
		}

		final List<TT> result = new ArrayList<TT>(list.size());
		for (int i = 0; i < list.size(); i++)
		{
			final ST model = list.get(i);
			final TT po;
			if (model == null)
			{
				// we are taking as it is
				po = null;
			}
			else
			{
				po = convertToPO(model);
			}

			result.add(po);
		}

		return result;
	}

	public static <ST, TT extends PO> TT[] convertToPOArray(final List<ST> list, final Class<TT> clazz)
	{
		if (list == null)
		{
			return null;
		}
		
		Check.assumeNotNull(clazz, "Param 'clazz' is not null");

		// Use Array native method to create array of a type only known at run time
		@SuppressWarnings("unchecked")
		final TT[] resultArray = (TT[])Array.newInstance(clazz, list.size());
		
		return convertToPOList(list).toArray(resultArray);
	}

	public static <ST, TT extends PO> TT convertToPO(final ST model)
	{
		if (model == null)
		{
			return null;
		}

		@SuppressWarnings("unchecked")
		final TT po = (TT)InterfaceWrapperHelper.getPO(model);

		Check.assumeNotNull(po, "po created from {} is not null", model);
		return po;
	}
}
