package org.adempiere.ad.model.util;

 


import java.util.Collections;
import java.util.Comparator;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.util.Check;

/**
 * Comparator which compares 2 models by their ID.
 *
 * NOTE: null models are not allowed.
 *
 * @author tsa
 *
 * @param <ModelType>
 */
public final class ModelByIdComparator<ModelType> implements Comparator<ModelType>
{
	public static final transient ModelByIdComparator<Object> instance = new ModelByIdComparator<>();

	public static final <ModelType> ModelByIdComparator<ModelType> getInstance()
	{
		@SuppressWarnings("unchecked")
		final ModelByIdComparator<ModelType> instanceCasted = (ModelByIdComparator<ModelType>)instance;
		return instanceCasted;
	}

	private ModelByIdComparator()
	{
		super();
	}

	@Override
	public int compare(final ModelType model1, final ModelType model2)
	{
		if (model1 == model2)
		{
			return 0;
		}

		Check.assumeNotNull(model1, "model1 not null"); // shall not happen
		Check.assumeNotNull(model2, "model2 not null"); // shall not happen

		final int modelId1 = InterfaceWrapperHelper.getId(model1);
		final int modelId2 = InterfaceWrapperHelper.getId(model2);
		return modelId1 - modelId2;
	}

	/**
	 * Returns a comparator that imposes the reverse ordering of this comparator.
	 *
	 * @return a comparator that imposes the reverse ordering of this comparator.
	 */
	public final Comparator<ModelType> reversed()
	{
		// NOTE: when we will switch to java8 this method shall be dropped
		return Collections.reverseOrder(this);
	}

}
