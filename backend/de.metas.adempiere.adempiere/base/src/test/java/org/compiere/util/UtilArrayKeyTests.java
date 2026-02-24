package org.compiere.util;

 


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.compiere.util.Util.ArrayKey;
import org.junit.Test;

/**
 * Some noob tests that I need to gather some self-confidence using array as keys in hashset and hashmaps.
 * 
 * @author ts
 * 
 */
public class UtilArrayKeyTests
{

	/**
	 * Different int arrays can't be used as keys, because they are not equal
	 */
	@Test
	public void intArrarysAreNotEqual()
	{
		final int[] intArray1 = new int[] { 1, 2 };
		final int[] intArray2 = new int[] { 1, 2 };

		assertTrue(intArray1[0] == intArray2[0]);
		assertTrue(intArray1[1] == intArray2[1]);

		assertFalse(intArray1.equals(intArray2));
		assertFalse(intArray1.hashCode() == intArray2.hashCode());
	}

	/**
	 * Different Integer arrays can't be used as keys, because they are not equal
	 */
	@Test
	public void integerArrarysArenotEqual()
	{
		final Integer[] intArray1 = new Integer[] { new Integer(1), new Integer(2) };
		final Integer[] intArray2 = new Integer[] { new Integer(1), new Integer(2) };

		assertTrue(intArray1[0].equals(intArray2[0]));
		assertTrue(intArray1[1].equals(intArray2[1]));

		assertFalse(intArray1.equals(intArray2));
		assertFalse(intArray1.hashCode() == intArray2.hashCode());
	}

	@Test
	public void arraysKeyWithIntegerAreEqual()
	{
		final ArrayKey key1 = Util.mkKey(new Integer(1), new Integer(2));
		final ArrayKey key2 = Util.mkKey(new Integer(1), new Integer(2));

		assertTrue(key1.equals(key2));
		assertTrue(key2.equals(key1));

		assertTrue(key1.hashCode() == key2.hashCode());
	}

	@Test
	public void arraysKeyWithIntAreEqual()
	{
		final ArrayKey key1 = Util.mkKey(1, 2);
		final ArrayKey key2 = Util.mkKey(1, 2);

		assertTrue(key1.equals(key2));
		assertTrue(key2.equals(key1));

		assertTrue(key1.hashCode() == key2.hashCode());
	}

	@Test
	public void arraysKeyWithIntAreEqual2()
	{
		ArrayKey key1 = Util.mkKey(new Object[] { 1, 2 });
		ArrayKey key2 = Util.mkKey(new Object[] { 1, 2 });
		// Please note that if we use following code, IT WILL NOT WORK:
		//ArrayKey key1 = MiscUtils.mkKey(new int[] { 1, 2 });
		//ArrayKey key2 = MiscUtils.mkKey(new int[] { 1, 2 });

		assertTrue(key1.hashCode() == key2.hashCode());
		assertTrue(key1.equals(key2));
	}
}
