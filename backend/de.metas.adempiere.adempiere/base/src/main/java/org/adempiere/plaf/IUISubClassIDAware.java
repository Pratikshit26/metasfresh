package org.adempiere.plaf;

 


import javax.swing.plaf.ComponentUI;

/**
 * To be extended by components are extending a standard UI component but they want to have a different L&F.
 * 
 * NOTE: the {@link ComponentUI} implementation shall be aware of this. In case it's not, this will have no effect.
 * 
 * @author tsa
 *
 */
public interface IUISubClassIDAware
{
	String getUISubClassID();
}
