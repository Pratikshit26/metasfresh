package org.adempiere.mm.attributes.api;

 


public enum AttributeAction
{
	Error("E"),
	GenerateNew("N"),
	Ignore("I");

	private final String code;

	AttributeAction(final String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return this.code;
	}
	
	public static AttributeAction forCode(final String code)
	{
		for (final AttributeAction value : values())
		{
			if (value.getCode().equals(code))
			{
				return value;
			}
		}
		
		throw new IllegalArgumentException("No AttributeAction was found for '"+code+"'");
	}
	
}
