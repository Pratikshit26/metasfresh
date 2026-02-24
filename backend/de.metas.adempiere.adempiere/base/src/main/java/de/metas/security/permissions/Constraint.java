package de.metas.security.permissions;

 


import org.adempiere.util.lang.ObjectUtils;

/**
 * Defines a security constraint.
 * 
 * A security constraint shall be seen as a custom rule which defines what shall be done or what's allowed in a given context.
 * 
 * The framework user shall know about a given constraint and it shall ask for it.
 * 
 * @author tsa
 *
 */
public abstract class Constraint
{
	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 * @return true if this constraint can be inherited from included roles.
	 */
	public abstract boolean isInheritable();
}
