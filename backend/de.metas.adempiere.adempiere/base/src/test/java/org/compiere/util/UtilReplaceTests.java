package org.compiere.util;

 


import org.junit.Assert;
import org.junit.Test;

public class UtilReplaceTests
{
	@Test
	public void test_replaceNonDigitCharsWithZero1()
	{
		final String stringToModify = "   9   io   * && ^^^% i	";
		
		final String expected =       "000900000000000000000000";
		
		final String modifiedString = Util.replaceNonDigitCharsWithZero(stringToModify);
		
		Assert.assertEquals("Strings do not fit", expected, modifiedString);
	}
	
	@Test
	public void test_replaceNonDigitCharsWithZero2()
	{
		final String stringToModify = "   9   io   80 && ^17 i	!@#$%0";
		
		final String expected =       "000900000000800000017000000000";
		
		final String modifiedString = Util.replaceNonDigitCharsWithZero(stringToModify);
		
		Assert.assertEquals("Strings do not fit", expected, modifiedString);
	}
	
	
	@Test
	public void test_replaceNonDigitCharsWithZero3()
	{
		final String stringToModify = "0";
		
		final String expected =       "0";
		
		final String modifiedString = Util.replaceNonDigitCharsWithZero(stringToModify);
		
		Assert.assertEquals("Strings do not fit", expected, modifiedString);
	}
	
	@Test
	public void test_replaceNonDigitCharsWithZero4()
	{
		final String stringToModify = "123456789";
		
		final String expected =       "123456789";
		
		final String modifiedString = Util.replaceNonDigitCharsWithZero(stringToModify);
		
		Assert.assertEquals("Strings do not fit", expected, modifiedString);
	}
	
	
	@Test
	public void test_replaceNonDigitCharsWithZero5()
	{
		final String stringToModify = "~!@#$%^&*()_-+=    	";
		
		final String expected =       "00000000000000000000";
		
		final String modifiedString = Util.replaceNonDigitCharsWithZero(stringToModify);
		
		Assert.assertEquals("Strings do not fit", expected, modifiedString);
	}
	
}
