package org.adempiere.facet;

 


/**
 * Used to categorize {@link IFacet}s.
 *
 * @author tsa
 *
 */
public interface IFacetCategory
{
	/**
	 * @return user friendly name
	 */
	String getDisplayName();

	/**
	 * @return true if the facets category UI panel needs to be collabled by default.
	 */
	boolean isCollapsed();

	/**
	 * @return true if the facets from this category needs to be refreshed each time a {@link IFacetFilterable} is filtered.
	 */
	boolean isEagerRefresh();
}
