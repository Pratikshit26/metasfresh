package org.adempiere.util;

 


import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BeanUtil
{
	public static PropertyDescriptor getPropertyDescriptor(BeanInfo beanInfo, String propertyName)
	{
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		if (propertyDescriptors == null || propertyDescriptors.length == 0)
		{
			return null;
		}
		
		String propertyName2 = null;
		if (propertyName.toLowerCase().startsWith("is"))
		{
			propertyName2 = propertyName.substring(2);
		}
		PropertyDescriptor suggestion2 = null;
		
		for (PropertyDescriptor pd : propertyDescriptors)
		{
			if (pd.getName().equalsIgnoreCase(propertyName))
			{
				return pd;
			}

			if (pd.getName().equalsIgnoreCase(propertyName2))
			{
				suggestion2 = pd;
			}
			
		}
		
		return suggestion2;
	}
	
	public static Map<String, PropertyDescriptor> getPropertyDescriptorsMap(BeanInfo beanInfo, List<String> propertyNames)
	{
		final Map<String, PropertyDescriptor> map = new HashMap<String, PropertyDescriptor>();
		for (String propertyName : propertyNames)
		{
			PropertyDescriptor pd = getPropertyDescriptor(beanInfo, propertyName);
			if (pd != null)
			{
				map.put(propertyName, pd);
			}
		}
		return map;
	}
}
