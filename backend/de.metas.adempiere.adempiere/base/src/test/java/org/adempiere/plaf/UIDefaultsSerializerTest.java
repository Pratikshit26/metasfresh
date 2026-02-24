package org.adempiere.plaf;

 


import java.awt.Color;
import java.awt.Font;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import org.junit.Assert;
import org.junit.Test;

public class UIDefaultsSerializerTest
{
	private UIDefaultsSerializer serializer = new UIDefaultsSerializer();

	@Test
	public void testNull()
	{
		Assert.assertEquals(null, serializer.toString(null));
		Assert.assertEquals(null, serializer.fromString(null));
		Assert.assertEquals(null, serializer.fromString(""));
	}

	@Test
	public void testColor()
	{
		testColor(Color.RED);
		testColor(new Color(1, 2, 3, 4));
	}

	private void testColor(final Color testColor)
	{
		// Test serializing/deserializing from ColorUIResource
		{
			final ColorUIResource value = new ColorUIResource(testColor);
			// System.out.println("value=" + value);
			final String valueStr = serializer.toString(value);
			// System.out.println("valueStr=" + valueStr);

			final Object value2 = serializer.fromString(valueStr);
			// System.out.println("value2=" + value2);

			Assert.assertEquals(value, value2);
		}

		// Test serializing/deserializing directly
		{
			final String valueStr = serializer.toString(testColor);
			final Object value2 = serializer.fromString(valueStr);
			Assert.assertEquals(testColor, value2);
		}

	}

	@Test
	public void testFont()
	{
		testFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 20));
	}

	private void testFont(final Font testFont)
	{
		// Test serializing/deserializing from FontUIResource
		{
			final FontUIResource value = new FontUIResource(testFont);
			final String valueStr = serializer.toString(value);

			final Object value2 = serializer.fromString(valueStr);
			Assert.assertEquals(value, value2);
		}

		// Test serializing/deserializing directly
		{
			final String valueStr = serializer.toString(testFont);

			final Object value2 = serializer.fromString(valueStr);
			Assert.assertEquals(testFont, value2);
		}
	}
}
