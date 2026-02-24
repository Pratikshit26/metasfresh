package org.compiere.apps.search;

 


import java.util.List;

import org.compiere.model.I_AD_InfoColumn;

/**
 * @author cg
 * 
 */
public interface IInfoQueryCriteria
{
	/**
	 * Constant used to inform where clause builder to clear the where clauses produced by previous criterias
	 */
	String WHERECLAUSE_CLEAR_PREVIOUS = new String("/* clear previous */");

	/**
	 * Constant used to inform where clause builder to stop asking other criterias for where clauses
	 */
	String WHERECLAUSE_STOP = new String("/* stop */");

	void init(IInfoSimple parent, I_AD_InfoColumn infoColumn, String searchText);

	I_AD_InfoColumn getAD_InfoColumn();
	
	int getParameterCount();

	String getLabel(int index);

	Object getParameterComponent(int index);

	Object getParameterToComponent(int index);

	Object getParameterValue(int index, boolean returnValueTo);

	String[] getWhereClauses(List<Object> params);

	String getText();
}
