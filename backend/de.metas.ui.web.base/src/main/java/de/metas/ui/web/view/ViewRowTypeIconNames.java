package de.metas.ui.web.view;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

/**
 * Defines all row icons which are available on frontend side.
 * <p>
 * NOTE to developers: On frontend side, the icons are defined in:
 * <ul>
 * <li>/src/assets/css/font-meta.css
 * <li>/src/assets/css/fonts/metasfresh.ttf
 * <li>/src/components/table/TableItem.js - getIconClassName function
 * </ul>
 */
@UtilityClass
public class ViewRowTypeIconNames
{
	public static final String ICONNAME_LU = "LU";
	public static final String ICONNAME_TU = "TU";
	public static final String ICONNAME_CU = "CU";

	// Required by issue: https://github.com/metasfresh/metasfresh-webui-frontend/issues/675#issuecomment-297016790
	public static final String ICONNAME_PP_Order_Receive = "PP_Order_Receive";
	public static final String ICONNAME_PP_Order_Issue = "PP_Order_Issue";
	public static final String ICONNAME_PP_Order_Issue_Service = "PP_Order_Issue_Service";
}
